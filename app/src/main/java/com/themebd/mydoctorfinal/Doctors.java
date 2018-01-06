package com.themebd.mydoctorfinal;

/**
 * Created by arif on 01-Nov-17.
 */

public class Doctors {

    public String name;
    public String category;
    public Boolean online;
    public String imageUrl;
    public String id;

    public Doctors(){}

    public Doctors(String name,String category,String imageUrl){
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
