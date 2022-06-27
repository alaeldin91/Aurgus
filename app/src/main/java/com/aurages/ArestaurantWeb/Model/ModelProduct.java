package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProduct {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nameAr")
    @Expose
    private String nameAr;
    @SerializedName("descriptionAr")
    @Expose
    private String descriptionAr;
    @SerializedName("nameEn")
    @Expose
    private String nameEn;
    @SerializedName("descriptionEn")
    @Expose
    private String descriptionEn;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sellType")
    @Expose
    private String sellType;
    @SerializedName("tax")
    @Expose
    private double tax;
    @SerializedName("timedEventFrom")
    @Expose
    private String timedEventFrom;
    @SerializedName("timedEventTo")
    @Expose
    private String timedEventTo;
    @SerializedName("active")
    @Expose
    private int active;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("printer_id")
    @Expose
    private String printerId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("addByUserId")
    @Expose
    private String addByUserId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("code")
    @Expose
    private int code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getTimedEventFrom() {
        return timedEventFrom;
    }

    public void setTimedEventFrom(String timedEventFrom) {
        this.timedEventFrom = timedEventFrom;
    }

    public String getTimedEventTo() {
        return timedEventTo;
    }

    public void setTimedEventTo(String timedEventTo) {
        this.timedEventTo = timedEventTo;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAddByUserId() {
        return addByUserId;
    }

    public void setAddByUserId(String addByUserId) {
        this.addByUserId = addByUserId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}