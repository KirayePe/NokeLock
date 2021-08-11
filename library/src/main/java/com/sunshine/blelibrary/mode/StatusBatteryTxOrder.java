package com.sunshine.blelibrary.mode;

public class StatusBatteryTxOrder extends TxOrder {


    public StatusBatteryTxOrder() {
        super(TYPE.STATUS_BATTERY);
        add(new byte[]{0x01,0x01});
    }
}
