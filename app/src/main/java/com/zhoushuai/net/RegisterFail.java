package com.zhoushuai.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.zhoushuai.listening.R;

/**
 * Created by zhoushuai on 30/04/2017.
 */

public class RegisterFail implements IFailCallback {
    private Activity activity;
    private ProgressDialog pd;

    public RegisterFail(Activity activity, ProgressDialog pd) {
        this.activity = activity;
        this.pd = pd;
    }

    @Override
    public void onFail(String result) {
        pd.dismiss();
        Toast.makeText(activity, R.string.fail_to_register, Toast.LENGTH_SHORT).show();
    }
}
