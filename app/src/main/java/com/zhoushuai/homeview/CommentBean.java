/*
 * $filename: Model.java,v $
 * $Date: 2014-4-27  $
 * Copyright (C) ZhengHaibo, Inc. All rights reserved.
 * This software is Made by Zhenghaibo.
 */
package com.zhoushuai.homeview;

import java.util.List;

/**
 * Created by zhoushuai on 17/04/2017.
 */
public class CommentBean {
    private int imgHead;// 头像资源ID
    private String name;// 姓名
    private String date;// 日期
    private int type;// 消息类型
    private boolean agree;//是否点过赞
    private List<String> agreeShow;//获得“赞”的姓名列表
    private List<String> comments;//用户评论列表

    public List<String> getAgreeShow() {
        return agreeShow;
    }

    public void setAgreeShow(List<String> agreeShow) {
        this.agreeShow = agreeShow;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    private String content;// 消息内容
    private List<String> imageUrls;// 图片的Url地址

    public int getImgHead() {
        return imgHead;
    }

    public void setImgHead(int imgHead) {
        this.imgHead = imgHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
