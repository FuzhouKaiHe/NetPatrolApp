package com.khdz.patrol.netpatrolapp.Model;

import java.util.Date;

/**
 * Created by fengscar on 2017/9/1.
 * 敲击记录
 */

public class HitRecord {
    private String mDeviceId;//巡检机机号
    private Date mTime;

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }


    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public HitRecord(String deviceId, Date time) {
        mDeviceId = deviceId;
        mTime = time;
    }
}
