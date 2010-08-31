package org.intermine.bio.util;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.intermine.dataconversion.DataConverter;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.xml.full.Item;

/**
 * Helper class for data converters
 * @author julie
  */
public class BioConverterUtil
{
    private static final String PROP_FILE = "soClassName.properties";
    private static Map<String, String> javaNamesToSO = new HashMap<String, String>();
    /**
     * Get SO name for Java class, eg. SequenceFeature to sequence_feature
     * @param javaClassName the relevant string
     * @return the SO value
     * @throws IOException if something goes wrong
     */
    public static String javaNameToSO(String javaClassName) throws IOException  {
        if (javaNamesToSO.isEmpty()) {
            InputStream is = BioConverterUtil.class.getClassLoader().getResourceAsStream(PROP_FILE);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(is));
            } catch (Exception e) {
                throw new BuildException("cannot file SO file: " + PROP_FILE, e);
            }
            String line = null;
            while ((line = br.readLine()) != null) {
                String fields[] = StringUtils.split(line, ' ');
                String javaName = fields[0];
                String soName = fields[1];
                javaNamesToSO.put(javaName, soName);
            }
        }
        return javaNamesToSO.get(javaClassName);
    }

    /**
     * Add values to a Map from keys to Set of values, creating the value list
     * as needed.
     *
     * @param map the Map
     * @param key the key
     * @param values the set of values
     */
    public static void addToSetMap(Map map, Object key, Set<Object> values) {
        Set valuesList = (Set) map.get(key);
        if (valuesList == null) {
            valuesList = new HashSet();
            map.put(key, valuesList);
        }
        valuesList.addAll(values);
    }


    /**
     * Add a value to a Map from keys to Set of values, creating the value list
     * as needed.
     *
     * @param map the Map
     * @param key the key
     * @param value the value
     */
    public static void addToSetMap(Map map, Object key, Object value) {
        Set valuesList = (Set) map.get(key);
        if (valuesList == null) {
            valuesList = new HashSet();
            map.put(key, valuesList);
        }
        valuesList.add(value);
    }

    /**
     * Add a value to a Map from keys to Set of values, creating the value list
     * as needed.
     *
     * @param map the Map
     * @param key the key
     * @param value the value
     */
    public static void addToListMap(Map map, Object key, Object value) {
        List valuesList = (List) map.get(key);
        if (valuesList == null) {
            valuesList = new ArrayList();
            map.put(key, valuesList);
        }
        valuesList.add(value);
    }


    /**
     * Create and store ontology object.
     *
     * @param dataConverter data converter
     * @return refId representing the ontology (SO) object
     */
    public static String getOntology(DataConverter dataConverter) {
        Item item = dataConverter.createItem("Ontology");
        item.setAttribute("name", "Sequence Ontology");
        item.setAttribute("url", "http://www.sequenceontology.org");
        try {
            dataConverter.store(item);
        } catch (ObjectStoreException e) {
            throw new RuntimeException("Can't store ontology", e);
        }
        return item.getIdentifier();
    }
}
