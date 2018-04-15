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
public class ReleasePackageXref extends GenericDataClass {

    private int id;
    private int package_id; //not null
    private int license_id; //not null
    private static final String[] COLUMN_NAMES = {"id", "license_id", "package_id"};

    public ReleasePackageXref(int id, int package_id, int license_id) {
        this.id = id;
        this.package_id = package_id;
        this.license_id = license_id;
        super.populateAttributeList();
    }

    @Override
    public String tableNameGetter() {
        return "package_file_xref";
    }

    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        setPackage_id(Integer.getInteger(array.get(0).get(1)));
        setPackage_id(Integer.getInteger(array.get(0).get(2)));
    }

    @Override
    public String primaryKeyNameGetter() {
        return "package_id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return "" + getPackage_id();
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public void setLicense_id(int license_id) {
        this.license_id = license_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public int getLicense_id() {
        return license_id;
    }

    public int getId() {
        return id;
    }
}
