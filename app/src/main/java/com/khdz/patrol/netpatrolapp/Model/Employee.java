package com.khdz.patrol.netpatrolapp.Model;

/**
 * Created by fengscar on 2017/9/2.
 * 巡检员
 */

public class Employee extends User {
    private String mCard;

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCard() {
        return mCard;
    }

    public void setCard(String card) {
        mCard = card;
    }

}
