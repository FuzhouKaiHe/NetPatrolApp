package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by fengscar on 2017/9/2.
 * 用户
 */

public class User {
    protected int mId;//用户的ID,主键

    protected String mCompanyCode;//公司编号：8位
    protected String mUsername;//用户名
    protected String mPassword;//密码

    protected int mStationId;//工作站的ID

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCompanyCode() {
        return mCompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        mCompanyCode = companyCode;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getStationId() {
        return mStationId;
    }

    public void setStationId(int stationId) {
        mStationId = stationId;
    }
}
