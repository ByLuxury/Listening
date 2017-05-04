package com.zhoushuai.listening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.zhoushuai.activity.ActivityFragment;
import com.zhoushuai.bottombar.BottomTab;
import com.zhoushuai.bottombar.BottomTabGroup;
import com.zhoushuai.contact.ContactFragment;
import com.zhoushuai.homeview.HomeFragment;
import com.zhoushuai.net.Config;
import com.zhoushuai.publish.PublishActivity;

/**
 * Created by zhoushuai on 17/04/2017.
 */
public class MainActivity extends FragmentActivity {

    private Fragment mHomeFragment, mActivityFragment, mContactFragment, mMeFragment, mNoCircleFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();


    }

    /**
     * 初始化视图
     */
    public void initView() {


        BottomTabGroup root = (BottomTabGroup) findViewById(R.id.bottom_bar_root);
        BottomTab home = (BottomTab) root.getChildAt(0);//首页
        BottomTab activity = (BottomTab) root.getChildAt(1);//讲座
        BottomTab add = (BottomTab) root.getChildAt(2);//中间添加按钮
        BottomTab contact = (BottomTab) root.getChildAt(3);//联系人
        BottomTab me = (BottomTab) root.getChildAt(4);//我的

        /**
         * 活动
         */
        //activity.getHint().setBackgroundResource(R.drawable.red_hint);// 设置提示红点的背景
        // activity.setHint("3"); // 设置提示红点上的文字
        activity.getHint().setTextSize(8);//设置字体大小
        activity.getHint().setTextColor(0xffffffff);//字体颜色

        /**
         * 交流
         */
        // contact.getHint().setBackgroundResource(R.drawable.red_hint);
        contact.getHint().setTextSize(8);
        contact.getHint().setTextColor(0xffffffff);
        // contact.setHint("20");
        home.setChecked(true);//默认首页被选择
        initFragment();//添加默认Fragment


        /**
         * tab切换事件
         */
        root.setOnCheckedChangeListener(new BottomTabGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BottomTabGroup root, int checkedId) {

                switch (checkedId) {
                    case R.id.tab_home:
                        addHomeFragment();
                        break;
                    case R.id.tab_activity:
                        addActivityFragment();
                        break;
                    case R.id.tab_contact:
                        addContactFragment();
                        break;
                    case R.id.tab_me:
                        addMeFragment();
                        break;
                    case R.id.tab_add:
                        publishMsg();
                        break;
                }
            }
        });


    }

    /**
     * 发布消息者
     *
     * @return
     */
    public String getPublishIntentData() {
        String sender = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        return sender;
    }

    /**
     * 初始化首页
     */
    public void initFragment() {
        Fragment fragment = null;
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        if (getPublishIntentData() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.KEY_PHONE_NUM, getPublishIntentData());
            fragment.setArguments(bundle);
        }
        judeFragment(fragment);

    }


    public void judeFragment(Fragment fragment) {

        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.content_layout, fragment).commit();
            mCurrentFragment = fragment;
        }

    }


    /**
     * 首页Fragment
     */
    public void addHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        if (getPublishIntentData() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.KEY_PHONE_NUM, getPublishIntentData());
            mHomeFragment.setArguments(bundle);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mHomeFragment);

    }

    /**
     * 活动Fragment
     */
    public void addActivityFragment() {
        if (mActivityFragment == null) {
            mActivityFragment = new ActivityFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mActivityFragment);
    }

    /**
     * 发布消息
     */
    public void publishMsg() {
        String phone = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        String token = getIntent().getStringExtra(Config.KEY_TOKEN);
        Intent intent = new Intent(MainActivity.this, PublishActivity.class);
        intent.putExtra(Config.KEY_PHONE_NUM, phone);
        intent.putExtra(Config.KEY_TOKEN, token);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Config.ACTIVITY_RESULT_REFRESH_CODE:
        }
    }

    /**
     * 交流
     */
    public void addContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mContactFragment);

    }

    /**
     * 我的
     */
    public void addMeFragment() {
        if (mMeFragment == null) {
            mMeFragment = new MeFragment();
            String nickname = getIntent().getStringExtra(Config.KEY_NICKNAME);
            Bundle bundle = new Bundle();
                bundle.putString(Config.KEY_NICKNAME, nickname);
                mMeFragment.setArguments(bundle);
        }


        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mMeFragment);
    }

    /**
     * 添加或者隐藏Fragment
     */
    private void addOrShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (mCurrentFragment == fragment) {
            return;
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.content_layout, fragment).commit();

        } else {
            fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;//赋给当前Fragment


    }

}
