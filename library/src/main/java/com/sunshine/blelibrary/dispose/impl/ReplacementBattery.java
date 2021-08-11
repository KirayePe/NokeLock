package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * Created by mac on 2017/6/3.
 */

public class ReplacementBattery extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("61010101")) {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.REPLACEMENT_BATTERY, "");
        } else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.REPLACEMENT_BATTERY, hexString);
        }
    }

    @Override
    protected String action() {
        return "6101";
    }
}
