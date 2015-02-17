package com.example.base;

import com.example.chatapp.MyApplication;

import cn.bmob.im.BmobUserManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment{
	protected BmobUserManager mUserManager = null;
	protected BaseActivity context = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mUserManager = MyApplication.getUserManager();
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (BaseActivity) activity;
	}
}
