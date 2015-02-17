package com.example.chatapp;

import java.io.File;

import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;
import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;

public class MyApplication extends Application {
	private static MyApplication myApplication = null;
	private static BmobUserManager userManager = null;
	private String mAvaterDir = null;
	public static int screen_width = 0;
	public static int screen_height = 0;

	public final static String APP_KEY = "159888b60f02a078c5c7ac97cf1e9b89";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myApplication = this;

		// 获取屏幕宽高
		getWidthAndHeight();

		// 初始化Bomb
		initBomb();
		initImageLoader();
		ToastHelper.init(this);
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

	public void createAvaterDir(String userName) {
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

	public String getAvaterDir(String userName) {
		createAvaterDir(userName);
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
}
