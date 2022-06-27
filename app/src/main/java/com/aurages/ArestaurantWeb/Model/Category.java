package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("timedEventFrom")
    @Expose
    private String timedEventFrom;
    @SerializedName("timedEventTo")
    @Expose
    private String timedEventTo;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("cat_id")
    @Expose
    private String catId;
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
    private Integer code;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
}
