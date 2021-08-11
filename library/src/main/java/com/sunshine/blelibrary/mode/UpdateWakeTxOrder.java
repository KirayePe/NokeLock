package com.sunshine.blelibrary.mode;

/**
 * 获取锁状态
 * Created by sunshine on 2017/2/24.
 */

public class UpdateWakeTxOrder extends TxOrder {

    /**
     * 获取锁状态
     */
    public UpdateWakeTxOrder(int num, int minute, int hour, int time) {
        super(TYPE.UPDATE_WAKE);
        add(new byte[]{0x04, (byte) num, (byte) minute, (byte) hour, (byte) time});
    }
}
