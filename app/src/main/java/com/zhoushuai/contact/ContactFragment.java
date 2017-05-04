package com.zhoushuai.contact;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhoushuai.listening.R;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private ViewPager mPageVp;
    /**
     * Tab显示内容TextView
     */
    private TextView mTabChatTv, mTabContactsTv, mTabFriendTv;
    /**
     * Tab的那个引导线
     */
    private ImageView mTabLineIv;
    /**
     * Fragment
     */
    private SeekPersonFragment mChatFg;
    private PrivateLetterFragment mFriendFg;
    private CommuntyFragment mContactsFg;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
 private  View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.contact_layout,container,false);

        findById();
        init();
        initTabLineWidth();
        return view;
    }


    private void findById() {
        mTabContactsTv = (TextView) view.findViewById(R.id.id_contacts_tv);
        mTabChatTv = (TextView) view.findViewById(R.id.id_chat_tv);
        mTabFriendTv = (TextView) view.findViewById(R.id.id_friend_tv);

        mTabLineIv = (ImageView) view.findViewById(R.id.id_tab_line_iv);

        mPageVp = (ViewPager) view.findViewById(R.id.id_page_vp);
    }


	private void init() {
        mFriendFg = new PrivateLetterFragment();
        mContactsFg = new CommuntyFragment();
        mChatFg = new SeekPersonFragment();
        mFragmentList.add(mChatFg);
        mFragmentList.add(mFriendFg);
        mFragmentList.add(mContactsFg);

        mFragmentAdapter = new FragmentAdapter(
               getFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面以及滑动的页面位置
             * offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                    int offsetPixels) {
               LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
               lp.leftMargin = screenWidth/3*position+offsetPixels/3;
               mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                case 0:
                    mTabChatTv.setTextColor(Color.BLUE);
                    break;
                case 1:
                    mTabFriendTv.setTextColor(Color.BLUE);
                    break;
                case 2:
                    mTabContactsTv.setTextColor(Color.BLUE);
                    break;
                }
                currentIndex = position;
            }
        });

    }

    /**
     * 设置滑动条的宽度为屏幕的1/3
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
        lp.width = screenWidth / 3;

        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabChatTv.setTextColor(0xff8c8c8c);
        mTabFriendTv.setTextColor(0xff8c8c8c);
        mTabContactsTv.setTextColor(0xff8c8c8c);
    }

}