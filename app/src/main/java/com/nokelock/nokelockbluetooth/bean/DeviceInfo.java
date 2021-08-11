package com.nokelock.nokelockbluetooth.bean;

import java.util.List;

/**
 * 作者: Sunshine
 * 时间: 2017/6/6.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class DeviceInfo {

    /**
     * code : 70200
     * message : 处理成功
     * data : [{"id":8,"origin_id":"638032222676","sn":"1LF1715000001","lock_type":6,"manufactor":"nokelock_manufactor","bt_mac":"C5:47:E1:9E:45:8C","bt_password":"48,48,48,48,48,48","bt_name":null,"bt_aes":"32,87,47,82,54,75,63,71,48,80,65,88,17,99,45,43","bt_version":"v1.1","iccid":"898607b0101700074876","mcu_version":"HL200170605","status":1,"create_time":"2017-06-07 14:47:33","update_time":"2017-06-07 14:47:33"}]
     */

    private int code;
    private String message;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 8
         * origin_id : 638032222676
         * sn : 1LF1715000001
         * lock_type : 6
         * manufactor : nokelock_manufactor
         * bt_mac : C5:47:E1:9E:45:8C
         * bt_password : 48,48,48,48,48,48
         * bt_name : null
         * bt_aes : 32,87,47,82,54,75,63,71,48,80,65,88,17,99,45,43
         * bt_version : v1.1
         * iccid : 898607b0101700074876
         * mcu_version : HL200170605
         * status : 1
         * create_time : 2017-06-07 14:47:33
         * update_time : 2017-06-07 14:47:33
         */

        private int id;
        private String origin_id;
        private String sn;
        private int lock_type;
        private String manufactor;
        private String bt_mac;
        private String bt_password;
        private Object bt_name;
        private String bt_aes;
        private String bt_version;
        private String iccid;
        private String mcu_version;
        private int status;
        private String create_time;
        private String update_time;

        public void setId(int id) {
            this.id = id;
        }

        public void setOrigin_id(String origin_id) {
            this.origin_id = origin_id;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public void setLock_type(int lock_type) {
            this.lock_type = lock_type;
        }

        public void setManufactor(String manufactor) {
            this.manufactor = manufactor;
        }

        public void setBt_mac(String bt_mac) {
            this.bt_mac = bt_mac;
        }

        public void setBt_password(String bt_password) {
            this.bt_password = bt_password;
        }

        public void setBt_name(Object bt_name) {
            this.bt_name = bt_name;
        }

        public void setBt_aes(String bt_aes) {
            this.bt_aes = bt_aes;
        }

        public void setBt_version(String bt_version) {
            this.bt_version = bt_version;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public void setMcu_version(String mcu_version) {
            this.mcu_version = mcu_version;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getId() {
            return id;
        }

        public String getOrigin_id() {
            return origin_id;
        }

        public String getSn() {
            return sn;
        }

        public int getLock_type() {
            return lock_type;
        }

        public String getManufactor() {
            return manufactor;
        }

        public String getBt_mac() {
            return bt_mac;
        }

        public String getBt_password() {
            return bt_password;
        }

        public Object getBt_name() {
            return bt_name;
        }

        public String getBt_aes() {
            return bt_aes;
        }

        public String getBt_version() {
            return bt_version;
        }

        public String getIccid() {
            return iccid;
        }

        public String getMcu_version() {
            return mcu_version;
        }

        public int getStatus() {
            return status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "id=" + id +
                    ", origin_id='" + origin_id + '\'' +
                    ", sn='" + sn + '\'' +
                    ", lock_type=" + lock_type +
                    ", manufactor='" + manufactor + '\'' +
                    ", bt_mac='" + bt_mac + '\'' +
                    ", bt_password='" + bt_password + '\'' +
                    ", bt_name=" + bt_name +
                    ", bt_aes='" + bt_aes + '\'' +
                    ", bt_version='" + bt_version + '\'' +
                    ", iccid='" + iccid + '\'' +
                    ", mcu_version='" + mcu_version + '\'' +
                    ", status=" + status +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
