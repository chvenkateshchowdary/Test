package com.example.cyclone.cyclone.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import static android.provider.BaseColumns._ID;
import static com.example.cyclone.cyclone.data.LogContract.logEntry.Coloumn_Image;
import static com.example.cyclone.cyclone.data.LogContract.logEntry.Coloumn_Name_Of_Application;
import static com.example.cyclone.cyclone.data.LogContract.logEntry.Coloumn_Time_Used;
import static com.example.cyclone.cyclone.data.LogContract.logEntry.Coloumn_name_ID;
import static com.example.cyclone.cyclone.data.LogContract.logEntry.Table_Name;
import static java.sql.Types.BLOB;

/**
 * Created by ALAAP on 3/2/2017.
 */

public  class LogReader extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "Logs";


    public LogReader(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES = " CREATE TABLE " + Table_Name + "("
                + Coloumn_name_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Coloumn_Name_Of_Application + " TEXT,"
                + Coloumn_Time_Used + " INTEGER,"
                + Coloumn_Image + " BLOB);";
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public void insertData(String appname, int appusagetime, byte[] image) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Coloumn_Name_Of_Application, appname);
        contentValues.put(Coloumn_Time_Used, appusagetime);
        contentValues.put(Coloumn_Image, image);
        db.insert(Table_Name, null, contentValues);
    }

    public void updateData(String appname, int appusagetime, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("update " + Table_Name + " set Time_Used = " + appusagetime  +" where name = " + "'"  + appname + "'",null);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + Table_Name);
        this.onCreate(db);
    }
}

