package com.aurages.ArestaurantWeb.Model;

public class Temp_Order {
    private  int id;
    private String guid;
    private String name;
    private String english_name;
    private int qty;
    private float price;
    private String notes;
    private int type;
    private  int append;
    private int IsChecked;
    private int IsDelivered;
    private String MatCode;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAppend() {
        return append;
    }

    public void setAppend(int append) {
        this.append = append;
    }

    public int getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(int isChecked) {
        IsChecked = isChecked;
    }

    public int getIsDelivered() {
        return IsDelivered;
    }

    public void setIsDelivered(int isDelivered) {
        IsDelivered = isDelivered;
    }

    public String getMatCode() {
        return MatCode;
    }

    public void setMatCode(String matCode) {
        MatCode = matCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
