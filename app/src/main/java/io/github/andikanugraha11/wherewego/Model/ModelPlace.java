package io.github.andikanugraha11.wherewego.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andika on 01/06/17.
 */

public class ModelPlace {

    String name, description, author, address, lat, lng;
    HashMap<String, String> images = new HashMap<String, String>();
    public ModelPlace(){

    }

//    public  ModelPlace(HashMap<String, String> images){
//
//        this.images = images;
//    }

    public ModelPlace(String name, String description, String author, String address, HashMap<String, String> images){
        this.name = name;
        this.description = description;
        this.author = author;
        this.address = address;
        this.images = images;
    }

    public ModelPlace(String lat, String lng){
        this.lat = lat;
        this.lng = lng;
    }

    //public ModelPlace(String gambar1,)
}

