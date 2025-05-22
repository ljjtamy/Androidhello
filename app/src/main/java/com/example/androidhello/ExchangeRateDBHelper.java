package com.example.androidhello1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExchangeRateDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "exchange_rate.db";
    private static final int DATABASE_VERSION = 2; // 从1改为2
    
    public static final String TABLE_RATES = "rates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CURRENCY = "currency";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_UPDATE_DATE = "update_date";

    // 添加正确的构造函数
    public ExchangeRateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RATES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CURRENCY + " TEXT NOT NULL, " +
                COLUMN_RATE + " TEXT NOT NULL, " +
                COLUMN_UPDATE_DATE + " INTEGER);"; // 改为INTEGER存储时间戳
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATES);
            onCreate(db);
        }
    }
}