package org.intermine.web.logic.bag;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.intermine.InterMineException;
import org.intermine.metadata.Model;
import org.intermine.model.InterMineObject;
import org.intermine.model.userprofile.Tag;
import org.intermine.objectstore.ObjectStore;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.objectstore.intermine.ObjectStoreInterMineImpl;
import org.intermine.objectstore.query.Query;
import org.intermine.objectstore.query.Results;
import org.intermine.objectstore.query.ResultsRow;
import org.intermine.util.CollectionUtil;
import org.intermine.util.DynamicUtil;
import org.intermine.util.TypeUtil;
import org.intermine.web.logic.profile.Profile;
import org.intermine.web.logic.profile.ProfileManager;
import org.intermine.web.logic.profile.TagManager;
import org.intermine.web.logic.session.SessionMethods;
import org.intermine.web.logic.tagging.TagNames;
import org.intermine.web.logic.tagging.TagTypes;
import org.intermine.web.logic.template.TemplateQuery;

/**
 * For a given list of input strings search for objects using default and configured queries for a
 * particular type.
 *
 * @author Richard Smith
 */
public class BagQueryRunner
{
    //private static final Logger LOG = Logger.getLogger(BagQueryRunner.class);
    private ObjectStoreInterMineImpl os;

    private Model model;

    private Map classKeys;

    private BagQueryConfig bagQueryConfig;

    private ServletContext context;

    /**
     * Construct with configured bag queries and a map of type -&gt; key fields.
     *
     * @param os
     *            the ObjectStore to run queries on
     * @param classKeys
     *            the class keys Map
     * @param bagQueryConfig
     *            the configuration for running queries
     * @param context
     *            the ServletContext used by type conversion
     */
    public BagQueryRunner(ObjectStore os, Map classKeys, BagQueryConfig bagQueryConfig,
                          ServletContext context) {
        this.os = (ObjectStoreInterMineImpl) os;
        this.context = context;
        this.model = os.getModel();
        this.classKeys = classKeys;
        this.bagQueryConfig = bagQueryConfig;
    }

