package com.sunshine.blelibrary.dispose.impl;

import com.fitsleep.sunshinelibrary.utils.ToastUtils;
import com.sunshine.blelibrary.dispose.BaseHandler;

/**
 * 作者: Sunshine
 * 时间: 2017/5/13.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class UpdateName extends BaseHandler {
    @Override
    protected void handler(String hexString) {
        if (hexString.startsWith("04020100")){
            ToastUtils.showMessage("名字修改成功");
        }else {
            ToastUtils.showMessage("名字修改失败");
        }
    }

    @Override
    protected String action() {
        return "0402";
    }
}
