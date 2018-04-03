/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marin
 */
public class Releases extends GenericDataClass{
    
    
    private int id; //not null
    private int software_id; //not null
    private static final String[] attributeNames = {"id", "software_id", "status"};
    private ArrayList<String> attributeList;
   
    public Releases(int id, int software_id) {
        this.id = id;
        this.software_id = software_id;
    }
    
    /**
     * Method that goes through all the get methods for our attributes and
     * populates an attributeList ArrayList with data
     */
    public void populateAttributeList() {
        attributeList = new ArrayList<>();
        attributeList.add("" + getId());
        attributeList.add("" + getSoftware_id());
        // attributeList.add(status.ACTIVE);  --> IMPLEMENTATION enum <-----> database
    }
    @Override
    public String getTableName() {
        return "releases";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try {
            for (int i = 1; i <= array.get(0).size(); i++) {
                switch (i) {
                    case 1:
                        setSoftware_id(Integer.getInteger(array.get(0).get(i)));
                        break;
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> getAllTheAttributes() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attributeNames.length; i++) {
            map.put(attributeNames[i], attributeList.get(i));
        }
        return map;
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public String getPrimaryKeyValue() {
        return "" + getId();
    }
    
     private enum status{
         ACTIVE, UNKNOWN;  
     }

    public int getId() {
        return id;
    }

    public int getSoftware_id() {
        return software_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }
     
}
