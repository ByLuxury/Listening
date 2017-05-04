package com.zhoushuai.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhoushuai on 29/04/2017.
 */

public class Success implements ISuccessCallback {
    ISuccessCallback successCallback;
    IFailCallback failCallback;

    public Success(ISuccessCallback successCallback, IFailCallback failCallback) {
        this.successCallback = successCallback;
        this.failCallback = failCallback;

    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            switch (jsonObject.getInt(Config.KEY_STATUS)) {
                case Config.RESULT_STATUS_SUCCESS:
                    if (successCallback != null) {
                        //successCallback.onSuccess(jsonObject.getString(Config.KEY_TOKEN));
                        successCallback.onSuccess(result);
                    }
                    break;
                case Config.RESULT_STATUS_FAIL:
                    if (failCallback != null) {
                        failCallback.onFail(result);
                    }
                    break;
//                case Config.RESULT_STATUS_INVALID_TOKEN:
//                    break;
                case Config.RETURN_RESULT_ERROR_PWD_STATUS:
                    if (failCallback != null) {
                        failCallback.onFail(result);
                    }
                    break;
                case Config.RETURN_RESULT_NO_USER_STATUS:
                    if (failCallback != null) {
                        failCallback.onFail(result);
                    }
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
