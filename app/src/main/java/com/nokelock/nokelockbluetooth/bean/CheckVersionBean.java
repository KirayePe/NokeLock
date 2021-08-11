package com.nokelock.nokelockbluetooth.bean;

/**
 * Created by mac on 2017/8/22.
 */

public class CheckVersionBean {

    private String packname;

    public CheckVersionBean(String packname){
        this.packname = packname;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
}
