package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.intermine.dataconversion.DirectoryConverter;
import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.xml.full.Item;
import org.xml.sax.SAXException;

/**
 * A DirectoryConverter that automatically sets the dataSets collection of
 * objects as they are stored.
 *
 * @author Julie Sullivan
 */

public abstract class BioDirectoryConverter extends DirectoryConverter
{
    private final Map<String, String> dataSets = new HashMap<String, String>();
    private final Map<String, String> dataSources = new HashMap<String, String>();
    private Set<String> synonyms = new HashSet<String>();
    private Set<String> crossReferences = new HashSet<String>();
    private Map<String, String> organisms = new HashMap<String, String>();

    /**
     * Create a new BioDirectoryConverter.
     * @param writer the Writer used to output the resultant items
     * @param model the data model
     * @param dataSourceName the DataSource name
     * @param dataSetTitle the DataSet title
     */
    public BioDirectoryConverter (ItemWriter writer, Model model,
                             String dataSourceName, String dataSetTitle) {
        super(writer, model);
        String dataSourceRefId = null;
        String dataSetRefId = null;
        if (StringUtils.isNotEmpty(dataSourceName) && StringUtils.isNotEmpty(dataSetTitle)) {
            dataSourceRefId = getDataSource(dataSourceName);
            dataSetRefId = getDataSet(dataSetTitle, dataSourceRefId);
        }
        BioStoreHook hook = new BioStoreHook(model, dataSetRefId, dataSourceRefId);
        setStoreHook(hook);
    }

    /**
     * Return a DataSource item for the given title
     * @param name the DataSource name
     * @return the DataSource Item
     */
    public String getDataSource(String name) {
        if (name == null) {
            return null;
        }
        String refId = dataSources.get(name);
        if (refId == null) {
            Item item = createItem("DataSource");
            item.setAttribute("name", name);
            try {
                store(item);
            } catch (ObjectStoreException e) {
                throw new RuntimeException("failed to store DataSource with name: " + name, e);
            }
            refId = item.getIdentifier();
            dataSources.put(name, refId);
        }
        return refId;
    }

    /**
     * Return a DataSet item with the given details.
     * @param title the DataSet title
     * @param dataSourceRefId the DataSource referenced by the the DataSet
     * @return the DataSet Item
     */
    public String getDataSet(String title, String dataSourceRefId) {
        String refId = dataSets.get(title);
        if (refId == null) {
            Item dataSet = createItem("DataSet");
            dataSet.setAttribute("name", title);
            dataSet.setReference("dataSource", dataSourceRefId);
            try {
                store(dataSet);
            } catch (ObjectStoreException e) {
                throw new RuntimeException("failed to store DataSet with title: " + title, e);
            }
            refId = dataSet.getIdentifier();
            dataSets.put(title, refId);
        }
        return refId;
    }

    /**
     * The Organism item created from the taxon id passed to the constructor.
     * @param taxonId NCBI taxonomy id of organism to create
     * @return the refId representing the Organism Item
     */
    public String getOrganism(String taxonId) {
        String refId = organisms.get(taxonId);
        if (refId == null) {
            Item organism = createItem("Organism");
            organism.setAttribute("taxonId", taxonId);
            try {
                store(organism);
            } catch (ObjectStoreException e) {
                throw new RuntimeException("failed to store organism with taxonId: " + taxonId, e);
            }
            refId = organism.getIdentifier();
            organisms.put(taxonId, refId);
        }
        return refId;
    }

    /**
     * Create a new CrossReference.  Keeps a map of already processed items, ignores duplicates.
     * The "store" param should be true only if the subject has already been stored.  Storing a
     * CrossReference first can signficantly slow down the build process.
     * @param subjectId id representing the object (eg. Gene) this CrossReference describes.
     * @param value identifier
     * @param dataSource external database
     * @param store if true, will store item
     * @throws ObjectStoreException if the synonym can't be stored
     * @throws SAXException if the synonym can't be stored
     * @return the synonym item or null if this is a duplicate
     */
    public Item createCrossReference(String subjectId, String value, String dataSource,
            boolean store)
        throws SAXException, ObjectStoreException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        String key = subjectId + value;
        if (!crossReferences.contains(key)) {
            Item item = createItem("CrossReference");
            item.setAttribute("identifier", value);
            item.setReference("subject", subjectId);
            item.setReference("source", getDataSource(dataSource));
            crossReferences.add(key);
            if (store) {
                store(item);
            }
            return item;
        }
        return null;
    }

    /**
     * Create a new Synonym.  Keeps a map of already processed synonyms, ignores duplicates.
     * The "store" param should be true only if the subject has already been stored.  Storing a
     * synonym first can signficantly slow down the build process.
     * @param subjectId id representing the object (eg. Gene) this synonym describes.
     * @param type the Synonym type, eg. identifier, name
     * @param value the Synonym value
     * @param isPrimary true if this is a primary identifier, false if not, null if don't know
     * @param store if true, will store item
     * @throws ObjectStoreException if the synonym can't be stored
     * @throws SAXException if the synonym can't be stored
     * @return the synonym item or null if this is a duplicate
     */
    public Item createSynonym(String subjectId, String type, String value, String isPrimary,
            boolean store)
        throws SAXException, ObjectStoreException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        String key = subjectId + type + value;
        if (!synonyms.contains(key)) {
            Item synonym = createItem("Synonym");
            synonym.setAttribute("type", type);
            synonym.setAttribute("value", value);
            synonym.setReference("subject", subjectId);
            if (!StringUtils.isEmpty(isPrimary)) {
                synonym.setAttribute("isPrimary", isPrimary);
            }
            synonyms.add(key);
            if (store) {
                store(synonym);
            }
            return synonym;
        }
        return null;
    }
}
