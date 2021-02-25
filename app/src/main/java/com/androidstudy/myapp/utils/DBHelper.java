package com.androidstudy.myapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "user.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user(id integer primary key autoincrement,username varcahr(20),password varchar(20))");
    }

    public void addData(SQLiteDatabase sqLiteDatabase, String name, String password) {
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", password);
        sqLiteDatabase.insert("user", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
