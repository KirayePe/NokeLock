package com.sunshine.blelibrary.mode;

/**
 * 获取GSM版本
 * Created by sunshine on 2017/3/9.
 */

public class GetGSMVersionTxOrder extends TxOrder {

    public GetGSMVersionTxOrder() {
        super(TYPE.GET_GSM_VERSION);
        add(new byte[]{0x01,0x00});
    }
}
