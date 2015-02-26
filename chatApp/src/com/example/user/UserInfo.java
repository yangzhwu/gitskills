package com.example.user;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class UserInfo extends BmobChatUser {
	private String mSex;
	
	private BmobGeoPoint mLocation = null;
	
	public void setSex(String sex) {
		mSex = sex;
	}
	
	public String getSex() {
		return mSex;
	}
	
	public void setLocation(BmobGeoPoint location) {
		mLocation = location;
	}
	
	public BmobGeoPoint getLocation() {
		return mLocation;
	}

}
