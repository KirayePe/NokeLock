package com.sunshine.blelibrary.mode;

/**
 * 获取工作模式
 * Created by sunshine on 2017/3/8.
 */

public class GetModeTxOrder extends TxOrder {

    public GetModeTxOrder() {
        super(TYPE.GET_MODE);
        add(new byte[]{0x01,0x00});
    }
}
