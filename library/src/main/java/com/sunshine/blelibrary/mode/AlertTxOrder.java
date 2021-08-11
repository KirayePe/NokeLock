package com.sunshine.blelibrary.mode;

public class AlertTxOrder extends TxOrder {

    public AlertTxOrder(byte bt) {
        super(TYPE.ALERT);
        add(new byte[]{0x01, bt});
    }
}
