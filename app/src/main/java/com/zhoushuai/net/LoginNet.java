package com.zhoushuai.net;

/**
 * Created by zhoushuai on 29/04/2017.
 */

/**
 * 登录通信类
 */
public class LoginNet {
    public LoginNet(String phone, String password, final ISuccessCallback successCallback, final IFailCallback failCallback) {
        new NetConnection(Config.URL,
                HttpMethod.POST,
                new Success(successCallback, failCallback),
                new Fail(failCallback), Config.KEY_ACTION,
                Config.ACTION_LOGIN, Config.KEY_PHONE_NUM, phone,
                Config.KEY_PASSWORD, password);

    }


}
