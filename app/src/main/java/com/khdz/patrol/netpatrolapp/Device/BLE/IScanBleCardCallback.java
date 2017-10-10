package com.khdz.patrol.netpatrolapp.Device.BLE;

import android.bluetooth.le.ScanResult;

/**
 * Created by fengscar on 2017/9/2.
 * 扫描到蓝牙卡时的操作
 */
///////////////////////////
//    bytes:扫描到的蓝牙广播信息
//    stringID:扫描到的蓝牙卡ID
///////////////////////////
public interface IScanBleCardCallback {
    void onScanBleCard(ScanResult result);
}
