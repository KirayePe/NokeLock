package com.sunshine.blelibrary.mode;

public class DataPullTxOrder extends TxOrder {

    public DataPullTxOrder(byte[] bytes) {
        super(TYPE.DATA_PULL);
        add(bytes);
    }
}
