/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3presentation.business;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import ps3preservation.data.Ps3SQLDatabase;

import javax.imageio.ImageIO;

/**
 *
 * @author Marin
 */
public class Software extends GenericDataClass{
    
    private int id; //not null
    private String name; //not null
    private static final String[] COLUMN_NAMES = {"id", "name"};
    private Ps3SQLDatabase database;
    private BufferedImage bufImg;

    public BufferedImage getBufImg() {
        return bufImg;
    }

    public void setBufImg(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }
    public void setBufImg(String data) {
        try {
            bufImg = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(data.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id
     * @param name 
     */
    public Software(int id, String name) {
        this.id = id;
        this.name = name;
        super.populateAttributeList();
    }
    public Software(Ps3SQLDatabase database){
        this.database = database;
    }
    public ArrayList getAllGames(String nameCondition){
        ArrayList<ArrayList<String>>results = database.getData("select * from software where name like '%"+nameCondition+"%'");
        return results;
    }
    public ArrayList getAllGames(String nameCondition, int offset){
        ArrayList<ArrayList<String>>results = database.getData("select * from software where name like '%"+nameCondition+"%' LIMIT " + offset + ", 25");
        return results;
    }
    @Override
    public String[] attributeNamesGetter() {
        return COLUMN_NAMES;
    }

    @Override
    public String tableNameGetter() {
        return "software";
    }

    @Override
    public void setAllTheAttributes(ArrayList<ArrayList<String>> array) {
        setName(array.get(0).get(1));
setBufImg(array.get(0).get(2));
    }

    @Override
    public String primaryKeyNameGetter() {
        return "id";
    }

    @Override
    public String primaryKeyValueGetter() {
        return ""+getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
