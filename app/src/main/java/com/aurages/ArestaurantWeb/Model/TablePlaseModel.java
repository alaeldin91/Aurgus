package com.aurages.ArestaurantWeb.Model;

public class TablePlaseModel {

    private String Guid ;
    private int Number ;
    private String Code ;
    private String Name ;
    private String EnglishName ;
    private String FrenchName ;
    private String Notes ;
    private String HostGuid ;
    private String UserGuid ;
    private int TablePrinter ;
    private int PrintCopies ;
    private String PrinterName ;
    private boolean selected=false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getFrenchName() {
        return FrenchName;
    }

    public void setFrenchName(String frenchName) {
        FrenchName = frenchName;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getHostGuid() {
        return HostGuid;
    }

    public void setHostGuid(String hostGuid) {
        HostGuid = hostGuid;
    }

    public int getTablePrinter() {
        return TablePrinter;
    }

    public void setTablePrinter(int tablePrinter) {
        TablePrinter = tablePrinter;
    }

    public int getPrintCopies() {
        return PrintCopies;
    }

    public void setPrintCopies(int printCopies) {
        PrintCopies = printCopies;
    }

    public String getPrinterName() {
        return PrinterName;
    }

    public void setPrinterName(String printerName) {
        PrinterName = printerName;
    }

    public String getUserGuid() {
        return UserGuid;
    }

    public void setUserGuid(String userGuid) {
        UserGuid = userGuid;
    }
}
