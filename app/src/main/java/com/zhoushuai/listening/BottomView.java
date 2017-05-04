package com.zhoushuai.listening;

import android.content.Context;
import android.util.AttributeSet;
import com.zhoushuai.bottombar.BottomTab;
/**
 * Created by zhoushuai on 17/04/2017.
 */
public class BottomView extends BottomTab {


    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public BottomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.tab_radio_button;
    }
}
