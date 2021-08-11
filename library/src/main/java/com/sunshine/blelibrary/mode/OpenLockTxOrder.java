package com.sunshine.blelibrary.mode;

import com.fitsleep.sunshinelibrary.utils.ToastUtils;
import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

import java.util.Random;

/**
 * 开锁指令
 * Created by sunshine on 2017/2/24.
 */

public class OpenLockTxOrder extends TxOrder {
    byte[] noBytes;
    public OpenLockTxOrder(byte[] noBytes) {
        super(TYPE.OPEN_LOCK);
        this.noBytes = noBytes;
        byte[] bytes = {0x06, Config.password[0], Config.password[1], Config.password[2], Config.password[3], Config.password[4], Config.password[5]};
        add(bytes);
    }



    /**
     * 将指令类型、数据列表按照规定的协议拼凑成字符串， 然后用来进行传输（在此项目是通过蓝牙传输）
     *
     * @return String
     * @Title: generateString
     */
    public String generateString() {
        StringBuilder builder = new StringBuilder();
        // 命令类型
        int typeValue = getType().getValue();
        int type = ((typeValue >> 8) & 0x00ff);// 命令类型高8位
        int code = ((typeValue) & 0x00ff);// 命令类型低8位
        // 拼凑命令类型
        builder.append(formatByte2HexStr((byte) type));
        builder.append(formatByte2HexStr((byte) code));

        // 拼凑数据
        for (int i = 0; i < size(); i++) {
            byte value = get(i);//获取数据
            builder.append(formatByte2HexStr(value));//拼凑数据
        }
        if (null == GlobalParameterUtils.getInstance().getToken() || GlobalParameterUtils.getInstance().getToken().length < 4) {
            ToastUtils.showMessage("token不正确");
        }else {
            //添加token
            for (int i = 0; i < 4; i++) {
                builder.append(formatByte2HexStr(GlobalParameterUtils.getInstance().getToken()[i]));
            }
        }
        // 如果数据总位数不够，在数据后面补0
        builder.append(formatByte2HexStr(noBytes[0]));
        builder.append(formatByte2HexStr(noBytes[1]));
        builder.append(formatByte2HexStr(noBytes[2]));
        // 生成字符串形式的指令
        String orderStr = builder.toString();
        return orderStr;
    }
}
