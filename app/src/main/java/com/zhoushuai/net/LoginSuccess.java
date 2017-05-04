package com.zhoushuai.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.zhoushuai.listening.MainActivity;
import com.zhoushuai.listening.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhoushuai on 30/04/2017.
 */

public class LoginSuccess implements ISuccessCallback {
    private FragmentActivity activity;
    private ProgressDialog pd;
    private String phone;
    private String pwd;

    public LoginSuccess(FragmentActivity activity, ProgressDialog pd, String phone, String pwd) {
        this.activity = activity;
        this.pd = pd;
        this.phone = phone;
        this.pwd = pwd;
    }

    @Override
    public void onSuccess(String result) {
        pd.dismiss();
        JSONObject jsonObject = null;
        try {
            Log.i("ZHOU", result);
            jsonObject = new JSONObject(result);
            String nickname = jsonObject.getString(Config.KEY_NICKNAME);//获取用户名
            Toast.makeText(activity, R.string.success_to_login, Toast.LENGTH_SHORT).show();
            Config.cacheToken(activity, jsonObject.getString(Config.KEY_TOKEN));//缓存token
            Config.cachePhoneNum(activity, phone);//缓存当前电话号码
            Config.cachePassword(activity, pwd);
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra(Config.KEY_TOKEN, Config.KEY_TOKEN);
            intent.putExtra(Config.KEY_PHONE_NUM, phone);
            intent.putExtra(Config.KEY_PASSWORD, pwd);
            intent.putExtra(Config.KEY_NICKNAME, nickname);
            activity.startActivity(intent);
            activity.finish();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
