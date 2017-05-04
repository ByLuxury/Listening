package com.zhoushuai.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhoushuai.listening.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {

    private List<ImageView> views = new ArrayList<>();
    private List<AdInfo> infos = new ArrayList<AdInfo>();
    private CycleViewPager cycleViewPager;
    private View view;
    private RelativeLayout mEverydayAskLayout,mOfflineActivityLayout,mFamousLessonLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_layout, container, false);
        configImageLoader();
        initAdView();
        initView();
        return view;
    }

    public void initView() {

        mEverydayAskLayout = (RelativeLayout) view.findViewById(R.id.activity_everyday_ask_layout);
        mOfflineActivityLayout= (RelativeLayout) view.findViewById(R.id.activity_camp_layout);
        mFamousLessonLayout= (RelativeLayout) view.findViewById(R.id.activity_famous_lesson_layout);

        mEverydayAskLayout.setOnClickListener(new ActivityListener());
        mOfflineActivityLayout.setOnClickListener(new ActivityListener());
        mFamousLessonLayout.setOnClickListener(new ActivityListener());


    }

    /**
     * 每日问答，线下活动、名人讲座事件
     */
    class ActivityListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_everyday_ask_layout:
                   Intent AskIntent = new Intent(getActivity(), EveryAskActivity.class);
                    startActivity(AskIntent);
                    break;
                case R.id.activity_camp_layout:
                    Intent OfflineIntent = new Intent(getActivity(), OfflineActivity.class);
                    startActivity(OfflineIntent);
                    break;
                case R.id.activity_famous_lesson_layout:
                    Intent FamousIntent = new Intent(getActivity(), FamousLesson.class);
                    startActivity(FamousIntent);
                    break;
                default:
                    break;

            }
        }
    }

    @SuppressLint("NewApi")
    private void initAdView() {

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for (int i = 0; i < AdImageUrl.imageUrls.length; i++) {
            AdInfo info = new AdInfo();
            info.setUrl(AdImageUrl.imageUrls[i]);
            info.setContent("图片" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ActivityViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ActivityViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ActivityViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间
        cycleViewPager.setTime(1400);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(AdInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;

                //单击图片事件
            }

        }

    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
