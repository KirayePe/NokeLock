package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 设置工作模式
 * Created by sunshine on 2017/3/8.
 */

public class SetMode extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("05210101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.SET_MODE,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.SET_MODE,hexString);
        }
    }

    @Override
    protected String action() {
        return "0521";
    }
}
