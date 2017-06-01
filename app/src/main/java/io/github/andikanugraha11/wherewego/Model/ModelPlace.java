package io.github.andikanugraha11.wherewego.Model;

/**
 * Created by andika on 01/06/17.
 */

public class ModelPlace {

    String nama, deskripsi, author, latLng;
    public ModelPlace(){

    }

    public ModelPlace(String nama, String deskripsi, String author, String latLng){
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.author = author;
        this.latLng = latLng;
    }
}
