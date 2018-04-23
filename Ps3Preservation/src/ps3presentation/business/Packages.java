/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3presentation.business;

import ps3presentation.business.GenericDataClass;
import java.util.ArrayList;
import ps3preservation.data.Ps3SQLDatabase;

/**
 *
 * @author Marin
 */
public class Packages extends GenericDataClass {

    private int id; //not null
    private String name; //not null
    private String filename; //not null
    private String source_url; //DEFAULT -> NULL
    private PackageType package_type; //DEFAULT -> NULL
    private Type type; //not null
    private PackageChannel package_channel; //not null
    private double version; //not null
    private double sys_version; //not null
    private int size; //not null
    private byte[] hash; //not null
    private int license_id; //DEFAULT -> NULL
    private byte verified; //not null
    private static final String[] COLUMN_NAMES = {"filename", "hash", "id", "license_id", "name", "type", "package_type", "size", "source_url", "sys_version", "package_channel", "verified", "version"};
    private Ps3SQLDatabase database;

    public Packages(int id, String name, String filename, String source_url, String package_type, String type, String package_channel, double sys_version, double version, int size, byte[] hash, int license_id, byte verified) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.source_url = source_url;
        enumHandler(package_type);
        enumHandler(type);
        enumHandler(package_channel);
        this.version = version;
        this.sys_version = sys_version;
        this.size = size;
        this.hash = hash;
        this.license_id = license_id;
        this.verified = verified;
        super.populateAttributeList();
    }

    public Packages(int id, Ps3SQLDatabase database) {
        this.id = id;
        this.database = database;
    }

    public boolean getPackageData() {
        boolean returned = false;
        ArrayList<ArrayList<String>> results = database.getData("select * from "
                + "packages where id = " + id);
        if (results.size() > 0) {
            returned = true;
            this.name = results.get(0).get(1);
            this.filename = results.get(0).get(2);
            this.source_url = results.get(0).get(3);
            enumHandler(results.get(0).get(4));
            enumHandler(results.get(0).get(5));
            enumHandler(results.get(0).get(6));
            this.version = Double.parseDouble(results.get(0).get(7));
            this.sys_version = Double.parseDouble(results.get(0).get(8));
            this.size = Integer.parseInt(results.get(0).get(9));
            //this.hash = Integer.parseInt(results.get(0).get(10));
            if (results.get(0).get(11) != null) {
                this.license_id = Integer.parseInt(results.get(0).get(11));
            }
            //this.verified = Integer.parseInt(results.get(0).get(12));

        }
        return returned;
    }

    enum Type {
        UPDATE, SOUNDTRACK, EDAT, THEME, DEMO, DLC, VIDEO, WALLPAPER, MANUAL, AVATAR, CORE
    };

    enum PackageType {
        ISO, PKG
    };

    enum PackageChannel {
        RETAIL, DEBUG
    };

    public void enumHandler(String enumValue) {
        for (Type element : Type.values()) {
            if (element.name().equals(enumValue)) {
                this.type = Type.valueOf(enumValue);
                return;
            } else {
                this.type = Type.DEMO;
            }
        }
        for (PackageType packType : PackageType.values()) {
            if (packType.name().equals(enumValue)) {
                this.package_type = PackageType.valueOf(enumValue);
                return;
            } else {
                this.package_type = PackageType.ISO;
            }
        }
        for (PackageChannel packChann : PackageChannel.values()) {
            if (packChann.name().equals(enumValue)) {
                this.package_channel = PackageChannel.valueOf(enumValue);
                return;
            } else {
                this.package_channel = PackageChannel.DEBUG;
            }
        }
    }

    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public String tableNameGetter() {
        return "packages";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return "" + getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public String getSource_url() {
        return source_url;
    }

    public String getPackage_type() {
        return package_type.name();
    }

    public String getType() {
        return type.name();
    }

    public String getPackage_channel() {
        return package_channel.name();
    }

    public double getVersion() {
        return version;
    }

    public double getSys_version() {
        return sys_version;
    }

    public int getSize() {
        return size;
    }

    public byte[] getHash() {
        return hash;
    }

    public int getLicense_id() {
        return license_id;
    }

    public byte getVerified() {
        return verified;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public void setPackage_type(PackageType package_type) {
        enumHandler(package_type.name());
    }

    public void setType(Type type) {
        enumHandler(type.name());
    }

    public void setPackage_channel(PackageChannel package_channel) {
        enumHandler(package_channel.name());
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public void setSys_version(double sys_version) {
        this.sys_version = sys_version;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setLicense_id(int license_id) {
        this.license_id = license_id;
    }

    public void setVerified(byte verified) {
        this.verified = verified;
    }

}
