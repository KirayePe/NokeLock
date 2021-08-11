package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * Created by mac on 2017/9/28.
 */

public class RFTest extends BaseHandler {

    @Override
    protected void handler(String hexString) {

        if (hexString.startsWith("54020101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.RF_TEST,"");
        }else {
            GlobalParameterUtils.getInstance().sendBroadcast(Config.RF_TEST,hexString);
        }
    }

    @Override
    protected String action() {
        return "5402";
    }
}
