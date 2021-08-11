package com.nokelock.nokelockbluetooth.config;


import com.nokelock.nokelockbluetooth.bean.BaseSendBean;
import com.nokelock.nokelockbluetooth.bean.CheckVersionBean;
import com.nokelock.nokelockbluetooth.bean.VersionBean;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者: Sunshine
 * 时间: 2017/6/5.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public interface ApiService {

    @Headers({"Content-type:application/json;charset=UTF-8"})//需要添加头
    @POST("Insert")
    Observable<BaseSendBean> getDeviceInfo(@Body BaseSendBean route);

    @Headers({"Content-type:application/json;charset=UTF-8"})//需要添加头
    @POST("JspTest/AppVersionServlet")
    Observable<VersionBean> checkVersion(@Body CheckVersionBean packname);
}
