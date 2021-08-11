package com.sunshine.blelibrary.mode;

import com.sunshine.blelibrary.config.Config;

public class OpenBatteryTxOrder extends TxOrder {


    public OpenBatteryTxOrder() {
        super(TYPE.OPEN_BATTERY);
        byte[] bytes = {0x06, Config.password[0], Config.password[1], Config.password[2], Config.password[3], Config.password[4], Config.password[5]};
        add(bytes);
    }
}
