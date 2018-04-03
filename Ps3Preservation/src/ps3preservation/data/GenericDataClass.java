/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.sql.SQLException;
import java.util.ArrayList;
import ps3preservation.Ps3SQLDatabase;

/**
 *
 * @author Marin
 */
public abstract class GenericDataClass {

    private ArrayList<ArrayList<String>> data;

    public void fetch(Ps3SQLDatabase database) {
        String query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        if (!data.isEmpty()) {
            setAllValues(data);
        }
    }
    
    public void put(Ps3SQLDatabase database) {
        if(existCheckUp(database)){
            System.out.println("Cannot execute put() UPDATE: -Data with " + getPrimaryKeyName() + " primay key and its value of '" + getPrimaryKeyValue()+ "' for PRIMARY key already exist in the database.");
        }else{
            result = mySQL.setData(String.format("UPDATE equipment SET EquipmentName = '%s', EquipmentDescription = '%s', "
                    + "EquipmentCapacity = %s WHERE EquipID = %s;",
                    getEquipmentName(), getEqipmentDescription(), getEquipmentCapacity(), getEquipmentID()));
            if (result) {
                System.out.println("Table update was executed successfully!");
            }
        }
    } 
    
    public boolean existCheckUp(Ps3SQLDatabase database){
        String query = String.format("SELECT * FROM %s WHERE %s= '%s';", getTableName(), getPrimaryKeyName(), getPrimaryKeyValue());
        data = database.getData(query);
        return data.isEmpty();
    }

    public abstract String getTableName();

    public abstract void setAllValues(ArrayList<ArrayList<String>> array);

    public abstract String getPrimaryKeyName();

    public abstract String getPrimaryKeyValue();
}
