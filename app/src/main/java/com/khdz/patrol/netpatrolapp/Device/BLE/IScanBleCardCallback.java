package com.khdz.patrol.netpatrolapp.Device.BLE;

import java.util.Date;

/**
 * Created by fengscar on 2017/9/2.
 * 扫描到蓝牙卡时的操作
 */

public interface IScanBleCardCallback {
    void onScanBleCard(String card,Date time);
}
