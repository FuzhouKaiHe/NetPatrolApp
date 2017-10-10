package com.khdz.patrol.netpatrolapp.Device;

import com.khdz.patrol.netpatrolapp.Model.DeviceRecord;
import com.khdz.patrol.netpatrolapp.Model.Employee;
import com.khdz.patrol.netpatrolapp.Model.FlagEnum;
import com.khdz.patrol.netpatrolapp.Model.HitRecord;
import com.khdz.patrol.netpatrolapp.Model.Place;
import com.khdz.patrol.netpatrolapp.Model.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.khdz.patrol.netpatrolapp.Device.CodeTrans.Byte2Gbk;
import static com.khdz.patrol.netpatrolapp.Device.CodeTrans.bytesToHexString;
import static java.lang.Thread.sleep;

/**
 * Created by Administrator on 2017/9/21.
 */

public class SendAndRcv {
    private final String TAG = SendAndRcv.class.getSimpleName();
    //////////////////////////////////////////////////

    //region 单例模式
    private static SendAndRcv instance;

    public static SendAndRcv getInstance() {
        if (instance == null) {
            synchronized (SendAndRcv.class){
                if(instance == null){
                    instance = new SendAndRcv();
                }
            }
        }
        return instance;
    }

    private SendAndRcv() {

    }
    //endregion


   private SerialManager serialManager = SerialManager.getInstance();

    private static long CurrentTime = 0;//连接巡更机的最新时间

