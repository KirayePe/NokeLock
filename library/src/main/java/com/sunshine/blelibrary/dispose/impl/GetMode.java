package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 获取锁的工作模式
 * Created by sunshine on 2017/3/8.
 */

public class GetMode extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("05200101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.GET_MODE,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.GET_MODE,hexString);
        }
    }

    @Override
    protected String action() {
        return "0520";
    }
}
