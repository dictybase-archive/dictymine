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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.objectstore.intermine.*;

import org.dictybase.util.PropertiesUtil;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Creates and runs a ModelOutput process to generate java or config files.
 *
 * @author Richard Smith
 * @author Matthew Wakeling
 * @author Siddhartha Basu
 */
public class TorqueModelOutputTask extends DefaultTask
{
    private File destFile, property;
    private String osName;
    private DatabaseSchema schema;
    private Properties intermineProps;

    /**
     * Intermine property file to load the configuration
     *
     * @param property is the property file
     */
    @InputFile
    public File getProperty() {
        return this.property;
    }
    public void setProperty(File file) {
        this.property = file;
    }

    /**
     * Sets the file to which the data should be written.
     *
     * @param destFile the file location
     */
    @OutputFile
    public File getDestFile() {
        return this.destFile;
    }
    public void setDestFile(File destFile) {
        this.destFile = destFile;
    }

    /**
     * Set the ObjectStore for which to generate the data.
     *
     * @param osName the ObjectStore name to be used
     */
    @Input
    public String getOsName() {
        return this.osName;
    }
    public void setOsName(String osName) {
        this.osName = osName;
    }

    @TaskAction
    public void run() throws GradleException {
        intermineProps = new Properties();
        try {
            intermineProps.load(new FileInputStream(getProperty()));
        } catch(IOException e) {
            throw new GradleException("unable to load properties file " + getProperty().toString() + " ", e);
        }
        try {
            Properties props = PropertiesUtil.getPropertiesStartingWith(getOsName(), intermineProps);
            props = PropertiesUtil.stripStart(getOsName(), props);

            String missingTablesString = props.getProperty("missingTables");
            String truncatedClassesString = props.getProperty("truncatedClasses");
            String noNotXmlString = props.getProperty("noNotXml");

            Model osModel;
            String modelName = props.getProperty("model");
            osModel = Model.getInstanceByName(modelName);
            List<ClassDescriptor> truncatedClasses = new ArrayList<ClassDescriptor>();
            if (truncatedClassesString != null) {
                String[] classes = truncatedClassesString.split(",");
                for (int i = 0; i < classes.length; i++) {
                    ClassDescriptor truncatedClassDescriptor =
                        osModel.getClassDescriptorByName(classes[i]);
                    if (truncatedClassDescriptor == null) {
                        throw new ObjectStoreException("Truncated class " + classes[i]
                                                       + " does not exist in the model");
                    }
                    truncatedClasses.add(truncatedClassDescriptor);
                }
            }
            boolean noNotXml = false;
            if ("true".equals(noNotXmlString) || (noNotXmlString == null)) {
                noNotXml = true;
            } else if ("false".equals(noNotXmlString)) {
                noNotXml = false;
            } else {
                throw new ObjectStoreException("Invalid value for property noNotXml: "
                        + noNotXmlString);
            }
            HashSet<String> missingTables = new HashSet<String>();
            if (missingTablesString != null) {
                String[] tables = missingTablesString.split(",");
                for (int i = 0; i < tables.length; i++) {
                    missingTables.add(tables[i].toLowerCase());
                }
            }

            schema = new DatabaseSchema(osModel, truncatedClasses, noNotXml, missingTables,
                    FORMAT_VERSION, false, false);
        } catch (ClassCastException e) {
            throw new GradleException("Objectstore " + osName
                    + " is not an ObjectStoreInterMineImpl", e);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new GradleException("unknown exception ", e);
        } catch (Error e) {
            e.printStackTrace(System.out);
            throw new GradleException("unknown error ", e);
        }

        TorqueModelOutput mo = new TorqueModelOutput(schema, getDestFile());
        mo.process();
    }
}
