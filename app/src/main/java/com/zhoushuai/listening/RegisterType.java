package com.zhoushuai.listening;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhoushuai on 26/04/2017.
 */

public class RegisterType extends Activity implements View.OnClickListener {
    private TextView mTeacher;
    private TextView mParent;
   private ImageView mImgReturn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_type_layout);
        initView();
    }

    public void initView() {
        mTeacher = (TextView) findViewById(R.id.teacher);
        mParent = (TextView) findViewById(R.id.parent);
        mImgReturn= (ImageView) findViewById(R.id.reg_type_return_img);
        mParent= (TextView) findViewById(R.id.parent);
        mTeacher.setOnClickListener(this);
        mImgReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher:
                Intent intent = new Intent(RegisterType.this, RegisterChooseCircle.class);
                Bundle bundle = getIntent().getExtras();
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.parent:
                break;
            case R.id.reg_type_return_img:
                finish();
                break;
        }
    }
}
