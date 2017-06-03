package io.github.andikanugraha11.wherewego.Model;

/**
 * Created by andika on 03/06/17.
 */

public class ModelGetPlace {


    String name, address, author, description;

    public  ModelGetPlace(){

    }

    public  ModelGetPlace(String name, String address, String author, String description){
        this.name = name;
        this.address = address;
        this.author = author;
        this.description = description;
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
}
