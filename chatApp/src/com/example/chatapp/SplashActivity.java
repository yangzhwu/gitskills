package com.example.chatapp;

import com.example.helper.PDHelper;

import cn.bmob.im.BmobChat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class SplashActivity extends FragmentActivity{
	private final static int GO_LOGIN = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		
		BmobChat.getInstance(this).init(MyApplication.getInstance().APP_KEY);
		MyApplication.getInstance().startLocation();
		myHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
	}
	
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
			case GO_LOGIN:
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		};
	};

}
