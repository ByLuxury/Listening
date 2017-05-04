package com.zhoushuai.bean;

import java.util.List;

/**
 * Created by zhoushuai on 02/05/2017.
 */

public class RefreshMsgBean {
    private String nickname;//昵称
    private String msg;//消息
    private List<RefreshMsgBean> list;

    public List<RefreshMsgBean> getList() {
        return list;
    }

    public void setList(List<RefreshMsgBean> list) {
        this.list = list;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMsg() {
        return msg;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
