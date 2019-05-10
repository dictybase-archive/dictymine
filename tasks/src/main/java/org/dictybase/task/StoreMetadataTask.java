package org.dictybase.task;

/*
 * Copyright (C) 2002-2016 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import static org.intermine.objectstore.intermine.TorqueModelOutput.FORMAT_VERSION;

import java.util.Properties;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import org.dictybase.metadata.Model;

import org.dictybase.sql.DatabaseFactory;
import org.dictybase.sql.Database;
import org.dictybase.util.PropertiesUtil;
import org.dictybase.metadata.ModelFactory;
import org.dictybase.modelproduction.MetadataManager;

import org.apache.log4j.Logger;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Store model metadata to a database
 * @author Kim Rutherford
 * @author Siddhartha Basu
 */
public class StoreMetadataTask extends DefaultTask
{
    private static final Logger LOG = Logger.getLogger(StoreMetadataTask.class);
    private File intermineProperty, userProperty, modelFile, keyFile;
    private String osName;

    /**
     * Intermine property file to load the configuration
     *
     * @param file is the property file
     */
    @InputFile
    public File getIntermineProperty() {
        return this.intermineProperty;
    }
    public void setIntermineProperty(File file) {
        this.intermineProperty = file;
    }

    /**
     * User defined property file to load the configuration
     *
     * @param file is the property file
     */
    @InputFile
    public File getUserProperty() {
        return this.userProperty;
    }
    public void setUserProperty(File file) {
        this.userProperty = file;
    }

    @Input
    public String getOsName() {
        return this.osName;
    }
    /**
     * Sets the objectstore
     * @param osName String used to identify objectstore and therefore database
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    @InputFile
    public File getModelFile() {
        return this.modelFile;
    }
    /**
     * Sets the model file
     * @param modelFile File used to identify the model (useprofrile, genomic...)
     */
    public void setModelFile(File file) {
        this.modelFile = file;
    }

    @InputFile
    public File getKeyFile() {
        return this.keyFile;
    }
    /**
     * Sets the key file
     * @param keyFile File is the key definition file
     */
    public void setKeyFile(File file) {
        this.keyFile = file;
    }

    @TaskAction
    public void run() throws GradleException {
        Properties mergedProps;
        Database database;
        String databaseAlias;
        try {
            mergedProps = PropertiesUtil.mergeProperties(getIntermineProperty(), getUserProperty());
        } catch(IOException e) {
            throw new GradleException("unable to merge properties file ", e);
        }
        try {
            databaseAlias = mergedProps.getProperty(getOsName() + ".db");
            database = DatabaseFactory.getDatabase(databaseAlias, mergedProps);
        } catch (Exception e) {
            throw new GradleException("unable to get database source ", e);
        }
        try {
            Model model = ModelFactory.loadModel(this.getModelFile());
            MetadataManager.store(database, MetadataManager.MODEL, model.toString());

            Properties keys = PropertiesUtil.loadProperties(this.getKeyFile());
            if (keys == null) {
                throw new GradleException("no keys for " + this.getKeyFile().toString()
                                         + " model found to store in the ObjectStore");
            }
            MetadataManager.store(database, MetadataManager.KEY_DEFINITIONS,
                                  PropertiesUtil.serialize(keys));

            /*Properties classKeys =
                MetadataManager.loadClassKeyDefinitions();
            MetadataManager.store(db, MetadataManager.CLASS_KEYS,
                                  PropertiesUtil.serialize(classKeys));*/

            /*Properties descriptions = MetadataManager.loadClassDescriptions(modelName);
            if (descriptions != null) {
                MetadataManager.store(db, MetadataManager.CLASS_DESCRIPTIONS,
                                      PropertiesUtil.serialize(descriptions));
            }*/

            Properties props = PropertiesUtil.getPropertiesStartingWith(this.getOsName(), mergedProps);
            props = PropertiesUtil.stripStart(this.getOsName(), props);

            String missingTablesString = props.getProperty("missingTables");
            String truncatedClassesString = props.getProperty("truncatedClasses");
            String noNotXmlString = props.getProperty("noNotXml");

            boolean noNotXml = false;
            if ("true".equals(noNotXmlString) || (noNotXmlString == null)) {
                noNotXml = true;
            } else if ("false".equals(noNotXmlString)) {
                noNotXml = false;
            } else {
                throw new GradleException("Invalid value for property noNotXml: "
                        + noNotXmlString);
            }

            MetadataManager.store(database, MetadataManager.OS_FORMAT_VERSION, "" + FORMAT_VERSION);
            if (truncatedClassesString != null) {
                MetadataManager.store(database, MetadataManager.TRUNCATED_CLASSES,
                        truncatedClassesString);
            }
            if (missingTablesString != null) {
                MetadataManager.store(database, MetadataManager.MISSING_TABLES, missingTablesString);
            }
            MetadataManager.store(database, MetadataManager.NO_NOTXML, "" + noNotXml);
            MetadataManager.store(database, MetadataManager.SERIAL_NUMBER, Long.toString(
                    new Random().nextLong()));
            LOG.info("stored all metadata information");
        } catch (Exception e) {
            throw new GradleException("unable to load model ", e);
        }
        database.shutdown();
    }
}
