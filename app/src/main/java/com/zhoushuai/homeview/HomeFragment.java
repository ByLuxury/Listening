package com.zhoushuai.homeview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhoushuai.bean.RefreshMsgBean;
import com.zhoushuai.listening.NetRefresh;
import com.zhoushuai.listening.R;
import com.zhoushuai.net.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.list;

/**
 * Created by zhoushuai on 17/04/2017.
 */
public class HomeFragment extends Fragment implements RefreshListView.IXListViewListener {

    private RefreshListView listView;
    // ListView的适配器
    private CommentAdapter commentAdapter;
    private Button btnSendComment;
    private View view;
    private EditText etComment;
    private Button mTextWatchMore;
    private Handler handler;
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        initView();
        return view;
    }


    void initView() {
        btnSendComment = (Button) view.findViewById(R.id.btnSendComment);
        etComment = (EditText) view.findViewById(R.id.etComment);
        listView = (RefreshListView) view.findViewById(R.id.listView);
        commentAdapter = new CommentAdapter(getActivity(), R.layout.comment_listview_item, getActivity());

        //listview头部视图
        LayoutInflater hearderViewLayout = LayoutInflater.from(getActivity());
        LinearLayout layoutHeader = (LinearLayout) hearderViewLayout.inflate(R.layout.home_listview_header, null);
        listView.addHeaderView(layoutHeader);//添加头部


        //listview底部视图
        LayoutInflater footerViewLayout = LayoutInflater.from(getActivity());
        LinearLayout layoutFooter = (LinearLayout) footerViewLayout.inflate(R.layout.home_listview_footer, null);
        listView.addFooterView(layoutFooter, null, false);//不可点击
        mTextWatchMore = (Button) layoutFooter.findViewById(R.id.home_watch_more_btn);

        listView.setAdapter(commentAdapter);
        listView.setXListViewListener(this);// 添加ListView的上拉和下拉刷新监听器
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        initListData();
        listView.setOnItemClickListener(new ListViewListener());
        btnSendComment.setOnClickListener(new BtnSendCommentListener());

        layoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CircleActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 填充测试数据
     */
    void initListData() {

        CommentBean commentBean = new CommentBean();//实例化评论javabean
        commentBean.setImgHead(R.drawable.yu);
        commentBean.setName("渝哥");
        commentBean.setContent(getResources().getString(R.string.home_desc_test));
        commentBean.setType(FinalStatus.MSG_TEXT);
        commentBean.setAgree(false);
        commentBean.setDate(new SimpleDateFormat().format(new Date()).toString());
        commentAdapter.addModel(commentBean);

        CommentBean commentBean2 = new CommentBean();
        commentBean2.setImgHead(R.drawable.yu);
        commentBean2.setName("渝哥");
        commentBean2.setContent(getResources().getString(R.string.home_how_to_stydy));
        commentBean2.setType(FinalStatus.MSG_IMAGE);
        commentBean2.setDate(new SimpleDateFormat().format(new Date()).toString());
        List<String> imageUrls = new ArrayList<>();//图片集合
        imageUrls.add("http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg");
        commentBean2.setImageUrls(imageUrls);
        commentAdapter.addModel(commentBean2);

        CommentBean commentBean3 = new CommentBean();//实例化评论javabean
        commentBean3.setImgHead(R.drawable.yu);
        commentBean3.setName("渝哥");
        commentBean3.setContent(getResources().getString(R.string.home_desc_test));
        commentBean3.setType(FinalStatus.MSG_TEXT);
        commentBean3.setAgree(false);
        commentBean3.setDate(new SimpleDateFormat().format(new Date()).toString());
        commentAdapter.addModel(commentBean);
        commentAdapter.notifyDataSetChanged();

        //refreshListViewInBackground();
    }

    /**
     * ListView单击事件
     */
    class ListViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }


    class BtnSendCommentListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String comment = etComment.getEditableText().toString();
            Toast.makeText(getActivity(), comment, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 读取json格式字符串
     *
     * @param is
     * @return
     */
    private String readData(InputStream is) {
        InputStreamReader isr;
        StringBuffer result = new StringBuffer();
        BufferedReader br;
        try {
            String line = "";
            isr = new InputStreamReader(is, Config.CHARSET);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onRefresh() {
        refreshListViewInBackground();
    }


    @UiThread
    void refreshListViewInBackground() {// 刷新数据
        String phone = getActivity().getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        new NetRefresh(Config.RefreshURL, phone, new NetRefresh.IReFreshSuccessCallback() {
            @Override
            public void onSuccess(String data) {
                JSONArray jsonArray = null;
                //Log.i("AAA", data);
                List<RefreshMsgBean> list = new ArrayList<>();
                try {

                    jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RefreshMsgBean refreshMsgBean = new RefreshMsgBean();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String name = jsonObject.getString(Config.KEY_NICKNAME);
                        String msg = jsonObject.getString(Config.KEY_MSG);
                        refreshMsgBean.setNickname(name);
                        refreshMsgBean.setMsg(msg);
                        list.add(refreshMsgBean);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                    for (int i = index; i <= index&&index<list.size(); i++) {
                        CommentBean commentBean = new CommentBean();
                        commentBean.setImgHead(R.drawable.logo);
                        commentBean.setName(list.get(i).getNickname());
                        commentBean.setContent(list.get(i).getMsg());
                        commentBean.setType(FinalStatus.MSG_TEXT);//消息类型
                        commentBean.setAgree(false);//点赞
                        commentBean.setDate(new SimpleDateFormat().format(new Date()).toString());
                        commentAdapter.addModel(commentBean, true);//添加到adpater
                        commentAdapter.notifyDataSetChanged();//更新视图

                }
                index++;

            }
        }, new NetRefresh.IRefreshFailCallback() {
            @Override
            public void onFail() {

            }
        });

        onLoad();
    }

    public interface IReFreshSuccessCallback {
        void onSuccess(String data);
    }

    public interface IRefreshFailCallback {
        void onFail();
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
        commentAdapter.addModel(commentBean);
        commentAdapter.notifyDataSetChanged();
        listView.setSelection(commentAdapter.getCount() - 1);// 将光标移动到加载的交界处
        onLoad();
    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));

    }

}
