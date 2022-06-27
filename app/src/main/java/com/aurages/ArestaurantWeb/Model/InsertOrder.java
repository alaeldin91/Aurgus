package com.aurages.ArestaurantWeb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertOrder<orderDetails> {
    @SerializedName("number")
    @Expose
    int number;
    @SerializedName("dailyNumber")
    @Expose
    int dailyNumber;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("billDate")
    @Expose
    String billDate;
    @SerializedName("delivaryDate")
    @Expose
    String delivaryDate;
    @SerializedName("billSatate")
    @Expose
    String billSatate;
    @SerializedName("total")
    @Expose
    float total;
    @SerializedName("discount")
    @Expose
    float discount;
    @SerializedName("discountPerCent")
    @Expose
    float discountPerCent;
    @SerializedName("extra")
    @Expose
    float extra;
    @SerializedName("tax")
    @Expose
    float tax;
    @SerializedName("sevice")
    @Expose
    float sevice;
    @SerializedName("note")
    @Expose
    String note;
    @SerializedName("printredCount")
    @Expose
    int printredCount;
    @SerializedName("remain")
    @Expose
    float remain;
    @SerializedName("branch_id")
    @Expose
    String branch_id;
    @SerializedName("customer_id")
    @Expose
    String customer_id;
    @SerializedName("table_id")
    @Expose
    String table_id;
    @SerializedName("paymentType")
    @Expose
    String paymentType;
    @SerializedName("bill_id")
    @Expose
    String bill_id;
    @SerializedName("employee_id")
    @Expose
    String add_user_id;
    @SerializedName("id")
    @Expose
    int id;





    public InsertOrder( int dailyNumber, String date, String billDate, String delivaryDate, String billSatate, float total, float discount, float discountPerCent, float extra, float tax, float sevice, String note, int printredCount, float remain, String branch_id, String customer_id, String table_id, String paymentType, String bill_id,String add_user_id) {

        this.dailyNumber = dailyNumber;
        this.date = date;
        this.billDate = billDate;
        this.delivaryDate = delivaryDate;
        this.billSatate = billSatate;
        this.total = total;
        this.discount = discount;
        this.discountPerCent = discountPerCent;
        this.extra = extra;
        this.tax = tax;
        this.sevice = sevice;
        this.note = note;
        this.printredCount = printredCount;
        this.remain = remain;
        this.branch_id = branch_id;
        this.customer_id = customer_id;
        this.table_id = table_id;
        this.paymentType = paymentType;
        this.bill_id = bill_id;
        this.add_user_id=add_user_id;

    }


    public int getNumber() {
        return number;
    }

    public int getDailyNumber() {
        return dailyNumber;
    }

    public String getDate() {
        return date;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getDelivaryDate() {
        return delivaryDate;
    }

    public String getBillSatate() {
        return billSatate;
    }

    public float getTotal() {
        return total;
    }

    public float getDiscount() {
        return discount;
    }

    public float getDiscountPerCent() {
        return discountPerCent;
    }

    public float getExtra() {
        return extra;
    }

    public float getTax() {
        return tax;
    }

    public float getSevice() {
        return sevice;
    }

    public String getNote() {
        return note;
    }

    public int getPrintredCount() {
        return printredCount;
    }

    public float getRemain() {
        return remain;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDailyNumber(int dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setDelivaryDate(String delivaryDate) {
        this.delivaryDate = delivaryDate;
    }

    public void setBillSatate(String billSatate) {
        this.billSatate = billSatate;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setDiscountPerCent(float discountPerCent) {
        this.discountPerCent = discountPerCent;
    }

    public void setExtra(float extra) {
        this.extra = extra;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public void setSevice(float sevice) {
        this.sevice = sevice;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrintredCount(int printredCount) {
        this.printredCount = printredCount;
    }

    public void setRemain(float remain) {
        this.remain = remain;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
