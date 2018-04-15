/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marin
 */
public class Files extends GenericDataClass {

    private int id; //not null
    private String file_id; //not null
    private byte[] hash; //not null
    private int sector_offset; //default null
    private int size; //not null
    private static final String[] ATTRIBUTE_NAMES = {"id", "file_id", "hash", "sector_offset", "size"}; 

    public static void main(String[] args) {

        Files ff = new Files(3, "123", "342".getBytes(), 3);

    }

    /**
     * Constructor with parameters that sets all the values of our variables
     *
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
        super.populateAttributeList();
    }

    /**
     * Constructor with parameters that sets all the values of our variables
     * except the offset which is allowed to be null
     *
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
        super.populateAttributeList();
    }
 

    @Override 
    public String[] attributeNamesGetter() {
        return ATTRIBUTE_NAMES;
    }

    @Override
    public String tableNameGetter() {
        return "files";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try {
            for (int i = 1; i <= array.get(0).size(); i++) {
                switch (i) {
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
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return "" + getId();
    }

    /**
     * Returns the id of the Files object in the form of the integer
     * @return integer value of the id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return 
     */
    public String getFile_id() {
        return file_id;
    }
    
    /**
     * 
     * @return 
     */
    public byte[] getHash() {
        return hash;
    }
    
    /**
     * 
     * @return 
     */
    public int getSector_offset() {
        return sector_offset;
    }
    
    /**
     * 
     * @return 
     */
    public int getSize() {
        return size;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @param file_id 
     */
    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
    
    /**
     * 
     * @param hash 
     */
    public void setHash(byte[] hash) {
        this.hash = hash;
    }
    
    /**
     * 
     * @param sector_offset 
     */
    public void setSector_offset(int sector_offset) {
        this.sector_offset = sector_offset;
    }
    
    /**
     * 
     * @param size 
     */
    public void setSize(int size) {
        this.size = size;
    }
}
