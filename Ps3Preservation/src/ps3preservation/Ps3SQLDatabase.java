/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marin
 */
public class Ps3SQLDatabase {

    // variables used to define the url path to our database and its tables
    private String protocol;
    private String database;
    private String host;
    private String port;
    private String properties;
    // username and password are used to connect to the database
    private String username;
    private String password;

    private Connection conn;
    private Statement statement;
    private DatabaseMetaData dbmd;
    private ResultSet resultSet;
    private ResultSetMetaData rsmd;
    private ArrayList<ArrayList<String>> resultData;
    private ArrayList<String> rowOfData;

    /**
     *
     * @param protocol
     * @param database
     * @param host
     * @param port
     * @param properties
     * @param username
     * @param password
     */
    public Ps3SQLDatabase(String protocol, String database, String host, String port, String properties, String username, String password) {
        this.protocol = protocol;
        this.database = database;
        this.host = host;
        this.port = port;
        this.properties = properties;
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public boolean connect() {
        System.out.println("Connecting to the database...");

        try {
            String url = getProtocol() + ":" + getDatabase() + "://" + getHost() + ":" + getPort() + "/" + getProperties();
            conn = DriverManager.getConnection(url, username, password);
            try {
                dbmd = conn.getMetaData();
            } catch (SQLException sql) {
                System.err.println("SQLException was thrown while trying to create DatabaseMetaData in the connect() method.");
            }
            try {
                return conn.isValid(0);
            } catch (SQLException sql) {
                System.err.println("Connecting to the database failed, timeout provided to isValid method is lower than 0.");
            }
        } catch (SQLException sql) {
            System.err.println("Connecting to the database failed, unable to connect to the database.");
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
                if (conn.isClosed()) {
                    return false;
                }
            }
        } catch (SQLException sql) {
            System.err.println("Closing the connection failed, unable to close the connection to the database.");
        }
        return true;
    }

    public String getDatabaseInformation() {
        String databaseInformation = "";
        try {
            resultSet = dbmd.getTables(null, null, null, null);
            String tablesNames = "";
            while (resultSet.next()) {
                tablesNames += resultSet.getString(3) + " ";
            }
            resultSet = dbmd.getTableTypes();
            String tablesTypes = "";
            while (resultSet.next()) {
                tablesTypes += resultSet.getString(1) + " ";
            }
            String groups = "No", joins = "No", pooling = "No", stored = "No";
            if (dbmd.supportsGroupBy()) {
                groups = "Yes";
            }
            if (dbmd.supportsOuterJoins()) {
                joins = "Yes";
            }
            if (dbmd.supportsStatementPooling()) {
                pooling = "Yes";
            }
            if (dbmd.supportsStoredProcedures()) {
                stored = "Yes";
            }
            databaseInformation = String.format("Database infomration\nProduct name: %s Product version: %s\n"
                    + "Driver version: %s\nTable names: %s\nTable types: %s\n"
                    + "Supports group by statements:%s\nSupports outer joins: %s\n"
                    + "Supports statements pooling: %s\nSupports stored procedures: %s", dbmd.getDatabaseProductName(), dbmd.getDatabaseProductVersion(),
                    dbmd.getDriverVersion(), tablesNames, tablesTypes, groups, joins, pooling, stored);

        } catch (SQLException ex) {
            System.err.println("Unable to get information about the database");
        }
        return databaseInformation;
    }

    public String getTableInformation(String tableName) {
        String resultInfo = "";
        try {
//            dbmd = conn.getMetaData();
            resultSet = dbmd.getColumns(null, null, tableName, null);
            int columnCount = 0;
            String columnInfo = "";
            while (resultSet.next()) {
                columnCount++;
                columnInfo += "Column #" + columnCount + " Column name: " + resultSet.getString(4)
                        + " \tColumn type: " + +resultSet.getInt(5) + "\n";
            }
            String primaryKey = "No primary key";
            resultSet = dbmd.getPrimaryKeys(null, null, tableName);

            while (resultSet.next()) {
                primaryKey = resultSet.getString("COLUMN_NAME");
            }
            resultInfo = String.format("Table information\nTable name: %s\n"
                    + "%s\nPrimary key: %s", tableName, columnInfo, primaryKey);
        } catch (SQLException ex) {
            System.err.println("Getting metadata about table failed");
        }
        return resultInfo;
    }
    
     public PreparedStatement prepareStatement(String statement, ArrayList<String> parameters) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(statement);
            int parameterIndex = 1;
            for (String par : parameters) {
                ps.setString(parameterIndex, par);
                parameterIndex++;
            }
        } catch (SQLException ex) {
            System.err.println("Preparing statements failed");
        }

        return ps;

    }


    /**
     *
     * @param query
     * @return
     */
    public ArrayList<ArrayList<String>> getData(String query) {
        resultData = new ArrayList<>();
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                rowOfData = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    rowOfData.add(resultSet.getString(i));
                }
                resultData.add(rowOfData);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return resultData;
    }

    public ArrayList<ArrayList<String>> getData(String query, ArrayList<String> parameters) {
        resultData = new ArrayList<>();
        PreparedStatement ps = prepareStatement(query, parameters);
        try {
            resultSet = ps.executeQuery();
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                rowOfData = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    rowOfData.add(resultSet.getString(i));
                }
                resultData.add(rowOfData);
            }
        } catch (SQLException ex) {
            System.err.println("Getting data from database failed");
        }
        return resultData;
    }
    
    public boolean setData(String query) {
        int rowsAffected = 0;
        try {
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return rowsAffected > 0;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getProperties() {
        return properties;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
