package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

public class Alert extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("20010101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.ALERT_ACTION,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.ALERT_ACTION,hexString);
        }
    }

    @Override
    protected String action() {
        return "2001";
    }
}
