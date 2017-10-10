package com.khdz.patrol.netpatrolapp.Model;

import java.util.List;

/**
 * Created by fengscar on 2017/9/2.
 * 巡检线路
 */

public class Route {
    private int mId;//主键
    private String mName;// 路线名字
    private List<Place> mPlaces;//路线包含的巡更点
    private int mStationId;//工作站

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(List<Place> places) {
        mPlaces = places;
    }

    public int getStationId() {
        return mStationId;
    }

    public void setStationId(int stationId) {
        mStationId = stationId;
    }
}
