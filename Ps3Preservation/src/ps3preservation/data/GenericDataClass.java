/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.util.ArrayList;
import java.util.HashMap; 
import ps3preservation.Ps3SQLDatabase;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic, Donat Avdijaj
 */
public abstract class GenericDataClass {

    private ArrayList<ArrayList<String>> data;
    private HashMap<String, String> values;
    private String query;

    /**
     * Fetch is a method that calls the SELECT statement on the database object that is inherited and used by all the subclasses
     * @param database Ps3SQLDatabase object
     */
    public void fetch(Ps3SQLDatabase database) {
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        if (!data.isEmpty()) {
            setAllTheAttributes(data);
        }
    }
    
    /**
     * Fetch is a method that calls UPDATE method which is inherited and used by all the subclasses
     * @param database Ps3SQLDatabase object
     */
    public void put(Ps3SQLDatabase database) {
        if(existCheckUp(database)){
            System.out.println("Cannot execute put() UPDATE: -Data with " + getPrimaryKeyName() + " primay key and its value of '" + getPrimaryKeyValue()+ "' for PRIMARY key already exist in the database.");
        }else{
            query = String.format("UPDATE %s SET", getTableName());
            values = getAllTheAttributes();
            
            values.entrySet().forEach((entry) -> {
                query += " " + entry.getKey() + " = '" + entry.getValue() + "',";
            });
            query = query.substring(0, query.length()-1); // to delete the last comma ","
            query += " WHERE " + getPrimaryKeyName() + " = " + getPrimaryKeyValue() + ";";
            
            boolean result = database.setData(query);
            if (result) {
                System.out.println("Table update was executed successfully!");
            }
        }
    } 
    
    /**
     * Checks if the data with the specific key already exists in the database by calling the SELECT method on the database
     * @param database Ps3SQLDatabase object 
     * @return true if there there is no data for the specified key, false if there is data
     */
    public boolean existCheckUp(Ps3SQLDatabase database){
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        return data.isEmpty();
    }

    
    /**
     * gets the table name for each module
     * @return String value of the table name
     */
    public abstract String getTableName();

    /**
     * Generates all the attributes names and values of every class and stores them in the HashMap<NAME, VALUE>
     * @return HashMap with the attribute names and values
     */
    public abstract HashMap<String, String> getAllTheAttributes();
    
    
    /**
     * Uses setters of the specific subclass and setting attributes to the values that were fetched from the database
     * by using previously defined setters of the subclass
     * @param array holds all the values that are to be used in the setters
     */
    public abstract void setAllTheAttributes(ArrayList<ArrayList<String>> array);

    /**
     * Gets the name of the primary key attribute
     * @return String of the primary key name attribute
     */
    public abstract String getPrimaryKeyName();

    /**
     * Gets the value of the primary key attribute
     * @return String of the primary key value attribute
     */
    public abstract String getPrimaryKeyValue();
} 