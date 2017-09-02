package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by fengscar on 2016/10/19.
 * 巡检地点
 */

public class Place {
    private int mId;
    private int mOrder;
    private int mName;
    private int mCard;
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

    public int getName() {
        return mName;
    }

    public void setName(int name) {
        mName = name;
    }

    public int getCard() {
        return mCard;
    }

    public void setCard(int card) {
        mCard = card;
    }

    public int getRouteId() {
        return mRouteId;
    }

    public void setRouteId(int routeId) {
        mRouteId = routeId;
    }
}
