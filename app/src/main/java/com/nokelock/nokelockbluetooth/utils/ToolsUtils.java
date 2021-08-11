package com.nokelock.nokelockbluetooth.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author Sunshine
 * @Description 未归档工具类
 */
public class ToolsUtils {


    /**
     * 判断系统语言是否是中文
     * @param context 上下文
     * @return {@link 0为中文，1为日文，其他为英文}
     */
    public static int getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        System.out.println("language:" + language);
        if (language.endsWith("zh")){
            return 0;
        }else if (language.endsWith("ja")) {
            return 1;
        }else{
            return 2;
        }
    }

    public static String getLanguages(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        System.out.println("language:" + language);
        if (language.endsWith("zh")){
            return "zh";
        }else if ("ja".endsWith(language)) {
            return "ja";
        }else{
            return "en";
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取系统版本名称
     * @param context 上下文
     * @return 返回版本Name
     */
    public static String getVersion(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo.versionName;
    }
    // 加密
    public  static byte[] Encrypt(byte[] sSrc, byte[] sKey) {

        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc);

            return encrypted;//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception ex) {
            return null;
        }
    }

    // 解密
    public  static byte[] Decrypt(byte[] sSrc, byte[] sKey) {

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] dncrypted = cipher.doFinal(sSrc);
            return dncrypted;

        } catch (Exception ex) {
            return null;
        }
    }

}
