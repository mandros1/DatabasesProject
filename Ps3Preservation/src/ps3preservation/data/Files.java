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
 * @author Marin
 */
public class Files extends GenericDataClass{
    private int id; //not null
    private String file_id; //not null
    private byte[] hash; //not null
    private int sector_offset; //default null
    private int size; //not null
    private static final String[] attributeNames = {"id", "file_id", "hash", "sector_offset", "size"};
    private ArrayList<String> attributeList;
    
    
    /**
     *  Constructor with parameters that sets all the values of our variables
     * @param id int value for id
     * @param file_id String value for id
     * @param hash byte array of the hash
     * @param sector_offset int value of the offset
     * @param size int value of the file size
     */
    public Files(int id, String file_id, byte[] hash, int sector_offset, int size) {
        this.id = id;
        this.file_id = file_id;
        this.hash = hash;
        this.sector_offset = sector_offset;
        this.size = size;
        populateAttributeList();
    }
    
    /**
     *  Constructor with parameters that sets all the values of our variables except the offset which is allowed to be null
     * @param id int value for id
     * @param file_id String value for id
     * @param hash byte array of the hash 
     * @param size int value of the file size
     */
    public Files(int id, String file_id, byte[] hash, int size) {
        this.id = id;
        this.file_id = file_id;
        this.hash = hash; 
        this.size = size;
        populateAttributeList();
    }
    
    
    /**
     * Method that goes through all the get methods for our attributes and populates an attributeList Arraylist with data
     */
    public void populateAttributeList(){
        attributeList = new ArrayList<>();
        attributeList.add(""+getId());
        attributeList.add(getFile_id());
        attributeList.add(Arrays.toString(getHash()));
        attributeList.add(""+getSize());
    }

    
     @Override
    public String getTableName() {
        return "files";
    }
       
    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try{
            for(int i=1; i<=array.get(0).size(); i++){
                switch(i){
                    case 1:
                        setFile_id(array.get(0).get(i));
                        break;
                    case 2:
                        setHash((array.get(0).get(i)).getBytes(Charset.forName("UTF-8")));
                        break;
                    case 3:
                        setSector_offset(Integer.getInteger(array.get(0).get(i)));
                        break;
                    case 4:
                        setSize(Integer.getInteger(array.get(0).get(i)));
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
    
    public void setId(int id) {
        this.id = id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSector_offset(int sector_offset) {
        this.sector_offset = sector_offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getFile_id() {
        return file_id;
    }

    public byte[] getHash() {
        return hash;
    }

    public int getSector_offset() {
        return sector_offset;
    }

    public int getSize() {
        return size;
    }  
}
