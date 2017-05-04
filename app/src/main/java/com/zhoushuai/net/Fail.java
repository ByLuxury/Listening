package com.zhoushuai.net;

/**
 * Created by zhoushuai on 29/04/2017.
 */

public class Fail implements IFailCallback {
    IFailCallback failCallback;

    public Fail(IFailCallback failCallback) {
        this.failCallback = failCallback;

    }
    @Override
    public void onFail(String result) {
        if(failCallback!=null){
            failCallback.onFail(result);
        }
    }
}
