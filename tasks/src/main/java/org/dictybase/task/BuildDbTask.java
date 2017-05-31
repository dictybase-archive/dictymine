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

import static org.intermine.objectstore.intermine.ObjectStoreInterMineImpl.CLOBVAL_COLUMN;
import static org.intermine.objectstore.intermine.ObjectStoreInterMineImpl.CLOB_TABLE_NAME;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;
import java.lang.String;

import org.apache.log4j.Logger;
import org.intermine.objectstore.intermine.ObjectStoreInterMineImpl;
import org.intermine.sql.DatabaseUtil;

import org.dictybase.util.PropertiesUtil;
import org.dictybase.sql.DatabaseFactory;
import org.dictybase.sql.Database;
import org.dictybase.util.DDLManager;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;


/**
 * Generates and inserts SQL given database name, schema and temporary directory
 *
 * @author Mark Woodbridge
 * @author Siddhartha Basu
 */
public class BuildDbTask extends DefaultTask
{
    private static final Logger LOG = Logger.getLogger(BuildDbTask.class);
    private static final String SERIAL_SEQUENCE_NAME = "serial";
    private File ddlFile, schemaFile, intermineProperty, userProperty;
    private Database database;
    private String databaseAlias;
    private String osName;
    private String model;


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

    @Input
    public String getModel() {
        return this.model;
    }
    /**
     * Sets the model
     * @param model String used to identify the model (useprofrile, genomic...)
     */
    public void setModel(String model) {
        this.model = model;
    }

    @OutputFile
    @Optional
    public File getDdlFile() {
        return this.ddlFile;
    }
    /**
     * Sets the file for ddl output
     * @param ddlFile is the output File
     */
    public void setDdlFile(File ddlFile) {
        this.ddlFile = ddlFile;
    }

    @InputFile
    public File getSchemaFile() {
        return this.schemaFile;
    }
    /**
     * Adds the schemafile to be processed.
     * @param schemafile to be processed
     */
    public void setSchemaFile(File schemafile) {
        this.schemaFile = schemafile;
    }

    @TaskAction
    public void run() throws GradleException {
        Properties mergedProps;
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

        Connection c = null;
        try {
            c = database.getConnection();
            c.setAutoCommit(true);
            DatabaseUtil.removeAllTables(c);
            DatabaseUtil.removeSequence(c, SERIAL_SEQUENCE_NAME);
            DatabaseUtil.removeSequence(
                c, ObjectStoreInterMineImpl.UNIQUE_INTEGER_SEQUENCE_NAME);
            LOG.info(String.format("removed sequence %s", SERIAL_SEQUENCE_NAME));
        } catch (SQLException e) {
            LOG.warn("Failed to remove all tables from database: " + e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        c = null;
        try {
            c = database.getConnection();
            c.setAutoCommit(true);
            DDLManager manager = new DDLManager(getSchemaFile());
            manager.loadSchema(c);
            LOG.info(String.format("loaded database schema from xml schema",
                         getSchemaFile().toString()));
        } catch(Exception e) {
            throw new GradleException("unable to load schema in the database ", e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        c = null;
        try {
            c = database.getConnection();
            c.setAutoCommit(true);
            c.createStatement().execute("CREATE SEQUENCE " + SERIAL_SEQUENCE_NAME);
            LOG.info(String.format("created sequence %s", SERIAL_SEQUENCE_NAME));
        } catch (SQLException e) {
            // probably happens because the SEQUENCE already exists
            LOG.info("Failed to create SEQUENCE: " + e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        c = null;
        try {
            c = database.getConnection();
            c.setAutoCommit(true);
            c.createStatement().execute("CREATE SEQUENCE "
                                        + ObjectStoreInterMineImpl.UNIQUE_INTEGER_SEQUENCE_NAME);
            LOG.info(String.format("created sequence %s", ObjectStoreInterMineImpl.UNIQUE_INTEGER_SEQUENCE_NAME));
        } catch (SQLException e) {
            // probably happens because the SEQUENCE already exists
            LOG.info("Failed to create SEQUENCE: " + e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        c = null;
        try {
            c = database.getConnection();
            c.setAutoCommit(true);
            c.createStatement().execute("ALTER TABLE " + CLOB_TABLE_NAME + " ALTER COLUMN "
                    + CLOBVAL_COLUMN + " SET STORAGE PLAIN");
            LOG.info(String.format("altered table %s", CLOB_TABLE_NAME));
        } catch (SQLException e) {
            // probably happens because the SEQUENCE already exists
            LOG.info("Failed to create SEQUENCE: " + e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        //tempFile.delete();

        //create bagvalues table in userprofile
        if (model.contains("userprofile")) {
            c = null;
            try {
                c = database.getConnection();
                c.setAutoCommit(true);
                DatabaseUtil.createBagValuesTables(c);
            } catch (SQLException e) {
                LOG.info("Failed to create bagvalues table: " + e);
            } finally {
                if (c != null) {
                    try {
                        LOG.info("created bag values");
                        c.close();
                    } catch (SQLException e) {
                        // ignore
                    }
                }
            }
        }
        database.shutdown();
    }
}
