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

import java.util.Properties;
import java.io.File;
import java.io.IOException;

import org.dictybase.metadata.ClassDescriptor;
import org.dictybase.metadata.Model;
import org.dictybase.sql.Database;
import org.dictybase.sql.DatabaseFactory;
import org.dictybase.sql.DatabaseUtil;
import org.dictybase.util.PropertiesUtil;


import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Task to run ANALYSE on a table or whole database.
 *
 * @author Richard Smith
 * @author Andrew Varley
 * @author Siddhartha Basu
 */

public class AnalyseDbTask extends DefaultTask
{

    private boolean full = false;
    private String clsName;
    private String model;
    private File intermineProperty, userProperty;
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
     * Set the database alias
     * @param osName the database alias
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }


    @Input
    @Optional
    public String getClassName() {
        return this.clsName;
    }

    /**
     * Set an optional class name, must also set model name
     * @param clsName name of class to ANALYSE
     */
    public void setClassName(String clsName) {
        this.clsName = clsName;
    }

    @Input
    @Optional
    public String getModel() {
        return this.model;
    }

    /**
     * Set model name, must be set if class name specified
     * @param model containing the class
     */
    public void setModel(String model) {
        this.model = model;
    }

    @Input
    @Optional
    public boolean getFull() {
        return this.full;
    }

    /**
     * Set true if a VACUUM FULL ANALYSE required
     * @param full true for full anaylse
     */
    public void setFull(boolean full) {
        this.full = full;
    }

    @TaskAction
    public void run() throws GradleException {
        Database db;
        Properties mergedProps;
        try {
            mergedProps = PropertiesUtil.mergeProperties(this.getIntermineProperty(), this.getUserProperty());
        } catch(IOException e) {
            throw new GradleException("unable to merge properties file ", e);
        }
        try {
            db = DatabaseFactory.getDatabase(
                   mergedProps.getProperty(this.getOsName() + ".db"),
                   mergedProps
                );
        } catch (Exception e) {
            throw new GradleException("unable to get database source ", e);
        }
        try {
            if (this.getClassName() != null && !"".equals(this.getClassName())) {
                if (model == null) {
                    throw new GradleException("model attribute is not set");
                }
                Model m = Model.getInstanceByName(this.getModel());
                ClassDescriptor cld = m.getClassDescriptorByName(this.getClassName());
                if (cld == null) {
                    throw new GradleException("class does not exist in model: " + this.getClassName());
                }
                DatabaseUtil.analyse(db, cld, this.getFull());
            } else {
                DatabaseUtil.analyse(db, this.getFull());
            }
        } catch (Exception e) {
            throw new GradleException("error in analyzing index", e);
        }
        try {
            db.shutdown();
        } catch(Exception e) {
            throw new GradleException("unable to shutdown database ", e);
        }
    }
}
