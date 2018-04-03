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
public class PackageLicenseXref extends GenericDataClass {

    private int package_id; //not null
    private int license_id; //not null
    private static final String[] attributeNames = {"package_id", "license_id"};
    private ArrayList<String> attributeList;

    public PackageLicenseXref(int package_id, int license_id) {
        this.package_id = package_id;
        this.license_id = license_id;
    }

    /**
     * Method that goes through all the get methods for our attributes and
     * populates an attributeList Arraylist with data
     */
    public void populateAttributeList() {
        attributeList = new ArrayList<>();
        attributeList.add("" + getPackage_id());
        attributeList.add("" + getLicense_id());
    }

    @Override
    public String getTableName() {
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
    public HashMap<String, String> getAllTheAttributes() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attributeNames.length; i++) {
            map.put(attributeNames[i], attributeList.get(i));
        }
        return map;
    }

    @Override
    public String getPrimaryKeyName() {
        return "package_id";
    }

    @Override
    public String getPrimaryKeyValue() {
        return "" + getPackage_id();
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public void setLicense_id(int license_id) {
        this.license_id = license_id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public int getLicense_id() {
        return license_id;
    }

}
