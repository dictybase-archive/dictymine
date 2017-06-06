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
import java.io.IOException;
import java.io.File;

import org.dictybase.sql.DatabaseFactory;
import org.dictybase.sql.Database;
import org.dictybase.util.PropertiesUtil;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Shutdown database pool
 * @author Siddhartha Basu
 */
public class DbShutdownTask extends DefaultTask {
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
     * Sets the objectstore
     * @param osName String used to identify objectstore and therefore database
     */
    public void setOsName(String osName) {
        this.osName = osName;
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
            database.shutdown();
        } catch(Exception e) {
            throw new GradleException("unable to shutdown database ", e);
        }
    }
}

