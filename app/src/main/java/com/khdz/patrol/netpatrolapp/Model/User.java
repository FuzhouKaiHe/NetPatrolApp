package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by fengscar on 2017/9/2.
 * 用户
 */

public class User {
    protected int mId;
    protected String mUsername;
    protected String mPassword;

    protected String mCompanyCode;
    protected int mStationId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public String getCompanyCode() {
        return mCompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        mCompanyCode = companyCode;
    }

    public int getStationId() {
        return mStationId;
    }

    public void setStationId(int stationId) {
        mStationId = stationId;
    }
}
