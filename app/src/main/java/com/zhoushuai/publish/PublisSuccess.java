package com.zhoushuai.publish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.zhoushuai.listening.MainActivity;
import com.zhoushuai.listening.R;
import com.zhoushuai.net.Config;
import com.zhoushuai.net.ISuccessCallback;

/**
 * Created by zhoushuai on 28/04/2017.
 */

public class PublisSuccess implements ISuccessCallback {

    private Activity activity;
    private ProgressDialog pd;
    private String phone;
    private String token;

    public PublisSuccess(Activity activity, ProgressDialog pd, String phone, String token) {
        this.activity = activity;
        this.pd = pd;
        this.phone = phone;
        this.token=token;
    }

    @Override
    public void onSuccess(String token1) {
        pd.dismiss();
        Toast.makeText(activity, R.string.success_to_publish, Toast.LENGTH_SHORT).show();
        Config.cacheToken(activity, token1);//缓存token
        Config.cachePhoneNum(activity,phone);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Config.KEY_PHONE_NUM,phone);
        intent.putExtra(Config.KEY_TOKEN,token);
        activity.setResult(Config.ACTIVITY_RESULT_REFRESH_CODE);
        activity.startActivity(intent);

    }
}