    public void Send2Machine(final byte cmd, final byte[] data) throws IOException {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ReconnectOrNot();

                int n = 0;
                if (null != data) {
                    n = data.length;
                }
                byte[] Sum = new byte[n + 4];

                Sum[0] = (byte) 0xEB;//发送帧头

                Sum[1] = cmd;//命令

                int num;
                if (null == data) {//数据长度
                    num = 0;
                    Sum[2] = 0x00;
                } else {
                    num = data.length;
                    Sum[2] = (byte) num;
                }
                if (0 != num) {
                    System.arraycopy(data, 0, Sum, 3, num);//数据
                }

                byte CheckSum = 0x00;//帧校验和
                for (int i = 0; i < num + 3; i++) {
                    CheckSum += Sum[i];
                }

                Sum[3 + num] = CheckSum;
                try {
                    serialManager.getsDriver().write(Sum, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    sDriver.write(new byte[]{(byte) 0xEB},0);//发送帧头
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    sDriver.write(cmd,0);//命令
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                int num;
//                if(data == null){
//                    num =0;
//                }else{
//                    num = data.length;
//                }
//
//                try {
//                    sDriver.write(new byte[]{(byte) num},0);//数据长度
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                byte CheckSum = 0x00;
//                if(num != 0){
//                    for(int i = 0; i < data.length; i++){
//                        try {
//                            sDriver.write(new byte[]{data[i]},0);//数据
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        CheckSum += data[i];
//                    }
//                }
//
//                try {
//                    sDriver.write(new byte[]{CheckSum},0);//帧校验和
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }.start();
    }//对发送命令功能的封装

    public long Connect2Machine() {
        try {
//            Send2Machine((byte) 0xAA,null);
            byte[] connect = new byte[]{(byte) 0xAA};
            serialManager.getsDriver().write(connect, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }//发送连接巡更机的命令

    public int ReconnectOrNot() {
        long now = System.currentTimeMillis();
        if ((now - CurrentTime) > 2000) {
            CurrentTime = now;
            Connect2Machine();

        }
        return 0;
    }//是否需要重新连接巡更机

    public int ReadRecordNUM() {
        int RecordSum;
        byte[] num = new byte[2];
        try {
            Send2Machine((byte) 0x35, new byte[]{0x08, 0x08});
            int i = 0;
            while (i < 100) {
                if (FlagEnum.ReadRecordNUM == SerialManager.Flag) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    break;
                } else {
                    sleep(10);
                    i++;
                }
            }
            if (i >= 100) {
                return -1;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(serialManager.getRcv(), 3, num, 0, num.length);
        RecordSum = (int) num[1] & 0xFF + 256 * (int) num[0] & 0xFF;
        return RecordSum;
    }//读取巡更记录数

    public ArrayList<DeviceRecord> ReadRecords(int RecordCount) {
        ArrayList<DeviceRecord> deviceRecords = new ArrayList<>();
        try {
            Send2Machine((byte) 0x33, new byte[]{0x00, 0x04});
            int t1 = 0;
            while (t1 < 100) {
                if (FlagEnum.ReadMachineID == SerialManager.Flag) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    break;
                } else {
                    sleep(10);
                    t1++;
                }
                if (t1 >= 100) {
                    return null;
                }
            }
            byte[] temp = new byte[5];
            System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);
            String id = bytesToHexString(temp);

            for (int t = 0; t < RecordCount; t++) {
                int i = t / 256;
                int j = t % 256;
                Send2Machine((byte) 0x37, new byte[]{(byte) i, (byte) j, 0x08});
                int t2 = 0;
                while (t2 < 100) {//接收到巡更机的响应就break，否则就睡眠1ms
                    if (SerialManager.Flag == FlagEnum.ReadRecordNumIfo) {
                        SerialManager.Flag = FlagEnum.UnConnected;
                        byte[] tmp = new byte[6];
                        System.arraycopy(serialManager.getRcv(), 3, tmp, 0, 6);
                        Calendar c = Calendar.getInstance();//获取一个日历实例
                        c.set((int) tmp[0] + 2000, (int) tmp[1] - 1, (int) tmp[2], (int) tmp[3], (int) tmp[4], (int) tmp[5]);//设定日历的日期
                        Date date = c.getTime();
                        byte[] num = new byte[5];
                        System.arraycopy(serialManager.getRcv(), 9, num, 0, num.length);
                        String cardId = bytesToHexString(num);

                        DeviceRecord deviceRecord = new DeviceRecord(id, cardId, date);
                        deviceRecords.add(deviceRecord);
                        break;
                    } else {
                        sleep(10);
                        t2++;
                    }
                }
                if (t2 >= 100) {
                    return null;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return deviceRecords;
    }//读取所有的巡更记录

    public int ReadHitRecordNum() {
        int HitNum;
        byte[] tmp = new byte[2];
        try {
            Send2Machine((byte) 0x3C, new byte[]{0x0D, 0x0D});
            int i = 0;
            while (i < 100) {
                if (FlagEnum.ReadHitRecordNum == SerialManager.Flag) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    break;
                } else {
                    sleep(10);
                    i++;
                }
            }
            if (i >= 100) {
                return -1;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(serialManager.getRcv(), 3, tmp, 0, tmp.length);
        HitNum = (int) tmp[1] & 0xFF + 256 * (int) tmp[0] & 0xFF;
        return HitNum;
    }//撞击记录数

    public ArrayList<HitRecord> ReadHitReacords(int RecordCount) throws IOException, InterruptedException {
        ArrayList<HitRecord> hitRecords = new ArrayList<>();

        Send2Machine((byte) 0x33, new byte[]{0x00, 0x04});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadMachineID == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] temp = new byte[5];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);
        String id = bytesToHexString(temp);

        for (int t = 0; t < RecordCount; t++) {
            int i = t / 256;
            int j = t % 256;
            Send2Machine((byte) 0x39, new byte[]{(byte) i, (byte) j, 0x0E});
            int t2 = 0;
            while (t2 < 100) {//接收到巡更机的响应就break，否则就睡眠1ms
                if (SerialManager.Flag == FlagEnum.ReadHitRecords) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    byte[] tmp = new byte[6];
                    System.arraycopy(serialManager.getRcv(), 3, tmp, 0, 6);
                    Calendar c = Calendar.getInstance();//获取一个日历实例
                    c.set((int) tmp[0] + 2000, (int) tmp[1] - 1, (int) tmp[2], (int) tmp[3], (int) tmp[4], (int) tmp[5]);//设定日历的日期
                    Date date = c.getTime();

                    HitRecord hitRecord = new HitRecord(id, date);
                    hitRecords.add(hitRecord);
                    break;
                } else {
                    sleep(10);
                    t2++;
                }
            }
            if (t2 >= 100) {
                return null;
            }
        }
        return hitRecords;
    }//读取所有的撞击记录

    public boolean clearPatrolRecords() throws IOException, InterruptedException {
        Send2Machine((byte) 0x70, new byte[]{0x08, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        int i = 0;
        while (i < 40) {
            if (FlagEnum.ClearPatrolRecords == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                return true;
            } else {
                sleep(1000);
                i++;
            }
        }
        return false;
    }//清除巡更记录

    public boolean clearHitRecords() throws IOException, InterruptedException {
        Send2Machine((byte) 0x70, new byte[]{0x08, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        int i = 0;
        while (i < 40) {
            if (FlagEnum.ClearHitRecords == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                return true;
            } else {
                sleep(1000);
                i++;
            }
        }
        return false;
    }//清除撞击记录

    ///////////////////////////////////////////////////////////////////////////////////////////
    //获得巡更机的基本信息
    public String getDeviceId() throws IOException, InterruptedException {
        Send2Machine((byte) 0x33, new byte[]{0x00, 0x04});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadMachineID == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] temp = new byte[5];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);
        String id = bytesToHexString(temp);
        return id;
    }//获得巡更机 机号

    public String getDeviceType() throws IOException, InterruptedException {
        Send2Machine((byte) 0x51, new byte[]{0x05, 0x01});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadMachineType == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] temp = new byte[16];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);

        return Byte2Gbk(temp);
    }//获得巡更机 型号

    public String getDeviceVersion() throws IOException, InterruptedException {
        Send2Machine((byte) 0x5B, new byte[]{0x0A, 0x0B});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadMachineVersion == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] temp = new byte[8];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);

        return Byte2Gbk(temp);
    }//获得巡更机 版本号

    public Date getDeviceTime() throws IOException, InterruptedException {
        Send2Machine((byte) 0x31, null);
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.GetDeviceTime == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] tmp = new byte[6];
        System.arraycopy(serialManager.getRcv(), 3, tmp, 0, 6);
        Calendar c = Calendar.getInstance();//获取一个日历实例
        c.set((int) tmp[0] + 2000, (int) tmp[1] - 1, (int) tmp[2], (int) tmp[3], (int) tmp[4], (int) tmp[5]);//设定日历的日期

        return c.getTime();
    }//获得巡更机的 时间

