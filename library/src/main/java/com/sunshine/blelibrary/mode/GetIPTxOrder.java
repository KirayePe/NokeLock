package com.sunshine.blelibrary.mode;

/**
 * Created by mac on 2017/6/3.
 */

public class GetIPTxOrder extends TxOrder {

    /**
     *  获取锁IP
     */

    public GetIPTxOrder() {
        super(TYPE.GET_LOCK_IP);
        add(new byte[]{0x01,0x00});
    }
}
