/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.util.ArrayList;

/**
 *
 * @author Marin
 */
public class Software extends GenericDataClass{
    
    private int id; //not null
    private String name; //not null
    private static final String[] COLUMN_NAMES = {"id", "name"};

    /**
     * 
     * @param id
     * @param name 
     */
    public Software(int id, String name) {
        this.id = id;
        this.name = name;
        super.populateAttributeList();
    }
      
    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public String tableNameGetter() {
        return "software";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        setName(array.get(0).get(1));
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return ""+getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
