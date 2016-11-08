package com.example.andriod.contentproviderdemo;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ProviderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void change(View v){
        System.out.println("1111111");
        test();
    }
    public void test(){
        User u = new User();
        u.id = 99;
        u.name = "jack";
        int id = insert(u);
        query(id);
    }
    public int insert(User u){
        ContentValues values = new ContentValues();
        values.put(Provider.PersonColumns.NAME,u.name);
        values.put(Provider.PersonColumns.ID,u.id);
        Uri uri = getContentResolver().insert(Provider.PersonColumns.CONTENT_URI,values);
        Log.i(TAG, "insert uri="+uri);
        String lastPath = uri.getLastPathSegment();
        System.out.println("MainActivity==lastPath="+lastPath);
        if (TextUtils.isEmpty(lastPath)) {
            Log.i(TAG, "insert failure!");
        } else {
            Log.i(TAG, "insert success! the id is " + lastPath);
        }
        return Integer.parseInt(lastPath);
    }
    private void query(int id) {
        Cursor c = getContentResolver().query(Provider.PersonColumns.CONTENT_URI, new String[] { Provider.PersonColumns.NAME, Provider.PersonColumns.ID }, Provider.PersonColumns._ID + "=?", new String[] { id + "" }, null);
        if (c != null && c.moveToFirst()) {
            User u = new User();
            u.name = c.getString(c.getColumnIndexOrThrow(Provider.PersonColumns.NAME));
            u.id = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns.ID));
            Log.i(TAG, "person.name="+u.name+"---person.id="+u.id);
        } else {
            Log.i(TAG, "query failure!");
        }
    }


}
