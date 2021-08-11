package com.sunshine.blelibrary.mode;

/**
 * 设置工作模式
 * Created by sunshine on 2017/3/8.
 */

public class SetModeTxOrder extends TxOrder {
    /**
     * @param type 指令类型
     */
    public SetModeTxOrder(int type) {
        super(TYPE.SET_MODE);
        if (type==0){
            add(new byte[]{0x01,0x00});//正常
        }else if (type==1){
            add(new byte[]{0x01,0x01});//运输
        }else if (type==2){
            add(new byte[]{0x01,0x02});//重启
        }else if (type==3){
            add(new byte[]{0x01,0x03});//关机
        }
    }
}
