/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.nio.charset.Charset;
import java.util.ArrayList;

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

    public Files(int id, String file_id, byte[] hash, int sector_offset, int size) {
        this.id = id;
        this.file_id = file_id;
        this.hash = hash;
        this.sector_offset = sector_offset;
        this.size = size;
    }
    
    public Files(int id, String file_id, byte[] hash, int size) {
        this.id = id;
        this.file_id = file_id;
        this.hash = hash; 
        this.size = size;
    }

    
     @Override
    public String getTableName() {
        return "files";
    }
     
    
    /**
     * 
     * @param val
     * @return 
     */
    public byte[] stringToByte(String val){
        byte[] b = val.getBytes(Charset.forName("UTF-8"));
        return b;
    }
    
    /**
     * 
     * @param array 
     */
    @Override
    public void setAllValues(ArrayList<ArrayList<String>> array) {
        try{
            for(int i=1; i<=array.get(0).size(); i++){
                switch(i){
                    case 1:
                        setFile_id(array.get(0).get(i));
                        break;
                    case 2:
                        setHash(stringToByte(array.get(0).get(i)));
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
