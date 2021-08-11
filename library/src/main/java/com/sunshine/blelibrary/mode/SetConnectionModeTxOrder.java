package com.sunshine.blelibrary.mode;

public class SetConnectionModeTxOrder extends TxOrder {


    public SetConnectionModeTxOrder(int mode) {
        super(TYPE.SET_CONNECTION_MODE);
        add(new byte[]{0x01, (byte) mode});
    }
}
