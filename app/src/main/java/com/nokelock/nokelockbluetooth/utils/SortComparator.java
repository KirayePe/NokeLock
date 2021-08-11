package com.nokelock.nokelockbluetooth.utils;


import com.nokelock.nokelockbluetooth.bean.DeviceBean;

import java.util.Comparator;

/**
 * 排序
 * Created by sunshine on 2017/2/21.
 */

public class SortComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        DeviceBean a = (DeviceBean) o;
        DeviceBean b = (DeviceBean) t1;

        return (b.getRssi()- a.getRssi());
    }
}
