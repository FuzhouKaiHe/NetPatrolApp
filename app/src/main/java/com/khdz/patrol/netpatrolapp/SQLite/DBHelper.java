package com.khdz.patrol.netpatrolapp.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khdz.patrol.netpatrolapp.Model.PatrolMen;

/**
 * Created by Administrator on 2017/9/15.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "blue.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PATROLMEN = " CREATE TABLE " + PatrolMen.TABLE + " ( "
                + PatrolMen.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PatrolMen.KEY_mDeviceId + " TEXT, "
                + PatrolMen.KEY_cardId + " TEXT, "
                + PatrolMen.KEY_realTime + " DATETIME )";
        db.execSQL(CREATE_TABLE_PATROLMEN);

//        String CREATE_TABLE_DeviceRecord = " CREATE TABLE " + DeviceRecord.TABLE + " ( "
//                + DeviceRecord.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + DeviceRecord.KEY_mDeviceId + " TEXT, "
//                + DeviceRecord.KEY_cardId + " TEXT, "
//                + DeviceRecord.KEY_realTime + " DATETIME )";
//        db.execSQL(CREATE_TABLE_DeviceRecord);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PatrolMen.TABLE);
        onCreate(db);
    }
}
