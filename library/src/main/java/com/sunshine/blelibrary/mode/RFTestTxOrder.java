package com.sunshine.blelibrary.mode;

/**
 * Created by mac on 2017/9/28.
 */

public class RFTestTxOrder extends TxOrder {

    /**
     * @param number 参数
     */
    public RFTestTxOrder(int number) {
        super(TYPE.RF_TEST);

        add(new byte[]{0x01, (byte) number});
    }
}
