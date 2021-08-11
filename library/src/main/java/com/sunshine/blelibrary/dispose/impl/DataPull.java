package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

public class DataPull extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        GlobalParameterUtils.getInstance().sendBroadcast(Config.DATA_PULL,hexString);
    }

    @Override
    protected String action() {
        return "54";
    }
}
