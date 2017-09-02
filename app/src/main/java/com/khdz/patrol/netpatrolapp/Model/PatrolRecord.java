package com.khdz.patrol.netpatrolapp.Model;

import java.util.Date;

/**
 * Created by fengscar on 2017/9/1.
 * 巡更机的巡检记录
 */

public class PatrolRecord {
    private String mDeviceId;//巡检机机号
    private String mCard;
    private Date mTime;

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getCard() {
        return mCard;
    }

    public void setCard(String card) {
        mCard = card;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public PatrolRecord(String deviceId, String card, Date time) {
        mDeviceId = deviceId;
        mCard = card;
        mTime = time;
    }
}
