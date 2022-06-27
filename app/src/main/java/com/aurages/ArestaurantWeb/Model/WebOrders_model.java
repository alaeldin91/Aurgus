package com.aurages.ArestaurantWeb.Model;

public class WebOrders_model {

    private int id;
    private String Guid;
    private Integer Number;
    private Integer DailyNumber;
    private Integer BillKind;
    private String Date;
    private String BillDate;
    private String HijriBillDate;
    private String DeliveryDate;
    private Integer BillState;
    private String RoomCode;
    private String TableCode;
    private String HostCode;
    private String CustCode;
    private String CustName;
    private Integer PayType;
    private Double Total;
    private Float Discount;
    private String DiscountPercent;
    private Float Extra;
    private Float Tax;
    private Float TaxAdmin;
    private Float Payment;
    private Float Service;
    private String Notes;
    private String UserCode;
    private Integer RestNumber;
    private Integer Printed;
    private Float PersonNo;
    private String Export;
    private String ExportWeb;
    private String BranchCode;
    private String ImageCode;
    private String StateWeb;
    private String IsEnd;
    private Integer SaveOrder;

    public WebOrders_model(int id, int saveOrder) {
        this.id=id;
        this.SaveOrder=saveOrder;
    }


    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public Integer getDailyNumber() {
        return DailyNumber;
    }

    public void setDailyNumber(Integer dailyNumber) {
        DailyNumber = dailyNumber;
    }

    public Integer getBillKind() {
        return BillKind;
    }

    public void setBillKind(Integer billKind) {
        BillKind = billKind;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getHijriBillDate() {
        return HijriBillDate;
    }

    public void setHijriBillDate(String hijriBillDate) {
        HijriBillDate = hijriBillDate;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public Integer getBillState() {
        return BillState;
    }

    public void setBillState(Integer billState) {
        BillState = billState;
    }

    public String getRoomCode() {
        return RoomCode;
    }

    public void setRoomCode(String roomCode) {
        RoomCode = roomCode;
    }

    public String getTableCode() {
        return TableCode;
    }

    public void setTableCode(String tableCode) {
        TableCode = tableCode;
    }

    public String getHostCode() {
        return HostCode;
    }

    public void setHostCode(String hostCode) {
        HostCode = hostCode;
    }

    public String getCustCode() {
        return CustCode;
    }

    public void setCustCode(String custCode) {
        CustCode = custCode;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public Integer getPayType() {
        return PayType;
    }

    public void setPayType(Integer payType) {
        PayType = payType;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Float getDiscount() {
        return Discount;
    }

    public void setDiscount(Float discount) {
        Discount = discount;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public Float getExtra() {
        return Extra;
    }

    public void setExtra(Float extra) {
        Extra = extra;
    }

    public Float getTax() {
        return Tax;
    }

    public void setTax(Float tax) {
        Tax = tax;
    }

    public Float getTaxAdmin() {
        return TaxAdmin;
    }

    public void setTaxAdmin(Float taxAdmin) {
        TaxAdmin = taxAdmin;
    }

    public Float getPayment() {
        return Payment;
    }

    public void setPayment(Float payment) {
        Payment = payment;
    }

    public Float getService() {
        return Service;
    }

    public void setService(Float service) {
        Service = service;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public Integer getRestNumber() {
        return RestNumber;
    }

    public void setRestNumber(Integer restNumber) {
        RestNumber = restNumber;
    }

    public Integer getPrinted() {
        return Printed;
    }

    public void setPrinted(Integer printed) {
        Printed = printed;
    }

    public Float getPersonNo() {
        return PersonNo;
    }

    public void setPersonNo(Float personNo) {
        PersonNo = personNo;
    }

    public String getExport() {
        return Export;
    }

    public void setExport(String export) {
        Export = export;
    }

    public String getExportWeb() {
        return ExportWeb;
    }

    public void setExportWeb(String exportWeb) {
        ExportWeb = exportWeb;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getImageCode() {
        return ImageCode;
    }

    public void setImageCode(String imageCode) {
        ImageCode = imageCode;
    }

    public String getStateWeb() {
        return StateWeb;
    }

    public void setStateWeb(String stateWeb) {
        StateWeb = stateWeb;
    }

    public String getIsEnd() {
        return IsEnd;
    }

    public void setIsEnd(String isEnd) {
        IsEnd = isEnd;
    }

    public Integer getSaveOrder() {
        return SaveOrder;
    }

    public void setSaveOrder(Integer saveOrder) {
        SaveOrder = saveOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }
}
