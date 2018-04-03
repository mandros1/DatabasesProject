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
 * Data layer class that connects to the database. This class is the simpler
 * version of what our final data layer class will look like. It handles
 * connection to the database, getting data from the database, setting data in
 * the database and closing connection
 *
 * @author ZG3 team
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
     * Constructor that accepts all the parameters needed for the connection to
     * the database
     *
     * @param protocol Type of protocol the user is using to connect
     * @param database The database the user wants to connect to
     * @param host The host where the server is located
     * @param port The port number on which to connect to the database
     * @param properties The properties of the connection
     * @param username The username of user that is trying to connect
     * @param password The password of the user that is trying to connect
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
     * The connect method that connects the object to the database
     *
     * @return Value that represent if the connection was successful
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
            System.err.println(sql.getMessage());
            System.err.println("Connecting to the database failed, unable to connect to the database.");
        }
        return false;
    }

    /**
     * The close method that closes all the resources and the connection to the
     * database
     *
     * @return Value that represents if the closing of the resources was
     * successful
     */
    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
                statement.close();
                resultSet.close();
                if (conn.isClosed()) {
                    return false;
                }
            }
        } catch (SQLException sql) {
            System.err.println("Closing the connection failed, unable to close the connection to the database.");
        }
        return true;
    }

    /**
     * Meta data method that gets the information about the database to which
     * the user is connected
     *
     * @return The string containing the information about the database
     */
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
            databaseInformation = String.format("\nDatabase infomration\nProduct name: %s Product version: %s\n"
                    + "Driver version: %s\nTable names: %s\nTable types: %s\n"
                    + "Supports group by statements:%s\nSupports outer joins: %s\n"
                    + "Supports statements pooling: %s\nSupports stored procedures: %s\n", dbmd.getDatabaseProductName(), dbmd.getDatabaseProductVersion(),
                    dbmd.getDriverVersion(), tablesNames, tablesTypes, groups, joins, pooling, stored);

        } catch (SQLException ex) {
            System.err.println("Unable to get information about the database");
        }
        return databaseInformation;
    }

    /**
     * Meta data method that gets the information about a certain table in the
     * database
     *
     * @param tableName The name of the table which information needs to be
     * accesses
     * @return The string containing the information about the table
     */
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
            resultInfo = String.format("\nTable information\nTable name: %s\n"
                    + "%s\nPrimary key: %s\n", tableName, columnInfo, primaryKey);
        } catch (SQLException ex) {
            System.err.println("Getting metadata about table failed");
        }
        return resultInfo;
    }

    /**
     * The method that prepares and returns the statement based on a generic
     * query and its parameters
     *
     * @param statement The statement that needs to be prepared
     * @param parameters The parameters that need to be generated into the
     * statement
     * @return The prepared statement ready for execution
     */
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
     * The get data method that executes a query provided to it and returns data
     * from the database
     *
     * @param query The query that needs to be executed
     * @return Data pulled from the database
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

    /**
     * The get data method that prepares a generic query provided to it, executes it and
     * returns data from the database
     * @param query The query that needs to be executed
     * @param parameters The query's parameters that need to be generated
     * @return The data returned from the database
     */
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

    /**
     * Method that executes a query that inserts data into the database
     * @param query The query that needs to be executed
     * @return Value that represents if the query was successfully executed
     */
    public boolean setData(String query) {
        boolean flag = false;
        int rowsAffected = 0;
        try {
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException sql) {
            System.err.println("Setting data to database failed");
        }
        return rowsAffected > 0;
    }

    /**
     * Method that inserts data into the database using generic query and its
     * parameters
     * @param query The query that need to be executed
     * @param parameters The query's parameters
     * @return Values that represents if the query was successfully executed
     */
    public boolean setData(String query, ArrayList<String> parameters) {
        boolean flag = false;
        PreparedStatement ps = prepareStatement(query, parameters);
        try {
            int columnsAffected = ps.executeUpdate();
            if (columnsAffected > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            System.err.println("Setting data to database failed");
        }

        return flag;
    }

    /**
     * Getter method for the protocol needed for the connection to the database
     * @return The protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Getter method for the database the user wants to connect to
     * @return The database name
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Getter method for the host of the server 
     * @return The host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Getter method for the port number on the server
     * @return The port number
     */
    public String getPort() {
        return port;
    }

    /**
     * Getter method for the properties of the connection
     * @return The properties of the connection
     */
    public String getProperties() {
        return properties;
    }

    /**
     * Getter method for the username value
     * @return The username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for user's password
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

}
