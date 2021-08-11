package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

public class FindCar extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("05170100")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.FIND_CAR,hexString);
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.FIND_CAR,hexString);
        }
    }

    @Override
    protected String action() {
        return "0517";
    }
}
