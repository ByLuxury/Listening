package com.zhoushuai.publish;

import com.zhoushuai.net.Config;
import com.zhoushuai.net.Fail;
import com.zhoushuai.net.HttpMethod;
import com.zhoushuai.net.IFailCallback;
import com.zhoushuai.net.ISuccessCallback;
import com.zhoushuai.net.NetConnection;
import com.zhoushuai.net.Success;

/**
 * Created by zhoushuai on 01/05/2017.
 */

public class PublishNet {
    public PublishNet(String phone, String token, String msg, ISuccessCallback successCallback, IFailCallback failCallback) {
        new NetConnection(Config.URL,
                HttpMethod.POST,
                new Success(successCallback, failCallback),
                new Fail(failCallback),
                Config.KEY_ACTION, Config.KEY_PUBLISH,
                Config.KEY_PHONE_NUM, phone,
                Config.KEY_TOKEN, token,
                Config.KEY_MSG, msg
        );
    }

   /* public interface ISuccessCallback {
        void success();
    }

    public interface IFailCallback {
        void onFail();
    }*/
}
