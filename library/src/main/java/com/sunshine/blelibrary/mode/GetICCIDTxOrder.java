package com.sunshine.blelibrary.mode;

/**
 * Created by mac on 2017/6/3.
 */

public class GetICCIDTxOrder extends TxOrder {

    /**
     *  获取iccid
     */
    public GetICCIDTxOrder() {
        super(TYPE.GET_ICC_ID);
        add(new byte[]{0x01,0x00});
    }
}
