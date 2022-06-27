package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("firstName") String firstName = "";
    @SerializedName("binCode") String binCode = "";
    public LoginRequest(String firstName, String binCode){
        this.firstName = firstName;
        this.binCode = binCode;
    }
}
