package com.nokelock.nokelockbluetooth.config;

import android.content.IntentFilter;

/**
 * 作者: Sunshine
 * 时间: 2017/6/7.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class ActionConfig {


    public static final String AUTH_KEY = "fwCWYMqmRINlRtMp";
    public static final String SECRET = "G7qMjMTRYkG7tuc9zujGvkVImW4WJ5kh";

    public static final String REFRESH_ADDRESS = "com.nokelock.fastble.REFRESH_ADDRESS";
    public static final String OPEN_ACTION ="com.nokelock.fastble.OPEN_ACTION";
    public static final String BYTTERY_ACTION = "com.nokelock.fastble.BYTTERY_ACTION";
    public static final String LOCK_STATUS = "com.nokelock.fastble.LOCK_STATUS";
    public static final String LOCK_GSM = "com.nokelock.fastble.LOCK_GSM";
    public static final String RESET_LOCK = "com.nokelock.fastble.RESET_LOCK";
    public static final String LOCK_GSM_ID = "com.nokelock.fastble.LOCK_GSM_ID";
    public static final String LOCK_GSM_VERSION = "com.nokelock.fastble.LOCK_GSM_VERSION";
    public static final String SET_MODE = "com.nokelock.fastble.SET_MODE";
    public static final String GET_MODE = "com.nokelock.fastble.GET_MODE";
    public static final String SET_NORMAL_MODE = "com.nokelock.fastble.SET_NORMAL_MODE";
    public static final String SET_RESTART_MODE = "com.nokelock.fastble.SET_RESTART_MODE";
    public static final String SET_SHUTDOWN_MODE = "com.nokelock.fastble.SET_SHUTDOWN_MODE";

    public static final String ICCID = "com.nokelock.fastble.ICCID";
    public static final String DEMAIN = "com.nokelock.fastble.DEMAIN";
    public static final String LOCK_IP = "com.nokelock.fastble.LOCK_IP";
    public static final String RF_TEST = "com.nokelock.fastble.RF_TEST";
    public static final String STOP = "com.nokelock.fastble.STOP";

    public static IntentFilter initIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(REFRESH_ADDRESS);
        intentFilter.addAction(OPEN_ACTION);
        intentFilter.addAction(BYTTERY_ACTION);
        intentFilter.addAction(LOCK_STATUS);
        intentFilter.addAction(LOCK_GSM);
        intentFilter.addAction(RESET_LOCK);
        intentFilter.addAction(LOCK_GSM_ID);
        intentFilter.addAction(LOCK_GSM_VERSION);
        intentFilter.addAction(GET_MODE);
        intentFilter.addAction(SET_MODE);
        intentFilter.addAction(SET_NORMAL_MODE);
        intentFilter.addAction(SET_RESTART_MODE);
        intentFilter.addAction(SET_SHUTDOWN_MODE);
        intentFilter.addAction(ICCID);
        intentFilter.addAction(DEMAIN);
        intentFilter.addAction(LOCK_IP);
        intentFilter.addAction(RF_TEST);
        intentFilter.addAction(STOP);
        return intentFilter;
    }
}
