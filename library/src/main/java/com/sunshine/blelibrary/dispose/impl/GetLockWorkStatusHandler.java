package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 获取锁的工作状态
 * Created by sunshine on 2017/3/8.
 */

public class GetLockWorkStatusHandler extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        GlobalParameterUtils.getInstance().sendBroadcast(Config.GEt_LOCK_WORK_STATUS,hexString);
    }

    @Override
    protected String action() {
        return "0522";
    }
}
