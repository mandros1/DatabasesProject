/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3presentation.business;

import java.util.ArrayList;
import ps3preservation.data.Ps3SQLDatabase;

/**
 * @author Marin
 */
public class Releases extends GenericDataClass {

    private static final String[] COLUMN_NAMES = {"real_id", "software_id", "status"};
    private String real_id; //not null
    private int software_id; //not null
    private Status status; // not null
    private String medium;
    private String rights;
    private String region;
    private String type;
    private Ps3SQLDatabase database;

    public Releases(String real_id, int software_id, String stat) {
        this.real_id = real_id;
        this.software_id = software_id;
        if (stat.equalsIgnoreCase("ALIVE")) {
            this.status = Status.valueOf(stat.toUpperCase());
        } else {
            this.status = Status.valueOf("UNKNOWN");
        }
        super.populateAttributeList();
    }

    public Releases(int id, Ps3SQLDatabase database) {
        System.out.println(id);
        this.software_id = id;
        this.database = database;
    }

    public boolean getReleaseData() {
        boolean returned = false;
        ArrayList<ArrayList<String>> results = database.getData("select * from releases where software_id = "+ software_id);
        if (results.size() > 0) {
            returned = true;
            real_id = results.get(0).get(1);
            if (results.get(0).get(1).equalsIgnoreCase("ALIVE")) {
                this.status = Status.valueOf(results.get(0).get(1).toUpperCase());
            } else {
                this.status = Status.valueOf("UNKNOWN");
            }
        }

        return returned;
    }

    public void calculateMetadata() {
        switch (real_id.charAt(0)) {
            case 'B':
                medium = "Blu-Ray";
                switch (real_id.charAt(1)) {
                    case 'C':
                        rights = "Copyrighted by Sony";
                        break;
                    case 'L':
                        rights = "Licensed to Sony";
                        break;
                    default:
                        rights = "Unknown";
                        break;
                }
                switch (real_id.charAt(2)) {
                    case 'A':
                        region = "Asia";
                        break;
                    case 'E':
                        region = "Europe";
                        break;
                    case 'H':
                        region = "Hong Kong";
                        break;
                    case 'J':
                        region = "Japan";
                        break;
                    case 'K':
                        region = "Korea";
                        break;
                    case 'P':
                        region = "Japan (PS1/PS2)";
                        break;
                    case 'U':
                        region = "USA";
                        break;
                    default:
                        region = "Unknown";
                        break;
                }
                switch (real_id.charAt(3)) {
                    case 'B':
                        type = "Peripheral Software";
                        break;
                    case 'C':
                        type = "System Firmware";
                        break;
                    case 'D':
                        type = "Demo";
                        break;
                    case 'M':
                        type = "Malayan Release";
                        break;
                    case 'S':
                        type = "Retail release";
                        break;
                    case 'T':
                        type = "(closed) Betas";
                        break;
                    case 'V':
                        type = "Multi Region PS3 CS disc";
                        break;
                    case 'X':
                        type = "Install disc? (demos)";
                        break;
                    case 'Z':
                        type = "Region locked PS3 CS disc";
                        break;
                    default:
                        type = "Unknown";
                        break;
                }
                break;
            case 'N':
                medium = "PSN";
                rights = "Unknown";
                switch (real_id.charAt(2)) {
                    case 'A':
                        region = "Asia";
                        break;
                    case 'E':
                        region = "Europe";
                        break;
                    case 'H':
                        region = "Hong Kong";
                        break;
                    case 'J':
                        region = "Japan";
                        break;
                    case 'K':
                        region = "Korea";
                        break;
                    case 'U':
                        region = "USA";
                        break;
                    case 'I':
                        region = "Internal ? (Sony) ";
                        break;
                    case 'X':
                        region = "Firmware/SDK Sample";
                        break;
                    default:
                        region = "Unknown";
                        break;
                }
                switch (real_id.charAt(3)) {
                    case 'A':
                        type = "First Party PS3 (Demo/Retail)";
                        break;
                    case 'B':
                        type = "Licensed PS3 (Demo/Retail)";
                        break;
                    case 'C':
                        type = "First Party PS2 Classic (Demo/Retail)";
                        break;
                    case 'D':
                        type = "Licensed PS2 Classic (Demo/Retail)";
                        break;
                    case 'E':
                        type = "Licensed PS1 Classic (PAL) (Demo/Retail)";
                        break;
                    case 'F':
                        type = "First Party PS1 Classic (PAL) (Demo/Retail)";
                        break;
                    case 'G':
                        type = "First Party PSP (Demo/Retail)";
                        break;
                    case 'H':
                        type = "Licensed PSP (Demo/Retail)";
                        break;
                    case 'I':
                        type = "First Party PS1 Classic (NTSC) (Demo/Retail)";
                        break;
                    case 'J':
                        type = "Licensed PS1 Classic (NTSC) (Demo/Retail)";
                        break;
                    case 'K':
                        type = "First Party Game related Content";
                        break;
                    case 'L':
                        type = "Licensed Game related Content";
                        break;
                    case 'M':
                        type = "Music";
                        break;
                    case 'N':
                        type = "Game Soundtracks";
                        break;
                    case 'O':
                        type = "Other";
                        break;
                    case 'P':
                        type = "?";
                        break;
                    case 'Q':
                        type = "?";
                        break;
                    case 'R':
                        type = "?";
                        break;
                    case 'S':
                        type = "System";
                        break;
                    case 'T':
                        type = "?";
                        break;
                    case 'U':
                        type = "?";
                        break;
                    case 'V':
                        type = "?";
                        break;
                    case 'W':
                        type = "First Party PSP Remasters";
                        break;
                    case 'X':
                        type = "First Party PSP Minis";
                        break;
                    case 'Y':
                        type = "Third Party PSP Remasters";
                        break;
                    case 'Z':
                        type = "Third Party PSP minis";
                        break;
                    default:
                        type = "Unknown";
                        break;
                }
                break;
            default:
                medium = "Unknown";
                rights = "Unknown";
                region = "Unknown";
                type = "Unknown";
                break;
        }
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
        return getReal_id();
    }

    public String getReal_id() {
        return real_id;
    }

    ;

    public void setReal_id(String real_id) {
        this.real_id = real_id;
    }

    public int getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private enum Status {
        ALIVE, UNKNOWN
    }

}
