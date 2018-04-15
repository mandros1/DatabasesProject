package ps3preservation.data;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea Cvijanovic, Donat Avdijaj
 */
public class Licenses extends GenericDataClass {

    private int id; //not null
    private String name; //not null
    private byte[] data; //not null 
    private int user_id; //not null
    private static final String[] COLUMN_NAMES = {"data", "id", "name", "user_id"};

    /**
     * Constructor with parameters
     *
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

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        try {
            for (int i = 0; i <= array.get(0).size(); i++) {
                switch (i) {
                    case 0:
                        setData((array.get(0).get(i)).getBytes(Charset.forName("UTF-8")));
                        break;
                    case 1:
                        setId(Integer.parseInt((array.get(0).get(i))));
                        break;
                    case 3:
                        setName((array.get(0).get(i)));
                        break;
                    case 4:
                        setUser_id(Integer.parseInt((array.get(0).get(i))));
                        break;
                }
            }
        } catch (NullPointerException npe) {
            //TODO: implement proper exception catching block   
            npe.printStackTrace();
        }
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
