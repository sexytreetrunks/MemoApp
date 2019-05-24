package com.ex.memoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {
    // DB version
    private static final int DB_VERSION = 1;
    // DB name
    private static final String DB_NAME = "memodb.db";
    // sql - create table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MemoContract.MemoEntry.TABLE_NAME + "("
            + MemoContract.MemoEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MemoContract.MemoEntry.COLUMN_NAME_TITLE + " TEXT,"
            + MemoContract.MemoEntry.COLUMN_NAME_CONTENT + " TEXT,"
            + MemoContract.MemoEntry.COLUMN_NAME_DATE + " DATE DEFAULT CURRENT_DATE)";
    // sql - drop table
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;

    public MemoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
    }
}
