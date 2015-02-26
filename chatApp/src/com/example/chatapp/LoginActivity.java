package com.example.chatapp;

import com.example.base.BaseActivity;
import com.example.constant.BroadcastConstant;
import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.example.user.UserInfo;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.SaveListener;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener{
    private EditText mPhoneNumber = null;
    private EditText mPassWord = null;
    private TextView mLogin = null;
    private TextView mCan_not_login = null;
    private TextView mNewUser = null;
    private RegisterSuccessReceiver mReceiver = null;
    
    /*
	 * 设置头部不可见
	 */
	@Override
	public void setHeadVisible() {
		// TODO Auto-generated method stub
		header.setVisibility(View.GONE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
		mPhoneNumber = (EditText) view.findViewById(R.id.phone_number);
    	mPassWord = (EditText) view.findViewById(R.id.pass_word);
    	mLogin = (TextView) view.findViewById(R.id.login);
    	mCan_not_login = (TextView) view.findViewById(R.id.can_not_login);
    	mNewUser = (TextView) view.findViewById(R.id.new_user);
    	mUserManager = MyApplication.getUserManager();
    	
    	mLogin.setOnClickListener(this);
    	mCan_not_login.setOnClickListener(this);
    	mNewUser.setOnClickListener(this);
    	initReceiver();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.login:
			login();
			break;
		case R.id.can_not_login:
			break;
		case R.id.new_user:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			break;
			default:
				break;
		}
	}
	
	/*
	 * 初始化广播接收器
	 */
	public void initReceiver() {
		mReceiver = new RegisterSuccessReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BroadcastConstant.REGISTER_SUCCESS);
		registerReceiver(mReceiver, intentFilter);
	}
	
	/*
	 * 注册成功监听广播
	 */
	private class RegisterSuccessReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent != null && intent.getAction().equals(BroadcastConstant.REGISTER_SUCCESS)) {
				LoginActivity.this.finish();
			}
		}
		
	}
	
	/*
	 * 登陆
	 */
	public void login() {
		String phone_number = mPhoneNumber.getText().toString();
		String pass_word = mPassWord.getText().toString();
		if (TextUtils.isEmpty(phone_number)) {
			ToastHelper.show(R.string.empty_phone_number);
			return;
		}
		if (TextUtils.isEmpty(pass_word)) {
			ToastHelper.show(R.string.empty_pass_word);
			return;
		}
		if (mUserManager == null) {
			ToastHelper.show(R.string.init_wrong);
			return;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(phone_number);
		userInfo.setPassword(pass_word);
		mPDHelper.show(R.string.loging);
		mUserManager.login(userInfo, new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show(R.string.login_failed);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				UserInfo userInfo = mUserManager.getCurrentUser(UserInfo.class);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		
	}
	
	/*
	 *退出注销广播
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
		super.onDestroy();
	}

	@Override
	public void setTitle() {
		
	}

	@Override
	public void setBackBtnVisible() {
		// TODO Auto-generated method stub
		back_btn.setVisibility(View.GONE);
	}
    
    
}
