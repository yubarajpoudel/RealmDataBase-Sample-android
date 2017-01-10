package com.mantraideas.realmdatabase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mantraideas on 1/10/17.
 */
public class Contacts extends RealmObject {


    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey
    private long id;

    private String email;

    private String imageUrl;

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    private String Designation;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
