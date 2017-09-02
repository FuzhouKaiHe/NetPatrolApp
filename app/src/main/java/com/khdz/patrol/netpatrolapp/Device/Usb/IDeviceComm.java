package com.khdz.patrol.netpatrolapp.Device.Usb;

import com.khdz.patrol.netpatrolapp.Model.Employee;
import com.khdz.patrol.netpatrolapp.Model.HitRecord;
import com.khdz.patrol.netpatrolapp.Model.PatrolRecord;
import com.khdz.patrol.netpatrolapp.Model.Route;

import java.util.Date;
import java.util.List;

/**
 * Created by fengscar on 2017/9/1.
 * 设备通信接口: 获取巡更机设备的数据.
 */

public interface IDeviceComm {

    //region 获取设备记录
    /**
     * 获取当前连接的巡更机的所有读卡记录
     * 1.获取机号
     * 2.获取记录数
     * 3.读取每条记录
     * @return 所有读卡记录
     * @throws SerialException 当获取失败时抛出异常
     */
    List<PatrolRecord> getPatrolRecords() throws SerialException;

    /**
     * 清空巡检机中的读卡记录
     * 该操作大概需要 40 秒
     * @return 操作结果
     * @throws SerialException  当清空失败时抛出异常
     */
    boolean clearPatrolRecords() throws SerialException;

    /**
     * 获取当前连接的巡更机的所有敲击记录
     *
     * @return 所有敲击记录
     * @throws SerialException 当获取失败时抛出异常
     */
    List<HitRecord> getHitRecords() throws SerialException;

    /**
     * 清空巡检机中的撞击记录
     * 该操作大概需要 4秒
     * @return 操作结果
     * @throws SerialException  当清空失败时抛出异常
     */
    boolean clearHitRecords() throws SerialException;

    //endregion

    //region 获取设备信息
    String getDeviceId() throws SerialException;

    String getDeviceType() throws SerialException;

    String getDeviceVersion() throws SerialException;

    Date getDeviceTime() throws SerialException;

    String getCompanyName() throws SerialException;

    String getCompanyTel() throws SerialException;
    //endregion

    /**
     * 设置闹钟...
     * 最多可以设置24个闹钟.
     * 设置之前要先清空,清空大概需要2秒.
      * @param alarms 所有要设置的闹钟时间
     * @return 设置结果
     */
    boolean ConfigDeviceAlarms(List<Date> alarms);

    /**
     * 校准巡检机时间
     * @param time 当前时间
     * @return 操作结果
     * @throws SerialException
     */
    boolean AdjustDeviceTime(Date time) throws SerialException;

    //region 配置设备中的路线和人员
    /**
     * 将巡检员信息发送到巡检机
     * 1.清空巡检机中的巡检员数据 该操作大概需要5秒
     * 2.设置巡检员记录数量
     * 3.设置具体的巡检员信息
     * 4.获取巡检机中的巡检员数据
     * 5.与参数中的信息进行对比并返回对比结果
     * @param employees 要发送的巡检员
     * @return 操作结果
     * @throws SerialException
     */
    boolean ConfigDeviceEmploees(List<Employee> employees) throws SerialException;

    /**
     * 将巡检员线路发送到巡检机
     * 1.清空巡检机中的线路数据 该操作大概需要10秒
     * 2.设置巡检线路数量
     * 3.设置每条线路的巡检地点数量
     * 4.设置具体的线路信息
     * 5.设置具体的地点信息
     * 6.获取巡检机中的线路信息
     * 7.与参数中的信息进行对比并返回对比结果
     * @param routes 要发送的线路信息
     * @return 操作结果
     * @throws SerialException
     */
    boolean ConfigDeviceRoutes(List<Route> routes) throws SerialException;
    //endregion

    //region 设备测试
    boolean TestLED() throws SerialException;

    boolean TestBuzzer() throws SerialException;
    //endregion
}
