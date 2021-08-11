package com.nokelock.nokelockbluetooth.bean;

/**
 * 作者: Sunshine
 * 时间: 2017/6/9.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class BaseBean {


    /**
     * code : 70200
     * message : 处理成功
     * data : {"online":0}
     */

    private int code;
    private String message;
    private DataEntity data;

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * online : 0
         */

        private int online;
        private int lock_status;
        public void setOnline(int online) {
            this.online = online;
        }

        public int getOnline() {
            return online;
        }

        public int getLock_status() {
            return lock_status;
        }

        public void setLock_status(int lock_status) {
            this.lock_status = lock_status;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "online=" + online +
                    ", lock_status=" + lock_status +
                    '}';
        }
    }
}
