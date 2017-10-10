package com.khdz.patrol.netpatrolapp.Model;

import java.util.Date;

/**
 * Created by fengscar on 2017/9/1.
 * 巡更机的巡检记录
 */

public class DeviceRecord {
    public static final String TABLE = "DeviceRecord";

    public static final String KEY_ID = "id";
    public static final String KEY_mDeviceId = "mDeviceId";
    public static final String KEY_cardId = "cardId";
    public static final String KEY_realTime = "realTime";


    private String mDeviceId;//巡检机机号 16进制
    private String mCard;//记录卡号
    private Date mTime;//记录卡号对应的时间

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

    public DeviceRecord(String deviceId, String card, Date time) {
        mDeviceId = deviceId;
        mCard = card;
        mTime = time;
    }
}
