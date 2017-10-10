package com.khdz.patrol.netpatrolapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.khdz.patrol.netpatrolapp.App;
import com.khdz.patrol.netpatrolapp.Device.SerialManager;
import com.khdz.patrol.netpatrolapp.R;

public class FragmentMainActivity extends Activity implements OnClickListener {
    private final String TAG = FragmentMainActivity.class.getSimpleName();

    SerialManager serialManager = SerialManager.getInstance();

    private Fragment01 fragment01;
    private Fragment02 fragment02;
    private Fragment03 fragment03;
    private Fragment04 fragment04;

    private LinearLayout mTabBtn01;
    private LinearLayout mTabBtn02;
    private LinearLayout mTabBtn03;
    private LinearLayout mTabBtn04;


    private FragmentManager fragmentManager;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();//控件初始化、绑定
        fragmentManager = getFragmentManager();//获得碎片管理权限

        serialManager.InitUsb();

        setTabSelection(0);//设置默认显示的碎片模块0
    }

    private void initViews()//控件初始化
    {

        mTabBtn01 = (LinearLayout) findViewById(R.id.id_tab_bottom_01);
        mTabBtn02 = (LinearLayout) findViewById(R.id.id_tab_bottom_02);
        mTabBtn03 = (LinearLayout) findViewById(R.id.id_tab_bottom_03);
        mTabBtn04 = (LinearLayout) findViewById(R.id.id_tab_bottom_04);

        mTabBtn01.setOnClickListener(this);
        mTabBtn02.setOnClickListener(this);
        mTabBtn03.setOnClickListener(this);
        mTabBtn04.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_bottom_01:
                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_02:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_03:
                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_04:
                setTabSelection(3);
                break;

            default:
                break;
        }
    }


    @SuppressLint("NewApi")
    private void setTabSelection(int index)//碎片事务选择的逻辑控制
    {
        resetBtn();//重置所有按钮

        FragmentTransaction transaction = fragmentManager.beginTransaction();//碎片事务

        hideFragments(transaction);//隐藏所有的碎片事务
        switch (index) {
            case 0:
                ((ImageButton) mTabBtn01.findViewById(R.id.btn_tab_bottom_01))
                        .setImageResource(R.drawable.ic_action_menu_true);
                if (fragment01 == null) {
                    fragment01 = new Fragment01();
                    transaction.add(R.id.id_content, fragment01);
                } else {
                    transaction.show(fragment01);
                }
                break;
            case 1:
                ((ImageButton) mTabBtn02.findViewById(R.id.btn_tab_bottom_02))
                        .setImageResource(R.drawable.ic_action_search_true);
                if (fragment02 == null) {
                    fragment02 = new Fragment02();
                    transaction.add(R.id.id_content, fragment02);
                } else {
                    transaction.show(fragment02);
                }
                break;
            case 2:
                ((ImageButton) mTabBtn03.findViewById(R.id.btn_tab_bottom_03))
                        .setImageResource(R.drawable.ic_action_input_true);
                if (fragment03 == null) {
                    fragment03 = new Fragment03();
                    transaction.add(R.id.id_content, fragment03);
                } else {
                    transaction.show(fragment03);
                }
                break;
            case 3:
                ((ImageButton) mTabBtn04.findViewById(R.id.btn_tab_bottom_04))
                        .setImageResource(R.drawable.ic_action_setting_true);
                if (fragment04 == null) {
                    fragment04 = new Fragment04();
                    transaction.add(R.id.id_content, fragment04);
                } else {
                    transaction.show(fragment04);
                }
                break;
        }
        transaction.commit();
    }

    private void resetBtn()//重置所有按钮（灰色表示未被选择的碎片事务）
    {
        ((ImageButton) mTabBtn01.findViewById(R.id.btn_tab_bottom_01))
                .setImageResource(R.drawable.ic_action_menu_false);
        ((ImageButton) mTabBtn02.findViewById(R.id.btn_tab_bottom_02))
                .setImageResource(R.drawable.ic_action_search_false);
        ((ImageButton) mTabBtn03.findViewById(R.id.btn_tab_bottom_03))
                .setImageResource(R.drawable.ic_action_input_false);
        ((ImageButton) mTabBtn04.findViewById(R.id.btn_tab_bottom_04))
                .setImageResource(R.drawable.ic_action_setting_false);
    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction)//隐藏所有的碎片事务
    {
        if (fragment01 != null) {
            transaction.hide(fragment01);
        }
        if (fragment02 != null) {
            transaction.hide(fragment02);
        }
        if (fragment03 != null) {
            transaction.hide(fragment03);
        }
        if (fragment04 != null) {
            transaction.hide(fragment04);
        }

    }

    //region返回键的监听
    private long clickTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(App.sContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            Log.e(TAG, "exit application");
            this.finish();
            System.exit(0);
        }
    }
    //endregion

}