    public String getCompanyName() throws IOException, InterruptedException {
        Send2Machine((byte) 0x12, new byte[]{0x01, 0x02});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadCompanyName == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }
        byte[] temp = new byte[32];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);
        return Byte2Gbk(temp);
    }//获得公司名字

    public String getCompanyTel() throws IOException, InterruptedException {
        Send2Machine((byte) 0x15, new byte[]{0x01, 0x05});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ReadCompanyLOG == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return null;
            }
        }

        byte[] temp = new byte[32];
        System.arraycopy(serialManager.getRcv(), 3, temp, 0, temp.length);

        return Byte2Gbk(temp);
    }//获得公司 电话

    ///////////////////////////////////////////////////////////////////////////
    ////测试
    public boolean TestLED() throws IOException, InterruptedException {
        Send2Machine((byte) 0x21, null);
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.TestLED == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }

        if (0x00 == serialManager.getRcv()[3]) {
            return true;
        } else {
            return false;
        }
    }//测试 LED

    public boolean TestBuzzer() throws IOException, InterruptedException {
        Send2Machine((byte) 0x20, null);
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.TestBUZZER == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }

        if (0x00 == serialManager.getRcv()[3]) {
            return true;
        } else {
            return false;
        }
    }//测试 蜂鸣器

    ///////////////////////////////////////////////////////////////////////////

    public boolean ConfigDeviceAlarms(ArrayList<Calendar> alarms) throws IOException, InterruptedException {

        Send2Machine((byte) 0x56, new byte[]{0x05, 0x0B, 0x06});
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ClearClock == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }

        for (int i = 0; i < alarms.size(); i++) {
            int m = alarms.get(i).get(Calendar.HOUR_OF_DAY);
            int n = alarms.get(i).get(Calendar.MINUTE);
            Send2Machine((byte) 0x26, new byte[]{(byte) i, (byte) m, (byte) n});
            int t2 = 0;
            while (t2 < 100) {
                if (SerialManager.Flag == FlagEnum.SetClockTime) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    break;
                } else {
                    sleep(10);
                    t2++;
                }
            }
            if (t2 >= 100) {
                return false;
            }
        }
        return true;

    }//设置巡更机 闹钟

    public boolean AdjustDeviceTime() throws IOException, InterruptedException {
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
//                int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = c.get(Calendar.MINUTE);//分
        int mSecond = c.get(Calendar.SECOND);//秒
        byte[] bytes = {(byte) (mYear - 2000), (byte) mMonth, (byte) mDay, (byte) mHour, (byte) mMinute, (byte) mSecond};

        Send2Machine((byte) 0xA3, bytes);
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.CheckTime == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                return true;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }
        return false;
    }//校正巡更机 时间

    public boolean ConfigDeviceEmploees(ArrayList<Employee> employees) throws IOException, InterruptedException {
        Send2Machine((byte) 0x58, new byte[]{0x05, 0x0D, 0x08});//清除人员钮号和姓名记录
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ClearNumAndName == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }

        Send2Machine((byte) 0x18, new byte[]{(byte) employees.size(), 0x08});//设置人员钮号和姓名记录数目
        int t2 = 0;
        while (t2 < 100) {
            if (FlagEnum.SetNumSum == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t2++;
            }
            if (t2 >= 100) {
                return false;
            }
        }

