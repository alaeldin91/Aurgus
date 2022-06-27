package com.aurages.ArestaurantWeb.Model;

public class ImageModel {

    private long id;
    private String imageName;
    private int imagePath;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}



