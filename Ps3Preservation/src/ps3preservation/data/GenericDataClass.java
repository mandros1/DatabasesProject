/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.beans.IntrospectionException;
import java.util.ArrayList; 
import java.util.HashMap; 
import ps3preservation.Ps3SQLDatabase;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic, Donat Avdijaj
 */
public abstract class GenericDataClass {

    private ArrayList<ArrayList<String>> data;
    private HashMap<String, String> values;
    private String query;
    private ArrayList<String> attributeList;

    /**
     * Fetch is a method that calls the SELECT statement on the database object that is inherited and used by all the subclasses
     * @param database Ps3SQLDatabase object
     */
    public void fetch(Ps3SQLDatabase database) {
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", tableNameGetter(), primaryKeyNameGetter(), primaryKeyValueGetter());
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
            System.out.println("Cannot execute put() UPDATE: -Data with " + primaryKeyNameGetter() + " primay key and its value of '" + primaryKeyValueGetter()+ "' for PRIMARY key already exist in the database.");
        }else{
            query = String.format("UPDATE %s SET", tableNameGetter());
            values = createAttributeMap();
            
            values.entrySet().forEach((entry) -> {
                query += " " + entry.getKey() + " = '" + entry.getValue() + "',";
            });
            query = query.substring(0, query.length()-1); // to delete the last comma ","
            query += " WHERE " + primaryKeyNameGetter() + " = " + primaryKeyValueGetter() + ";";
            
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
        query = String.format("SELECT * FROM %s WHERE %s= '%s';", tableNameGetter(), primaryKeyNameGetter(), primaryKeyValueGetter());
        data = database.getData(query);
        return data.isEmpty();
    }
    
    /**
     * Declares the ArrayList<String> and populates it with the data from the classes get methods
     * by running through all the getters of the class to which the object belongs to.
     */
    public void populateAttributeList(){
        attributeList = new ArrayList<>();
        try{
            // using PropertyDescriptor we call all the getters from the specified class and get their return values
            for(PropertyDescriptor propertyDescriptor : 
            Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors()){ 
                if( propertyDescriptor.getReadMethod() != null && !propertyDescriptor.getName().equals("class")){  
                    Object obj = propertyDescriptor.getReadMethod().invoke(this); // calls the getMethod (whichever) 
                    // depending on the return type it is casted to String and added to the ArrayList<String> attributeList 
                    if( obj instanceof Integer ){ 
                        attributeList.add(Integer.toString((Integer)obj));
                    }else if( obj instanceof Double ){ 
                        attributeList.add(Double.toString((Double)obj));
                    }else if( obj instanceof byte[] ){
                        try { 
                            attributeList.add(new String( ( byte[])obj, "UTF-8"));
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(GenericDataClass.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        attributeList.add((String)obj); 
                    }
                } 
            }
            setAttributeList(attributeList);
        } catch(IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
                e.printStackTrace();
                // TODO: IMPLEMENT PROPER EXCEPTION CATCHING
        } 
    }
        
        
    /**
     * Generates all the attributes names and values of every class and stores them in the HashMap<NAME, VALUE>
     * @return HashMap with the attribute names and values
     */
    public HashMap<String, String> createAttributeMap() {
        String [] attributeNames = attributeNamesGetter();
        attributeList = attributeListGetter();
        HashMap<String, String> map = new HashMap<>();
        for(int i=0; i<attributeNames.length; i++){
            map.put(attributeNames[i], attributeList.get(i));
        }
        return map;
    }
        
    /**
     * Accessor for the attributeList which holds the values of the attributes
     * @return ArrayList<String> attributeList which holds the attribute values for the specific class
     */
    public ArrayList<String> attributeListGetter(){
        return attributeList;
    }
    
    /**
     * Mutator for the attributeList which holds the values of the attributes
     * @param arrList is the arrayList of String that is set to the attributeList
     */
    public void setAttributeList(ArrayList<String> arrList){
        attributeList = arrList;
    }
    
    /**
     * Accessor for the column names/names of the attributes for specific module
     * @return String array (String []) that contains the names of the columns
     */
    public abstract String[] attributeNamesGetter();
    
    /**
     * Accessor for the table name for specific module
     * @return String value of the table name
     */
    public abstract String tableNameGetter();
 
    
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
    public abstract String primaryKeyNameGetter();

    /**
     * Gets the value of the primary key attribute
     * @return String of the primary key value attribute
     */
    public abstract String primaryKeyValueGetter();
} 