package com.zhoushuai.listening;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhoushuai on 23/04/2017.
 */

public class LoginActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mTextLogin, mTextRegister;
    private Fragment mLoginFragment, mRegisterFragment;
    private Fragment mCurrentFragment;
    private RelativeLayout mLoginLayout,mRegisterLayout;
    private ImageView mLoginImg,mRegisterImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_login);
        mTextLogin = (TextView) findViewById(R.id.login_title);
        mTextRegister = (TextView) findViewById(R.id.reg_title);
        mLoginLayout= (RelativeLayout) findViewById(R.id.login_layout_title);
        mRegisterLayout= (RelativeLayout) findViewById(R.id.register_layout_title);
        mLoginImg= (ImageView) findViewById(R.id.login_title_img);
        mRegisterImg= (ImageView) findViewById(R.id.reg_title_img);
        mLoginLayout.setOnClickListener(this);
        mRegisterLayout.setOnClickListener(this);

            initFragment();



    }


    /**
     * 初始化Fragment 默认选择注册
     */
    public void initFragment() {
        if (mRegisterFragment == null) {
            mRegisterFragment = new RegisterFragment();
        }
        if (!mRegisterFragment.isAdded()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.login_and_reg_content_layout, mRegisterFragment).commit();
            mCurrentFragment = mRegisterFragment;
            mRegisterImg.setImageResource(R.drawable.triangle_deep);
            mTextRegister.setTextColor(0xff131313);
            mLoginImg.setImageResource(R.drawable.triangle_gray);
            mTextLogin.setTextColor(0xff7d7c7c);

        }
    }


    private void addOrShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (mCurrentFragment == fragment) {
            return;
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.login_and_reg_content_layout, fragment).commit();

        } else {
            fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;//赋给当前Fragment
        mCurrentFragment = mRegisterFragment;


    }

    /**
     * 登录
     */
    public void loginFragment() {
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mLoginFragment);
        mCurrentFragment = mLoginFragment;
        mRegisterImg.setImageResource(R.drawable.triangle_gray);
        mTextRegister.setTextColor(0xff7d7c7c);
        mLoginImg.setImageResource(R.drawable.triangle_deep);
        mTextLogin.setTextColor(0xff131313);

    }

    /**
     * 注册
     */
    public void registerFragment() {
        if (mRegisterFragment == null) {
            mRegisterFragment = new RegisterFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mRegisterFragment);
         mCurrentFragment=mRegisterFragment;
        mRegisterImg.setImageResource(R.drawable.triangle_deep);
        mTextRegister.setTextColor(0xff131313);
        mLoginImg.setImageResource(R.drawable.triangle_gray);
        mTextLogin.setTextColor(0xff7d7c7c);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_layout_title: {
                //Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();
                loginFragment();
                break;}
             case R.id.register_layout_title: {
                    registerFragment();
                    break;
                }
            }
        }



//    public void userLogin(View view){
//     String name=editUsername.getText().toString();
//        String pwd=editPassword.getText().toString();
//        String url="http://10.1.183.195:8080/PostServer/Server";
////        Intent intent=new Intent(this,MainActivity.class);
////        startActivity(intent);
//        new HttpClientThread(url,name,pwd).start();
//
//    }
}
