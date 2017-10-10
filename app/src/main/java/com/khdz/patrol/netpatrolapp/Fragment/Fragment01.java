package com.khdz.patrol.netpatrolapp.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.khdz.patrol.netpatrolapp.Activity.DeviceInfoActivity;
import com.khdz.patrol.netpatrolapp.Activity.PatrolRecordActivity;
import com.khdz.patrol.netpatrolapp.Device.SerialManager;
import com.khdz.patrol.netpatrolapp.R;

/**
 * Created by Jason_boy5 on 2017/9/14.
 */

public class Fragment01 extends Fragment implements View.OnClickListener {
    private final String TAG = Fragment01.class.getSimpleName();

    SerialManager serialManager = SerialManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment01, container, false);

        initView(rootView);//控件初始化

        return rootView;
    }

    private void initView(View rootView) {
        Button btn_uploadPatrolRecord = (Button) rootView.findViewById(R.id.btn_uploadPatrolRecord);
        Button btn_queryPatrolRecord = (Button) rootView.findViewById(R.id.btn_queryPatrolRecord);
        Button btn_queryCheckResult = (Button) rootView.findViewById(R.id.btn_queryCheckResult);
        Button btn_queryCountMap = (Button) rootView.findViewById(R.id.btn_queryCountMap);
        Button btn_testDevice = (Button) rootView.findViewById(R.id.btn_testDevice);
        ImageView ivLogo = (ImageView) rootView.findViewById(R.id.ivLogo);

        btn_uploadPatrolRecord.setOnClickListener(this);
        btn_queryPatrolRecord.setOnClickListener(this);
        btn_queryCheckResult.setOnClickListener(this);
        btn_queryCountMap.setOnClickListener(this);
        btn_testDevice.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_uploadPatrolRecord:
                if (null == serialManager.getsDriver()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MachineNotFoundFragmentA.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PatrolRecordFragmentA.class);
                    startActivity(intent);
                }

                break;

            case R.id.btn_queryPatrolRecord:
                if (null == serialManager.getsDriver()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MachineNotFoundFragmentA.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PatrolRecordActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btn_queryCheckResult:
                break;

            case R.id.btn_queryCountMap:
                break;

            case R.id.btn_testDevice:
                if (null == serialManager.getsDriver()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MachineNotFoundFragmentA.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DeviceInfoActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.ivLogo:
                String url = "http://www.kai-he.com"; // web address
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
