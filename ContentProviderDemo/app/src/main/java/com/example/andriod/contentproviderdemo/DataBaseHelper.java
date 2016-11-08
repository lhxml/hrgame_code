package com.example.andriod.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andriod on 2016/11/7.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Provider.PersonColumns.TABLE_NAME + " ("
                + Provider.PersonColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.PersonColumns.NAME + " TEXT,"
                + Provider.PersonColumns.ID + " INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Provider.PersonColumns.TABLE_NAME);
        onCreate(db);
    }
}
