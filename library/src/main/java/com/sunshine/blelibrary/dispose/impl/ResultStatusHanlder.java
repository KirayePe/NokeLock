package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 作者: Sunshine
 * 时间: 2017/12/28.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class ResultStatusHanlder extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("05150101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.RESULT_STATUS,"");
        }else {
//            intent.putExtra("data",ConvertUtils.bytes2HexString(mingwen));
            GlobalParameterUtils.getInstance().sendBroadcast(Config.RESULT_STATUS,hexString);
        }
    }

    @Override
    protected String action() {
        return "0515";
    }
}
