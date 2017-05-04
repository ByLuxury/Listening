package com.zhoushuai.listening;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.net.Config;
import com.zhoushuai.net.IFailCallback;
import com.zhoushuai.net.ISuccessCallback;
import com.zhoushuai.net.LoginFail;
import com.zhoushuai.net.LoginNet;
import com.zhoushuai.net.LoginSuccess;
import com.zhoushuai.net.NetConnection;
import com.zhoushuai.net.Success;
import com.zhoushuai.util.MD5Util;

/**
 * Created by zhoushuai on 25/04/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private TextView loginText;
    private EditText editPhoneNum, editPassword;
    private ProgressDialog pd = null;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_ui, container, false);
        loginText = (TextView) view.findViewById(R.id.text_login_green);
        editPhoneNum = (EditText) view.findViewById(R.id.edit_account);
        editPassword = (EditText) view.findViewById(R.id.edit_password);
        editPhoneNum.setText(Config.getCachePhoneNum(getActivity()));//获取缓存的电话号码
        editPassword.setText(Config.getCachePassword(getActivity()));//获取缓存的密码
       // Config.clean(getActivity());//清楚sharepreference中的值
        loginText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(editPhoneNum.getText())) {
            Toast.makeText(getActivity(), R.string.phone_can_not_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if(!editPhoneNum.getText().toString().matches(Config.CHECK_PHONE_NUM)){
            Toast.makeText(getActivity(), R.string.phone_num_is_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(editPassword.getText())){
            Toast.makeText(getActivity(), R.string.password_can_not_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        pd = ProgressDialog.show(getActivity(), null, getResources().getString(R.string.is_logining));
        new LoginNet(editPhoneNum.getText().toString(),
                MD5Util.getMD5(editPassword.getText().toString()),
                new LoginSuccess(getActivity(), pd, editPhoneNum.getText().toString(), editPassword.getText().toString()),
                new LoginFail(getActivity(), pd)
        );

    }


}
