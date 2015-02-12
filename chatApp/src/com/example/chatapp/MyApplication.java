package com.example.chatapp;

import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;
import android.app.Application;

public class MyApplication extends Application {
	private static MyApplication myApplication = null;
	private static BmobUserManager userManager = null;
	
	public final static String APP_KEY = "159888b60f02a078c5c7ac97cf1e9b89";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myApplication = this;
		
		//初始化Bomb
		initBomb(); 
	}
	
	public void initBomb() {
		Bmob.initialize(this, APP_KEY);
		userManager = BmobUserManager.getInstance(this);
	}
	
	public static MyApplication getInstance() {
		return myApplication;
	}
	
	public static BmobUserManager getUserManager() {
		return userManager;
	}

}
