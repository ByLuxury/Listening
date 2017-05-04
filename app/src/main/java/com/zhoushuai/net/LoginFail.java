package com.zhoushuai.net;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.zhoushuai.listening.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhoushuai on 30/04/2017.
 */

public class LoginFail implements IFailCallback {
    private FragmentActivity activity;
    private ProgressDialog pd;

    public LoginFail(FragmentActivity activity, ProgressDialog pd) {
        this.activity = activity;
        this.pd = pd;
    }

    @Override
    public void onFail(String result) {
        pd.dismiss();
        Toast.makeText(activity, R.string.fail_to_login, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = null;
        if (result == null) {
            Toast.makeText(activity, R.string.fail_to_login, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            jsonObject = new JSONObject(result);
            switch (jsonObject.getInt(Config.KEY_STATUS)) {
                case Config.RESULT_STATUS_FAIL:
                    Toast.makeText(activity, R.string.fail_to_login, Toast.LENGTH_SHORT).show();
                    break;
                case Config.RETURN_RESULT_NO_USER_STATUS:
                    Toast.makeText(activity, R.string.no_user, Toast.LENGTH_SHORT).show();
                    break;
                case Config.RETURN_RESULT_ERROR_PWD_STATUS:
                    Toast.makeText(activity, R.string.password_is_error, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(activity, R.string.fail_to_login, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