    /**
     * Given an input list of string identifiers search for corresponding objects. First run a
     * default query then any queries configured for the speified type.
     *
     * @param type
     *            an unqualified class name to search for objects
     * @param input
     *            a list of strings to query
     * @param extraFieldValue
     *            the value used when adding an extra constraint to the bag query, configured in
     *            BagQueryConfig (eg. if connectField is "organism", the extraClassName is
     *            "Organism" and the constrainField is "name", the extraFieldValue might be
     *            "Drosophila melanogaster")
     * @param doWildcards true if the strings should be evaluated as wildcards
     * @return the matches, issues and unresolved input
     * @throws ClassNotFoundException if the type isn't in the model
     * @throws InterMineException if there is any other exception
     */
    public BagQueryResult searchForBag(String type, List input, String extraFieldValue,
            boolean doWildcards) throws ClassNotFoundException, InterMineException {

        Map<String, String> lowerCaseInput = new HashMap<String, String>();
        List<String> cleanInput = new ArrayList<String>();
        List<String> wildcardInput = new ArrayList<String>();
        Map<String, Pattern> patterns = new HashMap<String, Pattern>();
        Iterator inputIter = input.iterator();
        while (inputIter.hasNext()) {
            String inputString = (String) inputIter.next();
            if (!(inputString == null) && !(inputString.equals(""))) {
                if (inputString.indexOf('*') == -1 || (!doWildcards)) {
                    if (!lowerCaseInput.containsKey(inputString.toLowerCase())) {
                        cleanInput.add(inputString);
                        lowerCaseInput.put(inputString.toLowerCase(), inputString);
                    }
                } else {
                    wildcardInput.add(inputString);
                    patterns.put(inputString, Pattern.compile(inputString.toLowerCase()
                            .replaceAll("\\*", "\\.\\*")));
                }
            }
        }

        // TODO tidy up using type String and Class

        // TODO BagQueryResult.getUnresolved() needs to return a map from input
        // to null (if not found) or a set of objects.
        // or just leave as a list of identifiers and objects of the qrong type
        // CollectionUtil.groupByClass will sort out the strings and types
        Class typeCls = Class.forName(model.getPackageName() + "." + type);
        List<BagQuery> queries =
            getBagQueriesForType(bagQueryConfig, typeCls.getName());
        Set<String> unresolved = new LinkedHashSet<String>(cleanInput);
        Set wildcardUnresolved = new LinkedHashSet(wildcardInput);
        Iterator<BagQuery> qIter = queries.iterator();
        BagQueryResult bqr = new BagQueryResult();
        while (qIter.hasNext()) {
            BagQuery bq = qIter.next();
            // run the next query on identifiers not yet resolved
            if (!unresolved.isEmpty()) {
                Map<String, Set<Integer>> resMap = new HashMap<String, Set<Integer>>();
                Query q = null;
                try {
                    q = bq.getQuery(unresolved, extraFieldValue);
                    Results res = os.execute(q);
                    res.setNoPrefetch();
                    res.setBatchSize(10000);
                    Iterator resIter = res.iterator();
                    while (resIter.hasNext()) {
                        ResultsRow row = (ResultsRow) resIter.next();
                        Integer id = (Integer) row.get(0);
                        for (int i = 1; i < row.size(); i++) {
                            Object fieldObject = row.get(i);
                            if (fieldObject != null) {
                                if (!(fieldObject instanceof String)) {
                                    fieldObject = fieldObject.toString();
                                }
                                String field = (String) fieldObject;
                                String lowerField = field.toLowerCase();
                                if (lowerCaseInput.containsKey(lowerField)) {
                                    // because we are converting to lower case we need to match
                                    // to original input so that 'h' matches 'H' and 'h' becomes
                                    // a duplicate.
                                    String originalInput = lowerCaseInput.get(lowerField);
                                    Set<Integer> ids = resMap.get(originalInput);
                                    if (ids == null) {
                                        ids = new HashSet<Integer>();
                                        resMap.put(originalInput, ids);
                                    }
                                    // obj is an Integer
                                    ids.add(id);
                                    // remove any identifiers that are now resolved
                                    unresolved.remove(lowerCaseInput.get(lowerField));
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // Query couldn't handle extra value
                }
                addResults(resMap, unresolved, bqr, bq, typeCls, false);
            }
            if (!wildcardInput.isEmpty()) {
                try {
                    Map<String, Set<Integer>> resMap = new HashMap<String, Set<Integer>>();
                    Query q = bq.getQueryForWildcards(wildcardInput, extraFieldValue);
                    Results res = os.execute(q);
                    res.setNoPrefetch();
                    for (ResultsRow row : (List<ResultsRow>) res) {
                        Integer id = (Integer) row.get(0);
                        for (int i = 1; i < row.size(); i++) {
                            String field = "" + row.get(i);
                            String lowerField = field.toLowerCase();
                            for (String wildcard : wildcardInput) {
                                Pattern pattern = patterns.get(wildcard);
                                if (pattern.matcher(lowerField).matches()) {
                                    Set<Integer> ids = resMap.get(wildcard);
                                    if (ids == null) {
                                        ids = new HashSet<Integer>();
                                        resMap.put(wildcard, ids);
                                    }
                                    ids.add(id);
                                    // we have matched at least once with wildcard
                                    wildcardUnresolved.remove(wildcard);
                                }
                            }
                        }
                    }
                    for (Map.Entry<String, Set<Integer>> entry : resMap.entrySet()) {
                        // This is a dummy issue just to give a message when running queries
                        bqr.addIssue(BagQueryResult.WILDCARD, bq.getMessage(),
                                entry.getKey(), new ArrayList(entry.getValue()));
                        addResults(resMap, wildcardUnresolved, bqr, bq, typeCls, true);
                    }
                } catch (IllegalArgumentException e) {
                    // Query couldn't handle extra value
                }
            }
        }

        unresolved.addAll(wildcardUnresolved);
        Map<String, ?> unresolvedMap = new HashMap();
        Iterator<String> iter = unresolved.iterator();
        while (iter.hasNext()) {
            unresolvedMap.put(iter.next(), null);
        }
        bqr.getUnresolved().putAll(unresolvedMap);

        return bqr;
    }

    /**
     * Add results from resMap to a a BagQueryResults object.
     *
     * @throws InterMineException
     */
    private void addResults(Map<String, Set<Integer>> resMap, Set<String> unresolved,
                            BagQueryResult bqr, BagQuery bq, Class<?> type, boolean areWildcards)
    throws InterMineException {
        Map<String, Set<Object>> objsOfWrongType = new HashMap<String, Set<Object>>();
        Iterator mapIter = resMap.entrySet().iterator();
        while (mapIter.hasNext()) {
            Map.Entry entry = (Map.Entry) mapIter.next();
            String input = (String) entry.getKey();
            Set ids = (Set) entry.getValue();
            boolean resolved = true;

            if (!bq.matchesAreIssues()) {

                // if matches are not issues then each entry will be a match or a duplicate
                if (ids.size() == 1) {
                    bqr.addMatch(input, (Integer) ids.iterator().next());
                } else if (!areWildcards) {
                    List<Object> objs = new ArrayList<Object>();
                    Iterator objIter;
                    try {
                        objIter = os.getObjectsByIds(ids).iterator();
                    } catch (ObjectStoreException e) {
                        throw new InterMineException("can't fetch: " + ids, e);
                    }
                    while (objIter.hasNext()) {
                        objs.add(objIter.next());
                    }
                    bqr.addIssue(BagQueryResult.DUPLICATE, bq.getMessage(),
                                 (String) entry.getKey(), objs);
                }
            } else {
                List<Object> objs = new ArrayList<Object>();
                Set<Object> localObjsOfWrongType = new HashSet<Object>();
                Iterator objIter;
                try {
                    objIter = os.getObjectsByIds(ids).iterator();
                } catch (ObjectStoreException e) {
                    throw new InterMineException("can't fetch: " + ids, e);
                }

                // we have a list of objects that result from some query, divide into any that
                // match the type of the bag to be created and candidates for conversion
                while (objIter.hasNext()) {
                    Object obj = objIter.next();

                    // TODO this won't cope with dynamic classes
                    Class c = DynamicUtil.decomposeClass(obj.getClass()).iterator().next();
                    if (type.isAssignableFrom(c)) {
                        objs.add(obj);
                    } else {
                        localObjsOfWrongType.add(obj);
                    }
                }

                if (!objs.isEmpty()) {
                    // we have a list of objects, if any match the type then add to bqr as an issue
                    // discard objects that matched a different type
                    if (objs.size() == 1) {
                        bqr.addIssue(BagQueryResult.OTHER, bq.getMessage(), input, objs);
                    } else if (!areWildcards) {
                        bqr.addIssue(BagQueryResult.DUPLICATE, bq.getMessage(), input, objs);
                    }
                } else {
                    // all wrong, allow conversion attempts
                    resolved = false;
                    objsOfWrongType.put(input, localObjsOfWrongType);
                }
            }
            if (resolved) {
                unresolved.remove(input);
            }
        }

        // now objsOfWrongType contains all wrong types found for this query, try converting
        convertObjects(bqr, bq, type, objsOfWrongType);

        bqr.getUnresolved().putAll(objsOfWrongType);
    }

    /**
     * Find any objects in the objsOfWrongType Map that can be converted to the destination type,
     * add them to bqr as TYPE_CONVERTED issues and remove them from objsOfWrongType.
     */
    private void convertObjects(BagQueryResult bqr, BagQuery bq, Class<?> type, Map<String,
                                Set<Object>> objsOfWrongType)
        throws InterMineException {
        if (!objsOfWrongType.isEmpty()) {
            // group objects by class
            Map<InterMineObject, Set<String> > objectToInput =
                new HashMap<InterMineObject, Set<String> >();
            Iterator iter = objsOfWrongType.entrySet().iterator();
            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();
                String input = (String) entry.getKey();
                Set set = (Set) entry.getValue();
                Iterator objIter = set.iterator();
                while (objIter.hasNext()) {
                    InterMineObject imo = (InterMineObject) objIter.next();
                    Set<String> inputSet = objectToInput.get(imo);
                    if (inputSet == null) {
                        inputSet = new HashSet<String>();
                        objectToInput.put(imo, inputSet);
                    }
                    inputSet.add(input);
                }
            }

            Map objTypes = CollectionUtil.groupByClass(objectToInput.keySet(), true);

            Iterator objTypeIter = objTypes.keySet().iterator();
            while (objTypeIter.hasNext() && !objsOfWrongType.isEmpty()) {
                Class fromClass = (Class) objTypeIter.next();
                List candidateObjs = (List) objTypes.get(fromClass);

                // we may have already converted some of these types, remove any that have been.
                List<Object> objs = new ArrayList<Object>();
                Iterator candidateIter = candidateObjs.iterator();
                while (candidateIter.hasNext()) {
                    Object candidate = candidateIter.next();
                    if (objectToInput.containsKey(candidate)) {
                        objs.add(candidate);
                    }
                }

                // there was nothing to convert for this class
                if (objs.isEmpty()) {
                    continue;
                }

                // try to convert objects to target type
                Map convertedObjsMap = TypeConverter.getConvertedObjectMap(context,
                                           getConversionTemplates(context),
                                           fromClass, type, objs);
                if (convertedObjsMap == null) {
                    // no conversion found
                    continue;
                }
                // loop over the old objects
                Iterator origObjIter = convertedObjsMap.keySet().iterator();
                while (origObjIter.hasNext()) {
                    InterMineObject origObj = (InterMineObject) origObjIter.next();
                    List converterObjList = (List) convertedObjsMap.get(origObj);
                    Iterator convertedObjListIter = converterObjList.iterator();
                    boolean toRemove = false;
                    // then for each new object ...
                    while (convertedObjListIter.hasNext()) {
                        InterMineObject convertedObj = (InterMineObject) convertedObjListIter
                        .next();
                        ConvertedObjectPair convertedPair = new ConvertedObjectPair(origObj,
                                                                                    convertedObj);
                        List<Object> objPairList = new ArrayList<Object>();
                        Set origInputStringSet = objectToInput.get(origObj);
                        objPairList.add(convertedPair);
                        // remove this object so we don't try to convert it again
                        toRemove = true;
                        // make an issue for each input identifier that matched the objects in
                        // this old/new pair
                        Iterator inputStringIter = origInputStringSet.iterator();
                        while (inputStringIter.hasNext()) {
                            String origInputString = (String) inputStringIter.next();
                            bqr.addIssue(BagQueryResult.TYPE_CONVERTED,
                                         bq.getMessage() + " found by converting from x",
                                         origInputString, objPairList);
                            objsOfWrongType.remove(origInputString);
                        }
                    }
                    if (toRemove) {
                        objectToInput.remove(origObj);
                    }
                }
            }
        }
    }

    // temporary method - will be replaced by BagQueryHelper method
    private List<BagQuery> getBagQueriesForType(BagQueryConfig config, String type) {
        List<BagQuery> queries = new ArrayList<BagQuery>();

        // some queries should run before the default
        queries.addAll(config.getPreDefaultBagQueries(TypeUtil.unqualifiedName(type)));

        // create the default query and put it first in the list
        queries.add(new BagQuery(config, model, classKeys, type));

        // add any queries that are configured for this type
        queries.addAll(config.getBagQueries(TypeUtil.unqualifiedName(type)));

        return queries;
    }

    /**
     * Find template queries that are tagged for use as converters.
     * @param servletContext use to fetch ProfileManager and superuser account
     * @return a list of conversion templates
     */
    public static List<TemplateQuery> getConversionTemplates(ServletContext servletContext) {

        ProfileManager pm = SessionMethods.getProfileManager(servletContext);
        String sup = pm.getSuperuser();
        Profile p = pm.getProfile(sup);

        List<TemplateQuery> conversionTemplates = new ArrayList();
        TagManager tagManager = SessionMethods.getTagManager(servletContext);
        List<Tag> tags = tagManager.getTags(TagNames.IM_CONVERTER, null, TagTypes.TEMPLATE, sup);

        for (Tag tag : tags) {
            String oid = tag.getObjectIdentifier();
            TemplateQuery tq = p.getSavedTemplates().get(oid);
            if (tq != null) {
                conversionTemplates.add(tq);
            }
        }
        return conversionTemplates;
    }
}
