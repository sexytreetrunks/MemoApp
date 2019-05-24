package com.ex.memoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ex.memoapp.vo.MemoVO;

public class MemoDAO {
    private Context context;
    private MemoDBHelper dbHelper;
    private SQLiteDatabase dbCon;

    public MemoDAO(Context context) {
        this.context = context;
        dbHelper = new MemoDBHelper(this.context);
    }

    private SQLiteDatabase getReadableConnection() {
        if(dbHelper==null)
            dbHelper = new MemoDBHelper(context);
        return dbHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableConnection() {
        if(dbHelper==null)
            dbHelper = new MemoDBHelper(context);
        return dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbCon!=null)
            dbCon.close();
        if(dbHelper!=null)
            dbHelper.close();
    }

    public long insert(MemoVO memoVO) {
        dbCon = getWritableConnection();
        long newRowId = 0;
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoVO.getTitle());
        values.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, memoVO.getContent());
        newRowId = dbCon.insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public boolean update(MemoVO memoVO) {
        dbCon = getWritableConnection();
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoVO.getTitle());
        values.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, memoVO.getContent());
        values.put(MemoContract.MemoEntry.COLUMN_NAME_DATE, getTodayDate());

        String selection = MemoContract.MemoEntry.COLUMN_NAME_ID + "=?";
        String[] selectionArgs = {String.valueOf(memoVO.getId())};

        return dbCon.update(MemoContract.MemoEntry.TABLE_NAME, values, selection, selectionArgs) > 0;
    }

    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean delete(int id) {
        dbCon = getWritableConnection();
        ContentValues values = new ContentValues();
        String selection = MemoContract.MemoEntry.COLUMN_NAME_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        return dbCon.delete(MemoContract.MemoEntry.TABLE_NAME, selection, selectionArgs) >0;
    }

    public MemoVO getOne(int id) {
        dbCon = getReadableConnection();
        MemoVO vo = new MemoVO();
        String selection = MemoContract.MemoEntry.COLUMN_NAME_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = dbCon.query(
                MemoContract.MemoEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.moveToNext()) {
            vo.setId(cursor.getInt(0));
            vo.setTitle(cursor.getString(1));
            vo.setContent(cursor.getString(2));
            vo.setDate(cursor.getString(3));
        }
        return vo;
    }

    public List<MemoVO> getAll() {
        dbCon = getReadableConnection();
        List<MemoVO> list = new ArrayList<MemoVO>();
        String sortOrder = MemoContract.MemoEntry.COLUMN_NAME_DATE + " DESC";
        Cursor cursor = dbCon.query(
                MemoContract.MemoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while (cursor.moveToNext()) {
            MemoVO vo = new MemoVO();
            vo.setId(cursor.getInt(0));
            vo.setTitle(cursor.getString(1));
            vo.setContent(cursor.getString(2));
            vo.setDate(cursor.getString(3));
            list.add(vo);
        }
        cursor.close();
        return list;
    }
}
