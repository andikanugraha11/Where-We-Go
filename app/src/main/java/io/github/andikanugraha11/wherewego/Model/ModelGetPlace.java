package io.github.andikanugraha11.wherewego.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by andika on 03/06/17.
 */

public class ModelGetPlace {


    String name, address, author, description;
    private HashMap<String, Object> location ,images = new HashMap<String, Object>();
    public  ModelGetPlace(){

    }



    public  ModelGetPlace(String name, String address, String author, String description, HashMap<String, Object> location,  HashMap<String, Object> images){
        this.name = name;
        this.address = address;
        this.author = author;
        this.description = description;
        this.location = location;
        this.images = images;

    }

    public HashMap<String, Object> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Object> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Object> getImages() {
        return images;
    }

    public void setImages(HashMap<String, Object> images) {
        this.images = images;
    }
}