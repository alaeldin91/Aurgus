
package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBill {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("BillKindNumber")
    @Expose
    private int billKindNumber;
    @SerializedName("BillKindName")
    @Expose
    private String billKindName;
    @SerializedName("BillKindNameEnglish")
    @Expose
    private String billKindNameEnglish;
    @SerializedName("addByUserId")
    @Expose
    private String addByUserId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
public ModelBill(){

}




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBillKindNumber() {
        return billKindNumber;
    }

    public void setBillKindNumber(int billKindNumber) {
        this.billKindNumber = billKindNumber;
    }

    public String getBillKindName() {
        return billKindName;
    }

    public void setBillKindName(String billKindName) {
        this.billKindName = billKindName;
    }

    public String getBillKindNameEnglish() {
        return billKindNameEnglish;
    }

    public void setBillKindNameEnglish(String billKindNameEnglish) {
        this.billKindNameEnglish = billKindNameEnglish;
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
