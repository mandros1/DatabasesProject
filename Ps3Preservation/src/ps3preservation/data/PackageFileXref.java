/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.data;

import java.util.ArrayList; 

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea
 * Cvijanovic,Donat Avdijaj
 */
public class PackageFileXref extends GenericDataClass {

    private int id; //not null
    private int package_id; //not null
    private int file_id; //not null
    private static final String[] COLUMN_NAMES = {"file_id", "id", "package_id"};

    /**
     *
     * @param id
     * @param package_id
     * @param file_id
     */
    public PackageFileXref(int id, int package_id, int file_id) {
        this.id = id;
        this.package_id = package_id;
        this.file_id = file_id;
        super.populateAttributeList();
    }


    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }
    
    @Override
    public String tableNameGetter() {
        return "package_file_xref";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try {
            setFile_id(Integer.getInteger(array.get(0).get(0)));
            setPackage_id(Integer.getInteger(array.get(0).get(2)));

        } catch (NullPointerException npe) {
            // TODO: proper exception handling
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
}
