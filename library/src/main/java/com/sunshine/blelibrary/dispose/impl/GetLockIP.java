package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * Created by mac on 2017/6/3.
 */

public class GetLockIP extends BaseHandler {
    @Override
    protected void handler(String hexString) {

        GlobalParameterUtils.getInstance().sendBroadcast(Config.GET_LOCKIP,hexString);

    }

    @Override
    protected String action() {
        return "0532";
    }
}
