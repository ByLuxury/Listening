package com.zhoushuai.listening;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoushuai.bean.TeacherInfo;
import com.zhoushuai.net.RegisterFail;
import com.zhoushuai.net.RegisterNet;
import com.zhoushuai.net.RegisterSuccess;
import com.zhoushuai.util.MD5Util;

/**
 * Created by zhoushuai on 26/04/2017.
 */

public class RegisterChooseCircle extends Activity implements View.OnClickListener {
    private TextView mSkipText;
    private TextView mCreateDoneText;
    private ImageView mImgReturn;
    private EditText mCircleNameEdit;
    private EditText mSchoolNameEdit;
    private ProgressDialog pd = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_choose_circle_layout);
        init();
    }

    public void init() {
        mSkipText = (TextView) findViewById(R.id.reg_choose_circle_skip_text);
        mCreateDoneText = (TextView) findViewById(R.id.reg_create_done);
        mImgReturn = (ImageView) findViewById(R.id.reg_choose_circle__return_img);
        mCircleNameEdit = (EditText) findViewById(R.id.edit_circle_name);
        mSchoolNameEdit = (EditText) findViewById(R.id.edit_school_name);
        mSkipText.setOnClickListener(this);
        mCreateDoneText.setOnClickListener(this);
        mImgReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // submitData();
        switch (v.getId()) {
            case R.id.reg_choose_circle__return_img:
                finish();
                break;
            case R.id.reg_create_done:
                //break;
            case R.id.reg_choose_circle_skip_text:
                submitData();
                break;
            default:
                break;

        }
    }

    public void submitData() {
        pd = ProgressDialog.show(this, null, "加载中");
        Bundle bundle = getIntent().getExtras();
        TeacherInfo teacherInfo = (TeacherInfo) bundle.getSerializable("TeacherInfo");//获取传过来的对象
        String phone = teacherInfo.getPhone();
        String nickname = teacherInfo.getNickname();
        String password = teacherInfo.getPassword();
        String circleName = mCircleNameEdit.getText().toString();
        String school = mSchoolNameEdit.getText().toString();
        new RegisterNet(phone,
                nickname,
                MD5Util.getMD5(password),
                circleName,
                school,
                new RegisterSuccess(this, pd, phone,password,nickname),
                new RegisterFail(this, pd));

    }
}
