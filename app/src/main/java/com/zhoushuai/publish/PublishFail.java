package com.zhoushuai.publish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.zhoushuai.listening.R;
import com.zhoushuai.net.IFailCallback;

/**
 * Created by zhoushuai on 01/05/2017.
 */

public class PublishFail implements IFailCallback {
    private Activity activity;
    private ProgressDialog pd;

    public PublishFail(Activity activity, ProgressDialog pd) {
        this.activity = activity;
        this.pd = pd;
    }

    @Override
    public void onFail(String result) {
        pd.dismiss();
        Toast.makeText(activity, R.string.fail_to_publish, Toast.LENGTH_SHORT).show();
    }
}
