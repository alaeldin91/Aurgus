package com.aurages.ArestaurantWeb.Model;

public class WebOrderDetails_model {




    private int id;
    private String guid;
    private Integer Number;
    private Integer OrderNumber;
    private Integer OrderID;
    private String MatCode;
    private String MatName;
    private Float Qty;
    private Float Price;
    private String Notes;
    private Integer OrderType;
    private Integer BillType;
    private String HostCode;
    private Integer Printed;
    private Integer IsChecked;
    private Integer Export;
    private Integer ParentOrder;
    private Integer SaveOrderDetails;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public Integer getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        OrderNumber = orderNumber;
    }

    public Integer getOrderID() {
        return OrderID;
    }

    public void setOrderID(Integer orderID) {
        OrderID = orderID;
    }

    public Float getQty() {
        return Qty;
    }

    public void setQty(Float qty) {
        Qty = qty;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public Integer getOrderType() {
        return OrderType;
    }

    public void setOrderType(Integer orderType) {
        OrderType = orderType;
    }

    public Integer getBillType() {
        return BillType;
    }

    public void setBillType(Integer billType) {
        BillType = billType;
    }

    public String getHostCode() {
        return HostCode;
    }

    public void setHostCode(String hostCode) {
        HostCode = hostCode;
    }

    public Integer getPrinted() {
        return Printed;
    }

    public void setPrinted(Integer printed) {
        Printed = printed;
    }

    public Integer getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(Integer isChecked) {
        IsChecked = isChecked;
    }

    public Integer getExport() {
        return Export;
    }

    public void setExport(Integer export) {
        Export = export;
    }

    public Integer getParentOrder() {
        return ParentOrder;
    }

    public void setParentOrder(Integer parentOrder) {
        ParentOrder = parentOrder;
    }

    public Integer getSaveOrderDetails() {
        return SaveOrderDetails;
    }

    public void setSaveOrderDetails(Integer saveOrderDetails) {
        SaveOrderDetails = saveOrderDetails;
    }

    public String getMatCode() {
        return MatCode;
    }

    public void setMatCode(String matCode) {
        MatCode = matCode;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
