package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

public class OpenBatteryBox extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("10020101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.OPEN_BATTERY_ACTION,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.OPEN_BATTERY_ACTION,hexString);
        }
    }

    @Override
    protected String action() {
        return "1002";
    }
}
