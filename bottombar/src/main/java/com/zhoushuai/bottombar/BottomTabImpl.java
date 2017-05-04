package com.zhoushuai.bottombar;

/**
 * Created by zhoushuai on 19/04/2017.
 */

import android.widget.Checkable;

/**
 * 定义回调接口
 */

public interface BottomTabImpl extends Checkable {


    interface OnCheckedChangeListener {

        void onCheckedChanged(BottomTabImpl buttonView, boolean isChecked);
    }

    int getId();
    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);
}
