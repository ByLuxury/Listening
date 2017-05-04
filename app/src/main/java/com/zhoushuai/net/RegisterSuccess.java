package com.zhoushuai.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.zhoushuai.listening.MainActivity;
import com.zhoushuai.listening.R;

/**
 * Created by zhoushuai on 30/04/2017.
 */

public class RegisterSuccess implements ISuccessCallback {
    private Activity activity;
    private ProgressDialog pd;
    private String phone;
    private String pwd;
    private String nickname;

    public RegisterSuccess(Activity activity, ProgressDialog pd, String phone, String pwd, String nickname) {
        this.activity = activity;
        this.pd = pd;
        this.phone = phone;
        this.pwd = pwd;
        this.nickname = nickname;

    }


    @Override
    public void onSuccess(String token) {
        pd.dismiss();
        Toast.makeText(activity, R.string.success_to_register, Toast.LENGTH_SHORT).show();
        Config.cacheToken(activity, token);//缓存token
        Config.cachePhoneNum(activity, phone);
        Config.cachePassword(activity, pwd);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Config.KEY_TOKEN, token);
        intent.putExtra(Config.KEY_PHONE_NUM, phone);
        intent.putExtra(Config.KEY_NICKNAME, nickname);
        activity.startActivity(intent);
        activity.finish();

    }
}
