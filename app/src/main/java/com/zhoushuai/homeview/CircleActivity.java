package com.zhoushuai.homeview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhoushuai.listening.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoushuai on 21/04/2017.
 */

public class CircleActivity extends Activity implements View.OnClickListener, RefreshListView.IXListViewListener {
    private ImageView mCircleCheck;
    private RefreshListView listView;
    // ListView的适配器
    private CircleCommentAdapter mCircleCommentAdapter;
    private Button btnSendComment;
    private View view;
    private EditText etComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.circle_layout);
        initView();

    }


    void initView() {
        btnSendComment = (Button) findViewById(R.id.circle_btnSendComment);
        etComment = (EditText) findViewById(R.id.circle_etComment);
        listView = (RefreshListView) findViewById(R.id.circle_listView);
        mCircleCheck = (ImageView) findViewById(R.id.circle_check);
        mCircleCommentAdapter = new CircleCommentAdapter(this, R.layout.comment_listview_item, this);


        listView.setAdapter(mCircleCommentAdapter);
        listView.setXListViewListener(this);// 添加ListView的上拉和下拉刷新监听器
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        initListData();
        listView.setOnItemClickListener(new ListViewListener());
        btnSendComment.setOnClickListener(new BtnSendCommentListener());
        mCircleCheck.setOnClickListener(this);


    }

    /**
     * 数据填充
     */
    void initListData() {

        CommentBean commentBean = new CommentBean();//实例化评论javabean
        commentBean.setImgHead(R.drawable.yu);
        commentBean.setName("渝哥");
        commentBean.setContent("如果你还无法简洁的表达你的想法，那么只能说明你还不够了解它");
        commentBean.setType(FinalStatus.MSG_TEXT);
        commentBean.setAgree(false);
        commentBean.setDate(new SimpleDateFormat().format(new Date()).toString());
        mCircleCommentAdapter.addModel(commentBean);

        CommentBean commentBean2 = new CommentBean();
        commentBean2.setImgHead(R.drawable.yu);
        commentBean2.setName("渝哥");
        commentBean2.setContent("如何让孩子在娱乐中学习");
        commentBean2.setType(FinalStatus.MSG_IMAGE);
        commentBean2.setDate(new SimpleDateFormat().format(new Date()).toString());
        List<String> imageUrls = new ArrayList<>();//图片集合
        imageUrls.add("http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg");
        commentBean2.setImageUrls(imageUrls);
        mCircleCommentAdapter.addModel(commentBean2);

        CommentBean commentBean3 = new CommentBean();//实例化评论javabean
        commentBean3.setImgHead(R.drawable.yu);
        commentBean3.setName("渝哥");
        commentBean3.setContent("如果你还无法简洁的表达你的想法，那么只能说明你还不够了解它");
        commentBean3.setType(FinalStatus.MSG_TEXT);
        commentBean3.setAgree(false);
        commentBean3.setDate(new SimpleDateFormat().format(new Date()).toString());
        mCircleCommentAdapter.addModel(commentBean);

        mCircleCommentAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_check:
                Intent intent = new Intent(CircleActivity.this, CircleMember.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * ListView单击事件
     */
    class ListViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //position = position - 1;

            // if (null != commentAdapter.getModel(position)) {
            Toast.makeText(CircleActivity.this, "click Item...", Toast.LENGTH_SHORT).show();

            // }
        }
    }


    class BtnSendCommentListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String comment = etComment.getEditableText().toString();
            Toast.makeText(CircleActivity.this, comment, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRefresh() {
        refreshListViewInBackground();
    }


    @UiThread
    void refreshListViewInBackground() {// 模拟刷新数据
        CommentBean commentBean = new CommentBean();
        commentBean.setImgHead(R.drawable.head);
        commentBean.setName("老男孩");
        commentBean.setContent("为了梦想而努力。。。");
        commentBean.setType(FinalStatus.MSG_TEXT);
        commentBean.setAgree(false);
        commentBean.setDate(new SimpleDateFormat().format(new Date()).toString());
        mCircleCommentAdapter.addModel(commentBean, true);
        mCircleCommentAdapter.notifyDataSetChanged();
        onLoad();
    }

    @Override
    public void onLoadMore() {

        loadMoreInBackground();
    }

    @UiThread
    void loadMoreInBackground() {
        CommentBean commentBean = new CommentBean();
        commentBean.setImgHead(R.drawable.head);
        commentBean.setName("相信自己");
        commentBean.setContent("且行且珍惜");
        commentBean.setType(FinalStatus.MSG_TEXT);
        commentBean.setAgree(false);
        commentBean.setDate(new SimpleDateFormat().format(new Date()).toString());
        mCircleCommentAdapter.addModel(commentBean);
        mCircleCommentAdapter.notifyDataSetChanged();
        listView.setSelection(mCircleCommentAdapter.getCount() - 1);// 将光标移动到加载的交界处
        onLoad();
    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
}
