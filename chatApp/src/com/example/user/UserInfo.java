package com.example.user;

import cn.bmob.im.bean.BmobChatUser;

public class UserInfo extends BmobChatUser {
	private String mSex;
	
	/*
	 * 默认为男性
	 */
	public UserInfo() {
		mSex = "男";
	}
	
	public void setSex(String sex) {
		mSex = sex;
	}
	
	public String getSex() {
		return mSex;
	}

}
