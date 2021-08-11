package com.nokelock.nokelockbluetooth.utils;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21 0021.
 * time 2016-05-13
 */
public class ConditionsUtils {
    private static Application context;
    private static String appType = "ANDROID";

    public static void init(Application context) {
        ConditionsUtils.context = context;
    }

    public static Application getContext() {
        return context;
    }

    public static Map<String,String> getHeader(){
        Map<String,String> map = new HashMap<>();
        String languages = ToolsUtils.getLanguages(context);
        if (languages.endsWith("zh")){
            languages = "zh-CN";
        }else if(languages.endsWith("ja")){
            languages = "ja-JP";
        }else {
            languages = "en-US";
        }
        String appVersion = ToolsUtils.getVersion(context);
        String sysVersion = android.os.Build.VERSION.RELEASE;
        map.put("appLang",languages);
        map.put("appType",appType);
        map.put("appVersion",appVersion);
        map.put("sysVersion",sysVersion);
        return map;
    }

    public static String convert() {
        Date date = new Date(System.currentTimeMillis());
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

}
