package org.dictybase.modelproduction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.intermine.metadata.Model;

import org.apache.commons.io.IOUtils;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import org.dictybase.sql.Database;

/**
 * Class to handle persistence of an intermine objectstore's metadata to the objectstore's database
 * @author Mark Woodbridge
 */
public final class MetadataManager
{
    private MetadataManager() {
    }

    /**
     * Name of the metadata table (created by TorqueModelOutput)
     */
    public static final String METADATA_TABLE = "intermine_metadata";

    /**
     * Name of the key under which to store the serialized version of the model
     */
    public static final String MODEL = "model";

    /**
     * Name of the key under which to store the serialized version of the key definitions
     */
    public static final String KEY_DEFINITIONS = "keyDefs";

    /**
     * The name of the key to use to store the class_keys.properties file.
     */
    public static final String CLASS_KEYS = "class_keys";

    /**
     * The name of the key to use to store the objectstoresummary.properties file.
     */
    public static final String OS_SUMMARY = "objectStoreSummary";

    /**
     * The name of the key to use to store the autocomplete RAMIndexes.
     */
    public static final String AUTOCOMPLETE_INDEX = "autocomplete";

    /**
     * The name of the key to use to store the search index.
     */
    public static final String SEARCH_INDEX = "search";

    /**
     * The name of the key to use to store the search Directory.
     */
    public static final String SEARCH_INDEX_DIRECTORY = "search_directory";
    /**
     * Name of the key under which to store the serialized version of the class descriptions
     */
    //public static final String CLASS_DESCRIPTIONS = "classDescs";
    /**
     * The name of the key used to store objectstore format version number.
     */
    public static final String OS_FORMAT_VERSION = "osversion";

    /**
     * The name of the key used to store profile format version.
     */
    public static final String PROFILE_FORMAT_VERSION = "profileversion";

    /**
     * The name of the key used to store the truncated classes string.
     */
    public static final String TRUNCATED_CLASSES = "truncatedClasses";

    /**
     * The name of the key used to store the missing tables string.
     */
    public static final String MISSING_TABLES = "missingTables";

    /**
     * The name of the key used to store the noNotXml string.
     */
    public static final String NO_NOTXML = "noNotXml";

    /**
     * The name of the key used to store the modMine MetaData cache
     */
    public static final String MODMINE_METADATA_CACHE = "modMine_metadata_cache";

    /**
     * The name of the key used to store the serial number identifying the production db
     */
    public static final String SERIAL_NUMBER = "serialNumber";

    /**
     * Description of range type columns defined in the database.
     */
    public static final String RANGE_DEFINITIONS = "rangeDefinitions";

    /**
     * Store a (key, value) pair in the metadata table of the database
     * @param database the database
     * @param key the key
     * @param value the value
     * @throws SQLException if an error occurs
     */
    public static void store(Database database, String key, String value) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement insert = null, delete = null;
        boolean autoCommit = connection.getAutoCommit();
        String removeExisting = "DELETE FROM " + METADATA_TABLE + " where key = ?";
        String insertNew = "INSERT INTO " + METADATA_TABLE + " (key, value) " + " VALUES (?,?)";
        try {
            connection.setAutoCommit(false);
            delete = connection.prepareStatement(removeExisting);
            delete.setString(1, key);
            delete.executeUpdate();
            if (value != null) {
                insert = connection.prepareStatement(insertNew);
                insert.setString(1,  key);
                insert.setString(2, value);
                insert.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {

            if (insert != null) {
                insert.close();
            }
            if (delete != null) {
                delete.close();
            }
            if (connection != null) {
                connection.setAutoCommit(autoCommit);
                connection.close();
            }
        }
    }
}
