package com.example.chatapp;

import java.io.File;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.example.helper.PDHelper;
import com.example.helper.SPHelper;
import com.example.helper.ToastHelper;
import com.example.user.UserInfo;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.UpdateListener;
import android.R.integer;
import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

public class MyApplication extends Application {
	private static MyApplication myApplication = null;
	private static BmobUserManager userManager = null;
	private String mAvaterDir = null;
	public static int screen_width = 0;
	public static int screen_height = 0;
	public final static String APP_KEY = "159888b60f02a078c5c7ac97cf1e9b89";
	private LocationManagerProxy mLocationManagerProxy = null;
	private MyLocationListener mMyLocationListener = null;    
	private double lat;
	private double lng;
	private boolean getLocationSuccess = false; 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myApplication = this;
		
		mMyLocationListener = new MyLocationListener();
		//初始化SharedPreferences
		SPHelper.init(this);

		// 获取屏幕宽高
		getWidthAndHeight();
		// 初始化Bomb
		initBomb();
		//初始化ImageLorder
		initImageLoader();
		//初始化Toast
		ToastHelper.init(this);
		ToastHelper.show("application onCreat");
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

	public void getWidthAndHeight() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screen_width = dm.widthPixels;
		screen_height = dm.heightPixels;
	}

	/*
	 * 根据用户名创建存储用户信息的文件
	 */
	public void createUserDir(String userName) {
		String subDir = "chatApp/" + userName;
		if (CheckSdCardIsExist()) {
			File path = new File(Environment.getExternalStorageDirectory(),
					subDir);
			if (!path.exists())
				path.mkdirs();
			mAvaterDir = path.getAbsolutePath();
		} else {
			File path = new File(this.getCacheDir(), userName);
			if (!path.exists())
				path.mkdirs();
			mAvaterDir = path.getAbsolutePath();
		}
	}

	public boolean CheckSdCardIsExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/*
	 * 获取用户信息存储路径
	 */
	public String getUserDir(String userName) {
		createUserDir(userName);
		return mAvaterDir;
	}

	public void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				// 线程池内加载的数量
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				 .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}
	
	/*
	 * 开始定位
	 */
	public void startLocation() {
		if (mLocationManagerProxy == null) {
			ToastHelper.show("开始定位");
		    mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		    mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 5 * 1000, 20, mMyLocationListener);
		}
	}
	
	/*
	 * 停止定位
	 */
	public void stopLocation() {
		mLocationManagerProxy.removeUpdates(mMyLocationListener);
		mLocationManagerProxy.destroy();
		mLocationManagerProxy = null;
	}
	
	/*
	 * 高德地图定位监听
	 */
	private class MyLocationListener implements AMapLocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLocationChanged(AMapLocation location) {
			// TODO Auto-generated method stub
			if (location != null && location.getAMapException().getErrorCode() == 0) {
				double new_lat = location.getLatitude();
				double new_lng = location.getLongitude();
				ToastHelper.show(new_lat + " " + new_lng + " " + lat + " " + lng);
				getLocationSuccess = true;
				if (new_lat != lat || new_lng != lng) {
					lat = new_lat;
					lng = new_lng;
					SPHelper.getInstance().saveLat(lat);
					SPHelper.getInstance().saveLng(lng);
					Log.e("updata", "updata has run");
				}
				else {
					stopLocation();
				}
			}
		}
	}
	
	/*
	 * 更新用户位置信息
	 */
	public void updataLocation() {
		if (userManager == null || userManager.getCurrentUser() == null) {
			Log.e("error", "都为null");
			return;
		}
		if (!getLocationSuccess) return;
		getLocationSuccess = false;
		UserInfo userInfo = userManager.getCurrentUser(UserInfo.class);
		ToastHelper.show("let's go");
		BmobGeoPoint new_location = new BmobGeoPoint(lng, lat);
		userInfo.setLocation(new_location);
		userInfo.update(this, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ToastHelper.show("上传位置信息成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastHelper.show("上传位置信息失败");
			}
		});
		
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
}
