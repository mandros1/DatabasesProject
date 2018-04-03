/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
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
    public boolean connect(){
        System.out.println("Connecting to the database...");
        
        try{
           String url = getProtocol() + ":" + getDatabase() + "://" + getHost() + ":" + getPort() + "/" + getProperties();
           conn = DriverManager.getConnection(url, username, password);
           try{
               dbmd = conn.getMetaData();
           }catch(SQLException sql){
               System.err.println("SQLException was thrown while trying to create DatabaseMetaData in the connect() method.");
           }try{
               return conn.isValid(0);
           }catch(SQLException sql){
               System.err.println("Connecting to the database failed, timeout provided to isValid method is lower than 0.");
           }
        }catch(SQLException sql){
            System.err.println("Connecting to the database failed, unable to connect to the database.");
        }
        return false;
    }
    
    /**
     * 
     * @return 
     */
    public boolean close(){
        try{
            if(conn != null){
                conn.close();
                if(conn.isClosed()){
                    return false;
                }
            }
        }catch(SQLException sql){
            System.err.println("Closing the connection failed, unable to close the connection to the database.");
        }
        return true;
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    public ArrayList<ArrayList<String>> getData(String query){
        resultData = new ArrayList<>();
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            rsmd = resultSet.getMetaData();
            while(resultSet.next()){
                rowOfData = new ArrayList<>();
                for(int i=1; i<=rsmd.getColumnCount(); i++){
                    rowOfData.add(resultSet.getString(i));
                }
                resultData.add(rowOfData);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        
        return resultData;
    }
    
    public boolean setData(String query){
        int rowsAffected = 0;
        try{
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
        }catch(SQLException sql){
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
