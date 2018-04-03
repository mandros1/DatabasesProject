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
 * @author Marin
 */
public abstract class GenericDataClass {

    private ArrayList<ArrayList<String>> data;
    private HashMap<String, String> values;
    private String query;

    public void fetch(Ps3SQLDatabase database) {
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        if (!data.isEmpty()) {
            setAllTheAttributes(data);
        }
    }
    
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
    
    public boolean existCheckUp(Ps3SQLDatabase database){
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        return data.isEmpty();
    }

     
    public abstract String getTableName();

    public abstract HashMap<String, String> getAllTheAttributes();
    
    public abstract void setAllTheAttributes(ArrayList<ArrayList<String>> array);

    public abstract String getPrimaryKeyName();

    public abstract String getPrimaryKeyValue();
}
