package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * Created by mac on 2017/6/3.
 */

public class SetConnectionMode extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("07020100")) {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.SET_CONNECTION_MODE, "");
        } else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.SET_CONNECTION_MODE, hexString);
        }
    }

    @Override
    protected String action() {
        return "0702";
    }
}
