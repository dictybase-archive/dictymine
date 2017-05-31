package org.dictybase.util;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.platform.postgresql.PostgreSqlBuilder;
import org.apache.ddlutils.platform.postgresql.PostgreSqlPlatform;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.DdlUtilsException;
import org.apache.ddlutils.DatabaseOperationException;


/**
 * Convenient class to manage database ddl with ddlutils package
 *
 * @author Siddhartha Basu
 */
public final class DDLManager {

    private File xmlSchema;
    private Database dbmodel;

    /**
     * Constuctor
     * @param schema is a File with xml schema definition
     */
    public DDLManager(File schema) {
        xmlSchema = schema;
        dbmodel = generateModel(xmlSchema);
    }

    private Database generateModel(File schema) throws DdlUtilsException {
        System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
        DatabaseIO io = new DatabaseIO();
        io.setValidateXml(false);
        io.setUseInternalDtd(true);
        return io.read(xmlSchema);
    }

    public Database getModel() {
        return this.dbmodel;
    }

    /**
     * Generate DDL from xml schema file
     * @param output is the output File
     */
    public void generateDDL(File output) throws IOException {
        PostgreSqlBuilder builder = new PostgreSqlBuilder(new PostgreSqlPlatform());
        builder.setWriter(new FileWriter(output));
        builder.createTables(this.getModel(), true);
    }

    /**
     * Transform and load xml schema in the given database
     * @param connection is the connection to database
     */
    public void loadSchema(Connection connection) throws DatabaseOperationException {
        PostgreSqlPlatform platform = new PostgreSqlPlatform();
        platform.createTables(connection, this.getModel(), false , false);
    }
}
