package com.themebd.mydoctorfinal;

/**
 * Created by arif on 28-Oct-17.
 */

public class PatientHomeData {
    public int imageId;
    public String category;

    PatientHomeData(String category, int imageId){
        this.category = category;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getCategory() {
        return category;
    }
}
