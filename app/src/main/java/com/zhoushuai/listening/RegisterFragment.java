package com.zhoushuai.listening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.bean.TeacherInfo;
import com.zhoushuai.net.Config;

/**
 * Created by zhoushuai on 25/04/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText mPhoneEdit;
    private EditText mInputCodeEdit;//验证码
    private TextView mDoneText;
    private View view;
    private Button mGetCodebtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_ui, container, false);
        initView();
        return view;
    }

    public void initView() {
        mDoneText = (TextView) view.findViewById(R.id.text_reg_done);
        mPhoneEdit = (EditText) view.findViewById(R.id.reg_edit_phone);
        mInputCodeEdit = (EditText) view.findViewById(R.id.reg_edit_intputcode);
        mGetCodebtn = (Button) view.findViewById(R.id.get_code_btn);
        mDoneText.setOnClickListener(this);
        mGetCodebtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_reg_done:
                String phone = mPhoneEdit.getText().toString();
                if (TextUtils.isEmpty(mPhoneEdit.getText())) {
                    Toast.makeText(getActivity(), R.string.text_tip_phone, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mPhoneEdit.getText().toString().matches(Config.CHECK_PHONE_NUM)) {
                    Toast.makeText(getActivity(), R.string.phone_num_is_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mInputCodeEdit.getText())) {
                    Toast.makeText(getActivity(), R.string.text_check_code, Toast.LENGTH_SHORT).show();
                    return;
                }
                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setPhone(phone);
                Intent intent = new Intent(getActivity(), RegisterSetNiackPwd.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("TeacherInfo", teacherInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.get_code_btn:
                Toast.makeText(getActivity(), "获取验证码成功", Toast.LENGTH_SHORT).show();
                break;
        }


    }

}
