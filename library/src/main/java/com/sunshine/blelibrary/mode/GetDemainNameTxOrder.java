package com.sunshine.blelibrary.mode;

/**
 * Created by mac on 2017/6/3.
 */

public class GetDemainNameTxOrder extends TxOrder {

    /**
     *  获取锁域名
     */

    public GetDemainNameTxOrder() {
        super(TYPE.GET_DEMAIN_NAME);
        add(new byte[]{0x01,0x00});
    }
}
