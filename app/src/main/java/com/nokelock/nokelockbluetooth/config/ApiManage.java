package com.nokelock.nokelockbluetooth.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者: Sunshine
 * 时间: 2017/6/6.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class ApiManage {
    private static ApiManage apiManage;
    private String url = "http://service.rocolock.com:16888/";
    private String checkVersion = "http://120.24.3.148:8080/";

    private ApiService apiService;
    private Object ob = new Object();
    private int DEFAULT_TIMEOUT = 20;
    public static ApiManage getInstance() {
        return ApiHolder.getInstance();
    }

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();

    public ApiService getApiService(){
        if (apiService==null){
            synchronized (ob){
                if (apiService==null){
                    apiService = new Retrofit.Builder()
                            .baseUrl(url)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build().create(ApiService.class);
                }
            }
        }
        return apiService;
    }
    public ApiService checkVersionService(){
        if (apiService==null){
            synchronized (ob){
                if (apiService==null){
                    apiService = new Retrofit.Builder()
                            .baseUrl(checkVersion)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build().create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    private static class ApiHolder {
        public static ApiManage getInstance() {
            return new ApiManage();
        }
    }
}
