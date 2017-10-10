package com.khdz.patrol.netpatrolapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khdz.patrol.netpatrolapp.Model.PatrolMen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class DataMethod {
    private DBHelper dbHelper;

    public DataMethod(Context context) {
        dbHelper = new DBHelper(context);
    }

    public String HandleToggle(){
        String result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = " SELECT RealTime FROM " + PatrolMen.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            cursor.moveToLast();
            result = cursor.getString(cursor.getColumnIndex("realTime"));
        }
        cursor.close();
        db.close();
        return result;
    }

    public int insert(PatrolMen patrolmen) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatrolMen.KEY_cardId, patrolmen.CardId);
        values.put(PatrolMen.KEY_realTime, patrolmen.RealTime);

        // Inserting Row
        long patrol_Id = db.insert(PatrolMen.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) patrol_Id;
    }

    public void clearTable(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "DELETE FROM "+ PatrolMen.TABLE;
        db.execSQL(Query);
        db.close();
    }

//    public String getNewReC(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String Query = "SELECT TOP 1 RealTime FROM "+ PatrolMen.TABLE + " ORDER BY ID desc ";
//
//        db.close();
//
//    }

    public ArrayList<HashMap<String, String>> getPatrolList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PatrolMen.KEY_ID + "," +
                PatrolMen.KEY_cardId + "," +
                PatrolMen.KEY_realTime +
                " FROM " + PatrolMen.TABLE;

        //PatrolMen patrolmen = new PatrolMen();
        ArrayList<HashMap<String, String>> patrolList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> patrolmen = new HashMap<String, String>();
                patrolmen.put("id", cursor.getString(cursor.getColumnIndex(PatrolMen.KEY_ID)));
                patrolmen.put("cardId", cursor.getString(cursor.getColumnIndex(PatrolMen.KEY_cardId)));
                patrolList.add(patrolmen);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return patrolList;

    }

    public PatrolMen getPatrolById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                PatrolMen.KEY_ID + "," +
                PatrolMen.KEY_cardId + "," +
                PatrolMen.KEY_realTime +
                " FROM " + PatrolMen.TABLE
                + " WHERE " +
                PatrolMen.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        PatrolMen patrolmen = new PatrolMen();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                patrolmen.Id =cursor.getInt(cursor.getColumnIndex(PatrolMen.KEY_ID));
                patrolmen.CardId =cursor.getString(cursor.getColumnIndex(PatrolMen.KEY_cardId));
                patrolmen.RealTime =cursor.getString(cursor.getColumnIndex(PatrolMen.KEY_realTime));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return patrolmen;
    }
}
