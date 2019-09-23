package com.eat.erp;

public class Order {
    String srno;
    String retailer;
    String distributor;
    String id;
    String location;
    String buttonname;

    public Order(String srno, String id, String retailer, String distributor, String location, String buttonname) {
        this.srno=srno;
        this.retailer=retailer;
        this.distributor=distributor;
        this.id=id;
        this.location=location;
        this.buttonname=buttonname;
    }

    public String getsrno() {
        return srno;
    }

    public String getretailer() {
        return retailer;
    }

    public String getdistributor() {
        return distributor;
    }

    public String getid() {
        return id;
    }

    public String getlocation() {
        return location;
    }

    public String getbuttonname() {
        return buttonname;
    }
}
