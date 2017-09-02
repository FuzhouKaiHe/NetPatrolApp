package com.khdz.patrol.netpatrolapp.Device.BLE;

/**
 * Created by fengscar on 2017/9/2.
 * 蓝牙广播控制器
 */

public interface IBleScanController {

    void StartScan(IScanBleCardCallback scanBleCardCallback);

    void StopScan();
}
