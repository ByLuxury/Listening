package com.zhoushuai.homeview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.listening.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoushuai on 18/04/2017.
 */
public class CircleCommentAdapter extends BaseAdapter {

    private Context context;

    private Activity activity;

    private List<CommentBean> listViewData;

    private int layoutResId;// ListView每个Item的布局文件

    public CircleCommentAdapter(Context context, int layoutResId, Activity activity) {
        this.context = context;
        this.layoutResId = layoutResId;
        listViewData = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentBean commentBean = listViewData.get(position);
        ViewItemHolder viewItemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResId,
                    null);
            viewItemHolder = new ViewItemHolder();
            //从资源文件中获取组件
            viewItemHolder.imgHead = (ImageView) convertView
                    .findViewById(R.id.imgHead);
            viewItemHolder.tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            viewItemHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            viewItemHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);
            viewItemHolder.ivPhoto = (ImageView) convertView
                    .findViewById(R.id.ivPhoto);

            viewItemHolder.ivAgree = (ImageView) convertView
                    .findViewById(R.id.ivAgree);
            viewItemHolder.ivComment = (ImageView) convertView
                    .findViewById(R.id.ivComment);

            viewItemHolder.ivAgreeShow = (ImageView) convertView
                    .findViewById(R.id.ivAgreeShow);
            viewItemHolder.tvAgreeShow = (TextView) convertView
                    .findViewById(R.id.tvAgreeShow);
            viewItemHolder.tvComments = (TextView) convertView
                    .findViewById(R.id.tvComments);
            viewItemHolder.ivShare = (ImageView) convertView.findViewById(R.id.ivShare);
            convertView.setTag(viewItemHolder);//设置到view
        } else {
            viewItemHolder = (ViewItemHolder) convertView.getTag();
        }
        //给组件赋值
        viewItemHolder.imgHead.setImageBitmap(BitmapFactory.decodeResource(
                context.getResources(), commentBean.getImgHead()));
        viewItemHolder.tvName.setText(commentBean.getName());
        viewItemHolder.tvDate.setText(commentBean.getDate());
        viewItemHolder.tvContent.setText(commentBean.getContent());
        if (commentBean.getType() == FinalStatus.MSG_IMAGE) {// 图片资源
            viewItemHolder.ivPhoto.setImageResource(R.drawable.pic_screen);
            viewItemHolder.ivPhoto.setVisibility(View.VISIBLE);
        } else {
            viewItemHolder.ivPhoto.setVisibility(View.GONE);
        }
        //点赞按钮
        viewItemHolder.ivAgree
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        //分享监听
        viewItemHolder.ivShare.setOnClickListener(new ListViewButtonOnClickListener(position));

        if (commentBean.isAgree()) {
            viewItemHolder.ivAgree
                    .setImageResource(R.drawable.home_praise_icon);
        } else {
            viewItemHolder.ivAgree
                    .setImageResource(R.drawable.home_unpraise_icon);
        }
        viewItemHolder.ivAgree.setFocusable(false);
        if (null != commentBean.getAgreeShow() && commentBean.getAgreeShow().size() > 0) {
            viewItemHolder.ivAgreeShow.setVisibility(View.VISIBLE);
            viewItemHolder.tvAgreeShow.setVisibility(View.VISIBLE);
            viewItemHolder.tvAgreeShow.setText(commentBean.getAgreeShow().toString()
                    + "觉得很赞！");
        } else {
            viewItemHolder.ivAgreeShow.setVisibility(View.GONE);
            viewItemHolder.tvAgreeShow.setVisibility(View.GONE);
        }
        viewItemHolder.ivComment
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        viewItemHolder.ivComment.setFocusable(false);

        //发送评论
        if (null != commentBean.getComments() && commentBean.getComments().size() > 0) {
            viewItemHolder.tvComments.setVisibility(View.VISIBLE);
            String string = "";
            for (String comment : commentBean.getComments()) {
                string += "我:" + comment + "\n";
            }
            viewItemHolder.tvComments.setText(string);
        } else {
            viewItemHolder.tvComments.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {

        return listViewData.get(position);
    }

    @Override
    public int getCount() {

        if (null == listViewData) {
            return 0;
        }
        return listViewData.size();
    }

    /**
     * 添加一条记录
     *
     * @param commentBean
     */
    public void addModel(CommentBean commentBean) {
        listViewData.add(commentBean);
    }

    /**
     * 添加一条记录
     *
     * @param commentBean
     * @param insertHead
     */
    public void addModel(CommentBean commentBean, boolean insertHead) {
        if (insertHead) {
            listViewData.add(0, commentBean);
        } else {
            listViewData.add(commentBean);
        }
    }

    /**
     * 获取一条记录
     *
     * @param i
     * @return
     */
    public CommentBean getModel(int i) {
        if (i < 0 || i > listViewData.size() - 1) {
            return null;
        }
        return listViewData.get(i);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        listViewData.clear();
    }

    //ViewHolder
    class ViewItemHolder {
        ImageView imgHead;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        ImageView ivPhoto;
        ImageView ivAgree;
        ImageView ivComment;
        ImageView ivAgreeShow;
        TextView tvAgreeShow;
        TextView tvComments;
        ImageView ivShare;
    }

    /**
     * ListView点击事件
     */
    class ListViewButtonOnClickListener implements View.OnClickListener {
        private int position;// 记录ListView中Button所在的Item的位置

        /**
         * 各个监听事件
         *
         * @param position
         */
        public ListViewButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivAgree:
                    ImageView ivAgree = (ImageView) v;
                    CommentBean commentBean = listViewData.get(position);
                    List<String> agreeShow = commentBean.getAgreeShow();
                    if (null == agreeShow || agreeShow.size() <= 0) {
                        agreeShow = new ArrayList<>();
                    }
                    if (commentBean.isAgree()) {
                        agreeShow.remove("我");
                        ivAgree.setImageResource(R.drawable.home_unpraise_icon);
                    } else {
                        agreeShow.add("我");
                        ivAgree.setImageResource(R.drawable.home_praise_icon);
                    }
                    commentBean.setAgree(!commentBean.isAgree());
                    commentBean.setAgreeShow(agreeShow);
                    listViewData.set(position, commentBean);
                    notifyDataSetChanged();
                    break;
                case R.id.ivComment:
                    CommentBean commentBean1 = listViewData.get(position);
                    String nikename = commentBean1.getName();
                    activity.findViewById(R.id.circle_etComment).setVisibility(
                            View.VISIBLE);
                    activity.findViewById(R.id.circle_btnSendComment).setVisibility(
                            View.VISIBLE);
                    ((EditText) activity.findViewById(R.id.circle_etComment)).setHint("@"
                            + nikename);
                    activity.findViewById(R.id.circle_etComment).setFocusable(true);
                    activity.findViewById(R.id.circle_btnSendComment).setOnClickListener(
                            new ListViewButtonOnClickListener(position));
                    break;
                case R.id.circle_btnSendComment:
                    CommentBean mdl = listViewData.get(position);
                    List<String> commentsList = mdl.getComments();
                    String commentString = ((EditText) activity
                            .findViewById(R.id.circle_etComment)).getEditableText()
                            .toString();
                    if (null == commentsList || commentsList.size() <= 0) {
                        commentsList = new ArrayList<>();
                    }
                    commentsList.add(commentString);
                    mdl.setComments(commentsList);
                    listViewData.set(position, mdl);
                    notifyDataSetChanged();
                    ((EditText) activity.findViewById(R.id.circle_etComment)).setText("");
                    activity.findViewById(R.id.circle_etComment).setVisibility(View.GONE);
                    activity.findViewById(R.id.circle_btnSendComment).setVisibility(
                            View.GONE);
                    InputMethodManager imm2 = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm2.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
                case R.id.ivShare:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(intent, "分享"));
                    break;
                default:
                    break;
            }
        }
    }


}
