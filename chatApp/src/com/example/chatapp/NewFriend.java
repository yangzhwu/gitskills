package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;

import com.example.base.BaseActivity;

public class NewFriend extends BaseActivity{

	@Override
	public void setHeadVisible() {
		// TODO Auto-generated method stub
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.item_new_friend, null);
		
		return view;
	}

	@Override
	public void setTitle() {
		// TODO Auto-generated method stub
		title.setText(R.string.new_friend);
	}

	@Override
	public void setBackBtnVisible() {
		// TODO Auto-generated method stub
		back_btn.setVisibility(View.VISIBLE);
	}

}
