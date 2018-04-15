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
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic,Donat Avdijaj
 */
public class PackageFileXref extends GenericDataClass {

    private int id; //not null
    private int package_id; //not null
    private int file_id; //not null
    private static final String[] attributeNames = {"id", "package_id", "file_id"};
    private ArrayList<String> attributeList;

    public PackageFileXref(int id, int package_id, int file_id) {
        this.id = id;
        this.package_id = package_id;
        this.file_id = file_id;
    }

    /**
     * Method that goes through all the get methods for our attributes and
     * populates an attributeList Arraylist with data
     */ 
    
     
    
    @Override
    public String tableNameGetter() {
        return "package_file_xref";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try {
            for (int i = 1; i <= array.get(0).size(); i++) {
                switch (i) {
                    case 1:
                        setPackage_id(Integer.getInteger(array.get(0).get(i)));
                        break;
                    case 2:
                        setPackage_id(Integer.getInteger(array.get(0).get(i)));
                        break;
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> createAttributeMap() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attributeNames.length; i++) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getId() {
        return id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public int getFile_id() {
        return file_id;
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
