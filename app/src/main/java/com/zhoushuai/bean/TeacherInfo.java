package com.zhoushuai.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhoushuai on 26/04/2017.
 */
public class TeacherInfo implements Serializable {
    private String username;
    private String nickname;
    private String password;
    private String serial;
    private String phone;
    private String school;
    private String email;
    private String rank;
    private String circleName;


    public TeacherInfo() {

    }

    /**
     * 初始化进入
     *
     * @param phone
     * @param nickname
     * @param password
     */
    public TeacherInfo(String phone, String nickname, String password) {
        this.phone = phone;
        this.password = password;
        this.nickname = nickname;
    }

    public TeacherInfo(String email) {
        this.email = email;
    }

    public TeacherInfo(String rank, String school) {
        this.school = school;
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSerial() {
        return serial;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchool() {
        return school;
    }

    public String getEmail() {
        return email;
    }

    public String getRank() {
        return rank;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
