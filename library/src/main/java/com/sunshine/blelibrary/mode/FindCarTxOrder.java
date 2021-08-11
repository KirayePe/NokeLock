package com.sunshine.blelibrary.mode;

public class FindCarTxOrder extends TxOrder {

    public FindCarTxOrder() {
        super(TYPE.FIND_CAR);
        add(new byte[]{0x01,0x01});
    }
}
