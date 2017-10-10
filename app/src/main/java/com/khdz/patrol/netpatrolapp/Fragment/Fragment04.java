package com.khdz.patrol.netpatrolapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.khdz.patrol.netpatrolapp.Device.SendAndRcv;
import com.khdz.patrol.netpatrolapp.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jason_boy5 on 2017/9/14.
 */

public class Fragment04 extends Fragment{
    private final String TAG = Fragment04.class.getSimpleName();
    public UsbSerialDriver sDriver = null;
    private SendAndRcv sendAndRcv =SendAndRcv.getInstance();
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();//创建大小为1的固定线程池
    private SerialInputOutputManager mSerialIoManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment04,container,false);

//        View rootView = inflater.inflate(R.layout.fragment04, container, false);
//
//        initView(rootView);//控件初始化
//
////        getActivity();
//        sendAndRcv.setsDriver(FragmentMainActivity.getsDriver());
//        sDriver = sendAndRcv.getsDriver();
//        return rootView;
    }

//    private void initView(View rootView){
//        Button btn_set_clock = (Button)rootView.findViewById(R.id.btn_set_clock);
//        Button btn_check_time = (Button)rootView.findViewById(R.id.btn_check_time);
//        Button btn_rename = (Button)rootView.findViewById(R.id.btn_rename);
//        Button btn_toggle_user = (Button)rootView.findViewById(R.id.btn_toggle_user);
//
//        btn_set_clock.setOnClickListener(this);
//        btn_check_time.setOnClickListener(this);
//        btn_rename.setOnClickListener(this);
//        btn_toggle_user.setOnClickListener(this);
//    }
//
//    private final SerialInputOutputManager.Listener mListener =
//            new SerialInputOutputManager.Listener() {
//
//                @Override
//                public void onRunError(Exception e) {
//                    Log.d(TAG, "Runner stopped.");
//                }
//
//                @Override
//                public void onNewData(final byte[] data) {
////            List<byte[]> list = new ArrayList<>();
////            list.add(data);
//                    Observable.just(data)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Observer<byte[]>() {
//                                @Override
//                                public void onCompleted() {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onNext(byte[] bytes) {
//                                    dealData(bytes);
//                                }
//                            });
//                }
//            };
//
//    private void dealData(byte[] bytes) {//处理来自巡更机的应答数据
//        switch (bytes[0]) {
//            case (byte) 0xAA:
//                break;
//
//            case (byte) 0xEB:
//                switch (bytes[1]) {
//
//
////                    case (byte) 0x3D://撞击记录
////                        FlagUpdateList = 1;
////                        ReadRecordClick(bytes);
////                        break;
////
////                    case (byte) 0x66://清除巡检提醒时间
////                        Flag = ClearClock;
////                        break;
////
////                    case (byte) 0x86://设置巡检提醒时间
////
////                        ShowClock();
////                        break;
////
//                    case (byte) 0x84://时间校正
//                        Toast.makeText(App.sContext, "时间已校正！", Toast.LENGTH_SHORT).show();
//                        break;
////
////                    case (byte) 0x68://清除人员钮号和姓名记录
////                        Flag = ClearNumAndName;
////                        break;
////
////                    case (byte) 0x98://设置人员钮号和姓名记录数目
////                        Flag = SetNumSum;
////                        break;
////
////                    case (byte) 0x69://清除巡更点的钮号和地点名称记录
////                        Flag = ClearRecord;
////                        break;
////
////                    case (byte) 0x9A://设置线路记录数目
////                        Flag = SetRouteNum;
////                        break;
//
//                    default:
//                        break;
//                }
////                String temp = bytesToHexString(bytes);
////                Msg.append(temp);
////                Msg.append("\n");
////                Msg.append(temp.length());
////                Msg.append("\n");
//                break;
//
//            default:
//                break;
//        }
//    }//对接收到的数据进行处理
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_set_clock:
//                if (0 != linkOrNot()) {
//                    break;
//                }
//                break;
//
//            case R.id.btn_check_time:
//                if (0 != linkOrNot()) {
//                    break;
//                }
//                sendAndRcv.CheckTime();//时间校正
//                break;
//
//            case R.id.btn_rename:
//                if (0 != linkOrNot()) {
//                    break;
//                }
//                break;
//            case R.id.btn_toggle_user:
//                if (0 != linkOrNot()) {
//                    break;
//                }
//                break;
//
//            default:
//                break;
//
//        }
//    }
//
//    private int linkOrNot(){
//        if(null == sDriver){
//            Toast.makeText(App.sContext, "设备未连接！", Toast.LENGTH_SHORT).show();
//            return 1;
//        }else {
//            return 0;
//        }
//    }//是否获取Driver
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        stopIoManager();
//        if (sDriver != null) {
//            try {
//                sDriver.close();
//            } catch (IOException e) {
//                // Ignore.
//            }
//            sDriver = null;
//            sendAndRcv.setsDriver(null);
//        }
////        finish();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "Resumed, sDriver=" + sDriver);
//        if (sDriver == null) {
////            mTitleTextView.setText("No serial device.");
//        } else {
//            try {
//                sDriver.open();
//                sDriver.setParameters(115200, 8, UsbSerialDriver.STOPBITS_1, UsbSerialDriver.PARITY_NONE);
////                CurrentTime = Connect2Machine();
//            } catch (IOException e) {
//                Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
////                mTitleTextView.setText("Error opening device: " + e.getMessage());
//                try {
//                    sDriver.close();
//                } catch (IOException e2) {
//                    // Ignore.
//                }
//                sDriver = null;
//                sendAndRcv.setsDriver(null);
//                return;
//            }
////            mTitleTextView.setText("Serial device: " + sDriver.getClass().getSimpleName());
//        }
//        onDeviceStateChange();
//    }
//
//    private void stopIoManager() {
//        if (mSerialIoManager != null) {
//            Log.i(TAG, "Stopping io manager ..");
//            mSerialIoManager.stop();
//            mSerialIoManager = null;
//        }
//    }
//
//    private void startIoManager() {
//        if (sDriver != null) {
//            Log.i(TAG, "Starting io manager ..");
//            mSerialIoManager = new SerialInputOutputManager(sDriver, mListener);
//            mExecutor.submit(mSerialIoManager);
//        }
//    }
//
//    private void onDeviceStateChange() {
//        stopIoManager();
//        startIoManager();
//    }

}
