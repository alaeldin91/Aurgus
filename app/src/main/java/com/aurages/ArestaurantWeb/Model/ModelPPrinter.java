package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPPrinter {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("enName")
    @Expose
    private String enName;
    @SerializedName("printer")
    @Expose
    private String printer;
    @SerializedName("printerName")
    @Expose
    private String printerName;
    @SerializedName("printerIndex")
    @Expose
    private int printerIndex;
    @SerializedName("copiesNumber")
    @Expose
    private int copiesNumber;
    @SerializedName("note")
    @Expose
    private String note;
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public int getPrinterIndex() {
        return printerIndex;
    }

    public void setPrinterIndex(int printerIndex) {
        this.printerIndex = printerIndex;
    }

    public int getCopiesNumber() {
        return copiesNumber;
    }

    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
