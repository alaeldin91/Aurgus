package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_table_spinner {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("chairsNumber")
    @Expose
    private int chairsNumber;
    @SerializedName("maxChairsNumber")
    @Expose
    private int maxChairsNumber;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("floor_id")
    @Expose
    private String floorId;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("addByUserId")
    @Expose
    private String addByUserId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Model_table_spinner(String name) {
        this.name=name;
    }


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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getChairsNumber() {
        return chairsNumber;
    }

    public void setChairsNumber(int chairsNumber) {
        this.chairsNumber = chairsNumber;
    }

    public int getMaxChairsNumber() {
        return maxChairsNumber;
    }

    public void setMaxChairsNumber(int maxChairsNumber) {
        this.maxChairsNumber = maxChairsNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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
}
