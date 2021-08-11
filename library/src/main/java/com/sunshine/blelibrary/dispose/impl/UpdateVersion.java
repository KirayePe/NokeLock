package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 固件升级
 * Created by sunshine on 2017/2/21.
 */

public class UpdateVersion extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("03020101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.UPDATE_VERSION_ACTION,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.UPDATE_VERSION_ACTION,hexString);
        }
    }

    @Override
    protected String action() {
        return "030201";
    }
}
