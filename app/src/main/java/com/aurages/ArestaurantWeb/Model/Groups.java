package com.aurages.ArestaurantWeb.Model;

public class Groups {

    private String ID ;
    private String name ;
    private String sku ;
    private String timedEventFrom ;
    private String timedEventTo ;
    private Integer active ;
    private String cat_id ;
    private String addByUserId;
    private String created_at ;
    private String updated_at ;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getAddByUserId() {
        return addByUserId;
    }

    public void setAddByUserId(String addByUserId) {
        this.addByUserId = addByUserId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }



}
