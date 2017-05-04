package com.zhoushuai.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by zhoushuai on 29/04/2017.
 */

public class Config {

    // public static final String URL = "http://172.17.252.18:8080/loginServlet";
    //public static final String RefreshURL = "http://172.17.252.18:8080/RefreshServlet";
    public static final String URL = "http://192.168.43.214:8080/loginServlet";
    public static final String RefreshURL = "http://192.168.43.214:8080/RefreshServlet";
    public static final String APP_ID = "com.zhoushuai.listening";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PUBLISH = "publish";
    public static final String KEY_MSG = "msg";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_CIRCLE = "circle";
    public static final String KEY_SCHOOL = "school";
    public static final String CHARSET = "utf-8";
    public static final String KEY_PASSWORD = "password";
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;
    public static final int RETURN_RESULT_NO_USER_STATUS = 2;//用户不存在
    public static final int RETURN_RESULT_ERROR_PWD_STATUS = 3;//密码错误
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_REGISTER = "register";
    public static final String CHECK_PHONE_NUM = "^[1][3,4,5,7,8][0-9]{9}$";//验证手机号码
    public static final int ACTIVITY_RESULT_REFRESH_CODE = 1000;

    public static String getCacheToken(Context context) {

        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);

    }

    /**
     * @param context
     * @param token
     */
    public static void cacheToken(Context context, String token) {
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();

    }


    public static String getCachePhoneNum(Context context) {

        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);

    }

    /**
     * 缓存电话号码
     *
     * @param context
     * @param phoneNum
     */

    public static void cachePhoneNum(Context context, String phoneNum) {
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_PHONE_NUM, phoneNum);
        e.commit();

    }

    public static String getCachePassword(Context context) {

        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(Config.KEY_PASSWORD, null);

    }

    /**
     * 缓存密码
     *
     * @param context
     * @param password
     */
    public static void cachePassword(Context context, String password) {
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_PASSWORD, password);
        e.commit();

    }

    public static  void clean(Context context) {

        SharedPreferences sp =context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
