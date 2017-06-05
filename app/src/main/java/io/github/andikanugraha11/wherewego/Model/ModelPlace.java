package io.github.andikanugraha11.wherewego.Model;

import java.util.ArrayList;

/**
 * Created by andika on 01/06/17.
 */

public class ModelPlace {

    String name, description, author, address, lat, lng, imagesPrimary, imagesSecondary ;
    public ModelPlace(){

    }

    public  ModelPlace(String images){
        this.imagesPrimary = images;
    }

    public ModelPlace(String name, String description, String author, String address){
        this.name = name;
        this.description = description;
        this.author = author;
        this.address = address;

    }

    public ModelPlace(String lat, String lng){
        this.lat = lat;
        this.lng = lng;
    }

    //public ModelPlace(String gambar1,)
}
