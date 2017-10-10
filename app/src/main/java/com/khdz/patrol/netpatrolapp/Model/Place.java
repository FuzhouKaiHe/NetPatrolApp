package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by fengscar on 2016/10/19.
 * 巡检地点
 */

public class Place {
    private int mId;
    private int mOrder;//地点序号
    private String mName;
    private String mCard;
    private int mRouteId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        mOrder = order;
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

    public int getRouteId() {
        return mRouteId;
    }

    public void setRouteId(int routeId) {
        mRouteId = routeId;
    }
}
