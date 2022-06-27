package com.aurages.ArestaurantWeb.Model;

public class TableModel {

    private String id ;
    private String Name ;
    private int Number ;
    private int ChairsNumber ;
    private int MaxChairsNumber ;
    private int status ;
    private String floor_id ;
    private String branch_id ;
    private String addByUserId ;
    private String created_at ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getChairsNumber() {
        return ChairsNumber;
    }

    public void setChairsNumber(int chairsNumber) {
        ChairsNumber = chairsNumber;
    }

    public int getMaxChairsNumber() {
        return MaxChairsNumber;
    }

    public void setMaxChairsNumber(int maxChairsNumber) {
        MaxChairsNumber = maxChairsNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
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

    private String updated_at ;

}
