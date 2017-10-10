package com.khdz.patrol.netpatrolapp.Device.Usb;

import com.khdz.patrol.netpatrolapp.Model.DeviceRecord;
import com.khdz.patrol.netpatrolapp.Model.Employee;
import com.khdz.patrol.netpatrolapp.Model.HitRecord;
import com.khdz.patrol.netpatrolapp.Model.Route;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class DeviceComm implements IDeviceComm {
    @Override//获取巡更机的读卡记录
    public List<DeviceRecord> getPatrolRecords() throws SerialException {
        return null;
    }

    @Override//清除巡更机的读卡记录
    public boolean clearPatrolRecords() throws SerialException {
        return false;
    }

    @Override//获取巡更机的撞击记录
    public List<HitRecord> getHitRecords() throws SerialException {
        return null;
    }

    @Override//清除巡更机的撞击记录
    public boolean clearHitRecords() throws SerialException {
        return false;
    }

    @Override//获取巡更机机号
    public String getDeviceId() throws SerialException {
        return null;
    }

    @Override//型号
    public String getDeviceType() throws SerialException {
        return null;
    }

    @Override//版本号
    public String getDeviceVersion() throws SerialException {
        return null;
    }

    @Override//时间
    public Date getDeviceTime() throws SerialException {
        return null;
    }

    @Override//公司名字
    public String getCompanyName() throws SerialException {
        return null;
    }

    @Override//公司标语
    public String getCompanyTel() throws SerialException {
        return null;
    }

    @Override//设置闹钟
    public boolean ConfigDeviceAlarms(List<Date> alarms) {
        return false;
    }

    @Override//调整巡更机的时间
    public boolean AdjustDeviceTime(Date time) throws SerialException {
        return false;
    }

    @Override//配置人员
    public boolean ConfigDeviceEmploees(List<Employee> employees) throws SerialException {
        return false;
    }

    @Override//配置路线
    public boolean ConfigDeviceRoutes(List<Route> routes) throws SerialException {
        return false;
    }

    @Override//测试LED灯
    public boolean TestLED() throws SerialException {
        return false;
    }

    @Override//测试蜂鸣器
    public boolean TestBuzzer() throws SerialException {
        return false;
    }
}
