package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPivot_Modifiers {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("modifire_id")
    @Expose
    private String modifireId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModifireId() {
        return modifireId;
    }

    public void setModifireId(String modifireId) {
        this.modifireId = modifireId;
    }

}
