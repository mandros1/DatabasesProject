package ps3presentation.business;

import ps3presentation.business.GenericDataClass;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic,
 * Donat Avdijaj
 */
public class Licenses extends GenericDataClass {

    private int id; //not null
    private String name; //not null
    private byte[] data; //not null 
    private int user_id; //not null
    private static final String[] COLUMN_NAMES = {"data", "id", "name", "user_id"};

    public Licenses() {
    } 
    
    /**
     * Constructor with parameters 
     * @param id value for id
     * @param name value for name of the licenses
     * @param data byte array of data
     * @param user_id integer value for the user_id
     */
    public Licenses(int id, String name, byte[] data, int user_id) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.user_id = user_id;
        super.populateAttributeList();
    }
    
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array, int pos) {
        setData((array.get(pos).get(2)).getBytes(Charset.forName("UTF-8")));
        setId(Integer.parseInt((array.get(pos).get(0))));
        setName((array.get(pos).get(1)));
        setUser_id(Integer.parseInt((array.get(pos).get(3))));

    }
    
    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        setData((array.get(0).get(0)).getBytes(Charset.forName("UTF-8")));
        setId(Integer.parseInt((array.get(0).get(1))));
        setName((array.get(0).get(3)));
        setUser_id(Integer.parseInt((array.get(0).get(4))));

    }

    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return "" + getId();
    }

    @Override
    public String tableNameGetter() {
        return "licenses";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
