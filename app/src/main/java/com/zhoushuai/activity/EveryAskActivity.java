package com.zhoushuai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.zhoushuai.listening.R;

/**
 * Created by zhoushuai on 22/04/2017.
 */

public class EveryAskActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_every_ask);
    }
}
