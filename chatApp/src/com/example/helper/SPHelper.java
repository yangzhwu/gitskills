package com.example.helper;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/*
 * 单例模式
 */
public class SPHelper {
	private static SharedPreferences mSharedPreferences = null;
	private static SharedPreferences.Editor editor = null;
	private static SPHelper mSPHelper = null;
	//默认经纬度为北京市中心经纬度
	private final static double def_lat = 39.91677;  
	private final static double def_long = 116.41667;
    
	public static SPHelper getInstance() {
		if (mSPHelper == null) {
			mSPHelper = new SPHelper();
		}
		return mSPHelper;
	}
	private SPHelper() {
		
	}
	
	public static void init(Context context) {
		if (mSharedPreferences == null) {
			Log.e("sp初始化", "初始化完毕");
		    mSharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		    editor = mSharedPreferences.edit();
		}
	}
	
	/*
	 * 存储位置纬度信息
	 */
	public void saveLat(double lat) {
		String latitude = String.valueOf(lat);
		editor.putString("lat", latitude);
		editor.commit();
	}
	
	/*
	 * 获取位置纬度信息
	 */
	public double getLat() {
		String latitude = mSharedPreferences.getString("lat", null);
		if (latitude == null) {
			return def_lat;
		}
		else {
			return Double.parseDouble(latitude);
		}
	}
	
	/*
	 * 存储位置经度信息
	 */
	public void saveLng(double lng) {
		String lngitude = String.valueOf(lng);
		editor.putString("lng", lngitude);
		editor.commit();
	}
	
	/*
	 * 获取位置经度信息
	 */
	public double getLng() {
		String lng = mSharedPreferences.getString("lng", null);
		if (lng == null) {
			return def_long;
		}
		else {
			return Double.parseDouble(lng);
		}
	}
	
	
}
