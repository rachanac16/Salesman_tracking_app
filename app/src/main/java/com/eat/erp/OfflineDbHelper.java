package com.eat.erp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OfflineDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME="OfflineDB.db";

    private static final String CREATE_TABLE_EVENTS="create table if not exists "+OfflineDatabase.Events.TABLE_NAME+"("+OfflineDatabase.Events.EVENT_NO+" INTEGER PRIMARY KEY, "+OfflineDatabase.Events.EVENT_NAME+" text, "+OfflineDatabase.Events.EVENT_TIME+" text, " +OfflineDatabase.Events.EVENT_JSON+" text);";

    private static final String CREATE_TABLE_VISIT_DATA="create table if not exists "+OfflineDatabase.VisitData.TABLE_NAME+"("+OfflineDatabase.VisitData.DAY+" text PRIMARY KEY, "+OfflineDatabase.VisitData.ACTUAL_COUNT+" text, "+OfflineDatabase.VisitData.UNPLANNED_COUNT+" text, " +OfflineDatabase.VisitData.TOTAL_COUNT+" text, " +OfflineDatabase.VisitData.P_CALL+" text);";

    private static final String DROP_TABLE="drop table "+OfflineDatabase.Events.TABLE_NAME;

    public OfflineDbHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION );
        Log.e("Database Operations", "Database Created..");;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_VISIT_DATA);
        Log.e("Database Operations", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void deletetableVisitData(SQLiteDatabase db){
        db.execSQL(" delete from "+ OfflineDatabase.VisitData.TABLE_NAME);
        Log.e("Database Operations", "Table contents deleted");
    }

    public void addEVENT(String event_name,String event_time, String event_json, SQLiteDatabase db){
        ContentValues contentValues =new ContentValues();
        contentValues.put(OfflineDatabase.Events.EVENT_NAME, event_name);
        contentValues.put(OfflineDatabase.Events.EVENT_TIME, event_time);
        contentValues.put(OfflineDatabase.Events.EVENT_JSON, event_json);

        db.insert(OfflineDatabase.Events.TABLE_NAME, null, contentValues);
        Log.e("Database Operations", "added event");
    }

    public Cursor readEVENT(SQLiteDatabase db){

        String projections[]={OfflineDatabase.Events.EVENT_NO, OfflineDatabase.Events.EVENT_NAME, OfflineDatabase.Events.EVENT_TIME,OfflineDatabase.Events.EVENT_JSON };

        Cursor cursor=db.query(OfflineDatabase.Events.TABLE_NAME,projections,null,null,null,null,null,null);

        return cursor;

    }

    public void addVISITDATA(String day,String actual_count, String unplanned_count, String total_count, String p_call, SQLiteDatabase db){
        ContentValues contentValues =new ContentValues();
        contentValues.put(OfflineDatabase.VisitData.DAY, day);
        contentValues.put(OfflineDatabase.VisitData.ACTUAL_COUNT, actual_count);
        contentValues.put(OfflineDatabase.VisitData.UNPLANNED_COUNT, unplanned_count);
        contentValues.put(OfflineDatabase.VisitData.TOTAL_COUNT, total_count);
        contentValues.put(OfflineDatabase.VisitData.P_CALL, p_call);

        db.insert(OfflineDatabase.VisitData.TABLE_NAME, null, contentValues);
        Log.e("Database Operations", "added visit data");
    }

    public Cursor readVISITDETAILS(SQLiteDatabase db){

        String projections[]={OfflineDatabase.VisitData.DAY, OfflineDatabase.VisitData.ACTUAL_COUNT, OfflineDatabase.VisitData.UNPLANNED_COUNT,OfflineDatabase.VisitData.TOTAL_COUNT, OfflineDatabase.VisitData.P_CALL};
        Cursor cursor=db.query(OfflineDatabase.VisitData.TABLE_NAME,projections,null,null,null,null,null,null);

        return cursor;

    }

}
