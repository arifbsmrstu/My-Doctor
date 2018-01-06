package com.themebd.mydoctorfinal;

/**
 * Created by arif on 07-Nov-17.
 */

public class Patient {

    private String image,mobile,name,status;

    public Patient(){

    }

    public Patient(String image, String mobile, String name, String status) {
        this.image = image;
        this.mobile = mobile;
        this.name = name;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
