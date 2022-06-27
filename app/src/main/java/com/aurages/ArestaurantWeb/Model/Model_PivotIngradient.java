package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_PivotIngradient {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("ingredient_id")
    @Expose
    private String ingredientId;
    @SerializedName("quantity")
    @Expose
    private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

