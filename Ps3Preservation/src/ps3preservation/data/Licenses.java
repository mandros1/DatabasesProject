/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic, Donat Avdijaj
 */
public class Licenses extends GenericDataClass{
      
    
    private int id; //not null
    private String name; //not null
    private byte[] data; //not null 
    private int user_id; //not null
    private static final String[] attributeNames = {"id", "name", "data", "user_id"};
    private ArrayList<String> attributeList;

    /**
     * Constructor with parameters
     * @param id value for id
     * @param name value for name of the licenses
     * @param data byte array of data
     * @param user_id  int value for the user_id
     */
    public Licenses(int id, String name, byte[] data, int user_id) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.user_id = user_id;
        populateAttributeList();
    }
    
    /**
     * Method that goes through all the get methods for our attributes and populates an attributeList Arraylist with data
     */
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
                        setData(array.get(0).get(i).getBytes(Charset.forName("UTF-8")));
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
    public HashMap<String, String> createAttributeMap() {
        HashMap<String, String> map = new HashMap<>();
        for(int i=0; i<attributeNames.length; i++){
            map.put(attributeNames[i], attributeList.get(i));
        }
        return map;
    }
  
    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }
    
    @Override
    public String primaryKeyValueGetter() {
        return "" + getId();
    }
    
    @Override
    public String tableNameGetter() {
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

    @Override
    public ArrayList<String> attributeListGetter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttributeList(ArrayList<String> arrList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] attributeNamesGetter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
