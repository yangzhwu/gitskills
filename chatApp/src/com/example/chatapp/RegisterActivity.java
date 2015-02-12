package com.example.chatapp;

import com.example.base.BaseActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeadVisible(RelativeLayout header) {
		// TODO Auto-generated method stub
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
		return null;
	}

}