//        Employee employee = new Employee();
        int i = 0;
        for (Employee employee : employees) {
            Send2Machine((byte) 0x28, new byte[]{(byte) i, Byte.parseByte(employee.getCard()), Byte.parseByte(employee.getName())});//发送人员钮号和姓名记录
            int t3 = 0;
            while (t3 < 100) {
                if (FlagEnum.SendCardAndName == SerialManager.Flag) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    i++;
                    break;
                } else {
                    sleep(10);
                    t3++;
                }
                if (t3 >= 100) {
                    return false;
                }
            }
        }
        return true;
    }//配置设备中的 人员信息

    public boolean ConfigDeviceRoutes(List<Route> routes) throws IOException, InterruptedException {
        Send2Machine((byte) 0x59, new byte[]{0x05, 0x0E, 0x09});//25清除巡更点的钮号和地点名称记录
        int t1 = 0;
        while (t1 < 100) {
            if (FlagEnum.ClearPatrolAddressInfo == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t1++;
            }
            if (t1 >= 100) {
                return false;
            }
        }

        Send2Machine((byte) 0x1A, new byte[]{(byte) routes.size(), 0x0A});//38设置线路记录数目
        int t2 = 0;
        while (t2 < 100) {
            if (FlagEnum.SetRouteNum == SerialManager.Flag) {
                SerialManager.Flag = FlagEnum.UnConnected;
                break;
            } else {
                sleep(10);
                t2++;
            }
            if (t2 >= 100) {
                return false;
            }
        }

        for (Route route : routes) {
            Send2Machine((byte) 0x1C, new byte[]{(byte) route.getId(), (byte) route.getPlaces().size()});//40设置地点记录数目（巡更地点总数）
            int t3 = 0;
            while (t3 < 100) {
                if (FlagEnum.SetPatrolAddressNum == SerialManager.Flag) {
                    SerialManager.Flag = FlagEnum.UnConnected;
                    break;
                } else {
                    sleep(10);
                    t3++;
                }
                if (t3 >= 100) {
                    return false;
                }
            }
        }

        for (Route route : routes) {
            for (Place place : route.getPlaces()) {
                Send2Machine((byte) 0x29, new byte[]{(byte) route.getId(), (byte) place.getId(),
                        Byte.parseByte(place.getmCard()), Byte.parseByte(place.getmName())});//17设置每个巡更点的钮号和名字
                int t4 = 0;
                while (t4 < 100) {
                    if (FlagEnum.SendPatrolAddressInfo == SerialManager.Flag) {
                        SerialManager.Flag = FlagEnum.UnConnected;
                        break;
                    } else {
                        sleep(10);
                        t4++;
                    }
                    if (t4 >= 100) {
                        return false;
                    }
                }
            }

        }

        return true;

    }//配置 路线信息


}
