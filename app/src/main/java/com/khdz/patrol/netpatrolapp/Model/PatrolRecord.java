package com.khdz.patrol.netpatrolapp.Model;

import java.util.Date;

/**
 * Created by fengscar on 2017/9/1.
 * 巡检记录
 */

public class PatrolRecord {
    private int mStationId;//工作站ID

    private Employee mEmployee; //巡检员信息
    private String mRouteName; //线路名称
    private Place mPlace; //巡检点信息
    private Date mPlaceTime; //读巡检点的时间
    private PatrolEvent mEvent; //巡检事件
    private Date mEventTime; //读巡检事件卡的时间

    public int getmStationId() {
        return mStationId;
    }

    public void setmStationId(int mStationId) {
        this.mStationId = mStationId;
    }

    public Employee getmEmployee() {
        return mEmployee;
    }

    public void setmEmployee(Employee mEmployee) {
        this.mEmployee = mEmployee;
    }

    public String getmRouteName() {
        return mRouteName;
    }

    public void setmRouteName(String mRouteName) {
        this.mRouteName = mRouteName;
    }

    public Place getmPlace() {
        return mPlace;
    }

    public void setmPlace(Place mPlace) {
        this.mPlace = mPlace;
    }

    public Date getmPlaceTime() {
        return mPlaceTime;
    }

    public void setmPlaceTime(Date mPlaceTime) {
        this.mPlaceTime = mPlaceTime;
    }

    public PatrolEvent getmEvent() {
        return mEvent;
    }

    public void setmEvent(PatrolEvent mEvent) {
        this.mEvent = mEvent;
    }

    public Date getmEventTime() {
        return mEventTime;
    }

    public void setmEventTime(Date mEventTime) {
        this.mEventTime = mEventTime;
    }
}
