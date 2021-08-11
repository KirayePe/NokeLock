package com.sunshine.blelibrary.mode;

/**
 * 获取GSM id
 * Created by sunshine on 2017/3/9.
 */

public class GetGSMIdTxOrder extends TxOrder {

    public GetGSMIdTxOrder() {
        super(TYPE.GET_GSM_ID);
        add(new byte[]{0x01,0x00});
    }
}
