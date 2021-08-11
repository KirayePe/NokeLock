package com.sunshine.blelibrary.mode;

public class ReplacementBatteryTxOrder extends TxOrder {


    public ReplacementBatteryTxOrder() {
        super(TYPE.REPLACEMENT_BATTERY);
        add(new byte[]{0x01,0x01});
    }
}
