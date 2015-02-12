package com.example.user;

import cn.bmob.im.bean.BmobChatUser;

public class UserInfo extends BmobChatUser {
	private String mPhoneNumber = null;
	private String mPassWord = null;
	
	public UserInfo(String phoneNumber, String passWord) {
		this.mPhoneNumber = phoneNumber;
		this.mPassWord = passWord;
	}
	
	public String getPhoneNumber() {
		return mPhoneNumber;
	}
	
	public String getPassWord() {
		return mPassWord;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.mPhoneNumber = phoneNumber;
	}
	
	public void setPassWord(String passWord) {
		this.mPassWord = passWord;
	}

}
