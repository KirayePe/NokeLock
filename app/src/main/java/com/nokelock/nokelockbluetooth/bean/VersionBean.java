package com.nokelock.nokelockbluetooth.bean;

/**
 * Created by mac on 2017/8/22.
 */

public class VersionBean {

    private String packname;
    private String name;
    private int id;
    private String type;
    private String barcode;
    private String version;
    private String url;

    @Override
    public String toString() {
        return "packname:" + packname + "name:" + name + "type:" + type +
                "barcode:" + barcode + "version:" + version + "url:" + url;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
