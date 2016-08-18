package com.example.threadlocaltest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private ThreadLocal<String> mThreadLocal = new ThreadLocal<String>();
	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mThreadLocal.set("this is thread==="+Thread.currentThread().getName());
		Log.d(TAG, "mThreadLocal's value===="+mThreadLocal.get());
		new Thread("Thread1"){
			public void run() {
				super.run();
				mThreadLocal.set("this is thread==="+Thread.currentThread().getName());
				Log.d(TAG, "mThreadLocal's value=="+mThreadLocal.get());
			};
		}.start();
		new Thread("Thread2"){
			public void run() {
				super.run();
				Log.d(TAG, "mThreadlocal's value==="+mThreadLocal.get());
			};
		}.start();
		
	}

	
}
