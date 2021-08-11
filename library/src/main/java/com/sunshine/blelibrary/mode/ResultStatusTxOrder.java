package com.sunshine.blelibrary.mode;

/**
 * 作者: Sunshine
 * 时间: 2017/12/28.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class ResultStatusTxOrder extends TxOrder {
    /**
     * @param type 指令类型
     */
    public ResultStatusTxOrder() {
        super(TYPE.RESULT_STATUS);
        add(new byte[]{0x01,0x01});
    }
}
