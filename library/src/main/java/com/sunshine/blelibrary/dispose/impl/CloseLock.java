package com.sunshine.blelibrary.dispose.impl;

import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.BaseHandler;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

/**
 * 复位指令
 * Created by sunshine on 2017/2/20.
 */

public class CloseLock extends BaseHandler {
    @Override
    protected void handler(String hexString) {
//        byte[] mingwen = ConvertUtils.hexString2Bytes(hexString);
//        Logger.e("close lock","decrypt:"+ ConvertUtils.bytes2HexString(mingwen));
//        Intent intent = new Intent();
//        intent.setAction(Config.CLOSE_ACTION);
        if (hexString.startsWith("050D0101")){
            GlobalParameterUtils.getInstance().sendBroadcast(Config.CLOSE_ACTION,"");
        }else {
//            intent.putExtra("data",ConvertUtils.bytes2HexString(mingwen));
            GlobalParameterUtils.getInstance().sendBroadcast(Config.CLOSE_ACTION,hexString);
        }

//        GlobalParameterUtils.getInstance().getContext().sendBroadcast(intent);
    }

    @Override
    protected String action() {
        return "050D";
    }
}
