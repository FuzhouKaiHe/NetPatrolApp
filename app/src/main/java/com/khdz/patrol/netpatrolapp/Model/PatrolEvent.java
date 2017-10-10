package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by Administrator on 2017/9/15.
 * 巡检事件
 */

public class PatrolEvent {
    private int mId;
    private String mName;
    private String mCard;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCard() {
        return mCard;
    }

    public void setmCard(String mCard) {
        this.mCard = mCard;
    }
}
