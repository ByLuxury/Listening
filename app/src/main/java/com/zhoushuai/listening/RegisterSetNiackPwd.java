package com.zhoushuai.listening;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhoushuai.bean.TeacherInfo;

/**
 * Created by zhoushuai on 26/04/2017.
 */

public class RegisterSetNiackPwd extends Activity implements View.OnClickListener {
    private EditText mNickNameEdit, mPwdEdit;
    private Button mNextStepBtn;
    private ImageView mImgReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_set_nickname);
        initData();
    }

    public void initData() {
        mNextStepBtn = (Button) findViewById(R.id.reg_next_step_text);
        mNickNameEdit = (EditText) findViewById(R.id.reg_edit_input_nickname);
        mPwdEdit = (EditText) findViewById(R.id.reg_edit_input_pwd);
        mImgReturn = (ImageView) findViewById(R.id.reg_setnickname_return_img);
        mNextStepBtn.setOnClickListener(this);
        mImgReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_next_step_text:
                sendInfo();
                break;
            case R.id.reg_setnickname_return_img:
                Intent intent=new Intent(RegisterSetNiackPwd.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 像另一个Activity传递信息
     */
    public void sendInfo() {
        String nickName = mNickNameEdit.getText().toString();
        String password = mPwdEdit.getText().toString();
        if(TextUtils.isEmpty(mNickNameEdit.getText())){
            Toast.makeText(this,R.string.reg_input_nickname,Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPwdEdit.getText())){
            Toast.makeText(this,R.string.reg_input_pwd,Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = getIntent().getExtras();//获取bundle对象
        TeacherInfo teacherInfo = (TeacherInfo) bundle.getSerializable("TeacherInfo");//取出当前教师信息类
        //传递昵称和密码
        teacherInfo.setNickname(nickName);
        teacherInfo.setPassword(password);
        bundle.putSerializable("TeacherInfo", teacherInfo);//重新传递对象
        Intent intent = new Intent(RegisterSetNiackPwd.this, RegisterType.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
