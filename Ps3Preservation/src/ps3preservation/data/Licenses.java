/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Marin
 */
public class Licenses extends GenericDataClass{
    
    private int id; //not null
    private String name; //not null
    private byte[] data; //not null 
    private int user_id; //not null
    private static final String[] attributeNames = {"id", "name", "data", "user_id"};
    private ArrayList<String> attributeList;

    public Licenses(int id, String name, byte[] data, int user_id) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.user_id = user_id;
        populateAttributeList();
    }
    
    public void populateAttributeList(){
        attributeList = new ArrayList<>();
        attributeList.add(""+getId());
        attributeList.add(getName());
        attributeList.add(Arrays.toString(getData()));
        attributeList.add(""+getUser_id());
    }
    
     @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try{
            for(int i=1; i<=array.get(0).size(); i++){
                switch(i){
                    case 1:
                        setName(array.get(0).get(i));
                        break;
                    case 2:
                        setData(array.get(0).get(i).getBytes());
                        break;
                    case 3:
                        setUser_id(Integer.getInteger(array.get(0).get(i)));
                        break; 
                }
            }
        }catch(NullPointerException npe){ 
            npe.printStackTrace(); 
        }
    }
    
    @Override
    public HashMap<String, String> getAllTheAttributes() {
        HashMap<String, String> map = new HashMap<>();
        for(int i=0; i<attributeNames.length; i++){
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
    
    @Override
    public String getTableName() {
        return "licenses";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    } 

}
