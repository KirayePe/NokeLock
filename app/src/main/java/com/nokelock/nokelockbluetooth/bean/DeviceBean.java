package com.nokelock.nokelockbluetooth.bean;

import android.bluetooth.BluetoothDevice;

/**
 * 作者: Sunshine
 * 时间: 2017/6/5.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class DeviceBean {
    private BluetoothDevice device;
    private int rssi = 0;

    public DeviceBean(BluetoothDevice device, int rssi) {
        this.device = device;
        this.rssi = rssi;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
