package com.nokelock.nokelockbluetooth;

import android.app.Application;

import com.fitsleep.sunshinelibrary.utils.CrashHandler;
import com.fitsleep.sunshinelibrary.utils.ToastUtils;
import com.nokelock.nokelockbluetooth.utils.ConditionsUtils;
import com.sunshine.blelibrary.config.LockType;
import com.sunshine.blelibrary.impl.AndroidBle;
import com.sunshine.blelibrary.inter.IBLE;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;


/**
 * 作者: Sunshine
 * 时间: 2017/6/6.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class App extends Application {

    private static App app;
    private IBLE bleManager;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        bleManager = new AndroidBle(this);
        ToastUtils.init(getApplicationContext());
        GlobalParameterUtils.getInstance().setLockType(LockType.MTS);
        ConditionsUtils.init(this);

//        CrashHandler.getInstance().init(this);
    }

    public static App getInstance() {
        return app;
    }

    public IBLE getBleManager() {
        if (bleManager == null) {
            bleManager = new AndroidBle(this);
        }
        return bleManager;
    }
}
