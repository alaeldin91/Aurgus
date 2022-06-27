package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InsertOrderDetails {


    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("OrderNumber")
    @Expose
    int  OrderNumber;
    @SerializedName("Qty")
    @Expose
    int  Qty;
    @SerializedName("price")
    @Expose
    float price;
    @SerializedName("note")
    @Expose
    String note;
    @SerializedName("printed")
    @Expose
    int  printed;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("delivaryDate")
    @Expose
    String delivaryDate;
    @SerializedName("order_id")
    @Expose
    String order_id;
    @SerializedName("product_id")
    @Expose
    String  product_id;
    @SerializedName("employee_id")
    @Expose
    String  add_user_byid;

    public InsertOrderDetails( int order_number,int Qty,float price,String note,int printed, String  date, String delivaryDate,String order_id, String product,String add_user_byid){
        this.OrderNumber=order_number;
        this.Qty = Qty;
        this.date = date;
        this.printed = printed;
        this.delivaryDate = delivaryDate;
        this.order_id = order_id;
        this.product_id = product;
        this.add_user_byid = add_user_byid;
        this.note = note;
        this.price = price;

    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDelivaryDate(String delivaryDate) {
        this.delivaryDate = delivaryDate;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }




    public int  getOrderNumber() {
        return OrderNumber;
    }

    public int getQty() {
        return Qty;
    }

    public float getPrice() {
        return price;
    }

    public String getNote() {
        return note;
    }

    public int getPrinted() {
        return printed;
    }

    public String getDate() {
        return date;
    }

    public String getDelivaryDate() {
        return delivaryDate;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getProduct_id() {
        return product_id;
    }




}
