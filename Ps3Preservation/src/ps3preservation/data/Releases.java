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
public class Releases extends GenericDataClass {

    private String id; //not null
    private int software_id; //not null
    private Status status; // not null 
    private static final String[] COLUMN_NAMES = {"id", "software_id", "status"};

    public Releases(String id, int software_id, String stat) {
        this.id = id;
        this.software_id = software_id;
        if (stat.equalsIgnoreCase("ALIVE")) {
            this.status = Status.valueOf(stat.toUpperCase());
        } else {
            this.status = Status.valueOf("UNKNOWN");
        }
        super.populateAttributeList();
    }

    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public String tableNameGetter() {
        return "releases";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        setSoftware_id(Integer.parseInt(array.get(0).get(1)));
        setStatus(Status.valueOf(array.get(0).get(2)));
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return getId();
    }

    private enum Status {
        ALIVE, UNKNOWN
    };

    public String getId() {
        return id;
    }

    public int getSoftware_id() {
        return software_id;
    }

    public String getStatus() {
        return status.name();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
