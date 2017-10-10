package com.khdz.patrol.netpatrolapp.Fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khdz.patrol.netpatrolapp.R;

/**
 * Created by Administrator on 2017/9/26.
 */

public class DialogDelete extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete,container);
        return view;
    }
}
