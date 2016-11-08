package com.example.andriod.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by andriod on 2016/11/7.
 */

public class PersonProvider extends ContentProvider{
    private static HashMap<String, String> sPersonsProjectionMap;
    private static final int PERSONS = 1;
    private static final int PERSONS_ID = 2;

    private static final UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 这个地方的persons要和PersonColumns.CONTENT_URI中最后面的一个Segment一致
        sUriMatcher.addURI(Provider.AUTHORITY, "persons", PERSONS);
        sUriMatcher.addURI(Provider.AUTHORITY, "persons/#", PERSONS_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.PersonColumns._ID, Provider.PersonColumns._ID);
        sPersonsProjectionMap.put(Provider.PersonColumns.NAME, Provider.PersonColumns.NAME);
        sPersonsProjectionMap.put(Provider.PersonColumns.ID, Provider.PersonColumns.ID);
    }

    private DataBaseHelper mOpenHelper;
    @Override
    public boolean onCreate() {
        mOpenHelper = new DataBaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(Provider.PersonColumns.TABLE_NAME);
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Provider.PersonColumns.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder,null);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        int match = sUriMatcher.match(uri);
        System.out.println("PersonProvider::uri="+uri+" match="+match);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues(contentValues);
        Long rawId = db.insert(Provider.PersonColumns.TABLE_NAME,null,values);
        if(rawId>0){
            Uri noteUri = ContentUris.withAppendedId(Provider.PersonColumns.CONTENT_URI,rawId);
            System.out.println("Provider::noteUri"+noteUri);
            getContext().getContentResolver().notifyChange(noteUri,null);
            return noteUri;
        }
        throw new SQLException("Failed to insert row into "+uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
