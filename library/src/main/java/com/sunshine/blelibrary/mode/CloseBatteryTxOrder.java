package com.sunshine.blelibrary.mode;

public class CloseBatteryTxOrder extends TxOrder {


    public CloseBatteryTxOrder() {
        super(TYPE.CLOSE_BATTERY);
        add(new byte[]{0x01,0x01});
    }
}
