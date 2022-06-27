package com.aurages.ArestaurantWeb.Model;



import java.net.InetAddress;

public class ModelPrinter {

    private String name;
    private InetAddress address;

    public ModelPrinter(String name, InetAddress address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof ModelPrinter) {
            ModelPrinter printer = (ModelPrinter) o;
            if (printer.name.equals(this.name))
                return true;
        }
        return false;
    }



}
