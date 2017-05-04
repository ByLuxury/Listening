package com.zhoushuai.net;

/**
 * Created by zhoushuai on 30/04/2017.
 */

public class RegisterNet {
    public RegisterNet(String phone, String nickname, String password,String circle,String school, final ISuccessCallback successCallback, final IFailCallback failCallback) {
        new NetConnection(Config.URL,
                HttpMethod.POST,
                new Success(successCallback, failCallback),
                new Fail(failCallback), Config.KEY_ACTION,
                Config.ACTION_REGISTER, Config.KEY_PHONE_NUM, phone,
                Config.KEY_NICKNAME,nickname,
                Config.KEY_PASSWORD, password,
                Config.KEY_CIRCLE,circle,
                Config.KEY_SCHOOL,school);
    }
}