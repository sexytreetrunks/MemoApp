package com.ex.memoapp.db;

import android.provider.BaseColumns;

public class MemoContract {
    private MemoContract(){}

    public static abstract class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "memo";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
