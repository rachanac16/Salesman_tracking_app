package com.eat.erp;

public class BeatPlan {
    String srno;
    String retailer;
    String buttonname;
    String id;
    String channel_type;
    String follow_type;
    String temp;
    String checkin;
    String latitude;
    String longitude;

    public BeatPlan(String srno, String retailer, String buttonname, String id, String channel_type, String follow_type, String temp, String checkin, String latitude, String longitude) {
        this.srno=srno;
        this.retailer=retailer;
        this.buttonname=buttonname;
        this.id=id;
        this.channel_type=channel_type;
        this.follow_type=follow_type;
        this.temp=temp;
        this.checkin=checkin;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getsrno() {
        return srno;
    }

    public String getretailer() {
        return retailer;
    }

    public String getbuttonname() {
        return buttonname;
    }

    public String getid() {
        return id;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public String getFollow_type() {
        return follow_type;
    }

    public String gettemp() {
        return temp;
    }

    public String getcheckin() {
        return checkin;
    }

    public String getlatitude() {
        return latitude;
    }

    public String getlongitude() {
        return longitude;
    }
}
