package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 作者: Sunshine
 * 时间: 2018/7/7.
 * 邮箱: 44493547@qq.com
 * 描述:
 */
public class UpdateWake extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("05640101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.UPDATE_WAKE_ACTION,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.UPDATE_WAKE_ACTION,hexString);
        }
    }

    @Override
    protected String action() {
        return "0564";
    }
}
