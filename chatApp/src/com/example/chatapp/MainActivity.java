package com.example.chatapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base.BaseActivity;
import com.example.fragment.ContactFragment;
import com.example.fragment.RecentFragment;
import com.example.fragment.SettingFragment;
import com.example.helper.ToastHelper;

public class MainActivity extends BaseActivity implements OnClickListener{
	//三个标题字符串
    private int[] titleString = {R.string.recend_contact, R.string.contacts, R.string.setting};
    //当前显示的fragment的下标
    private int mIndex = 0;
    /*
     *底部三个tab按钮 
     */
    private RadioButton recent_btn = null;
    private RadioButton contact_btn = null;
    private RadioButton setting_btn = null;
    /*
     * 三个fragment
     */
    private RecentFragment recentFragment = null;
    private ContactFragment contactFragment = null;
    private SettingFragment settingFragment = null;
    /*
     * 三个fragment的下标
     */
    private final static int RECENT_FRAGEMENT = 0;
    private final static int CONTACT_FRAGMENT = 1;
    private final static int SETTING_FRAGMENT = 2;
    
    /*
     * 设置头部是否可见
     */
	@Override
	public void setHeadVisible() {
		// TODO Auto-generated method stub
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
		recent_btn = (RadioButton) view.findViewById(R.id.recent_btn);
		contact_btn = (RadioButton) view.findViewById(R.id.contact_btn);
		setting_btn = (RadioButton) view.findViewById(R.id.setting_btn);
		
		recent_btn.setChecked(true);
		recent_btn.setOnClickListener(this);
		contact_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
		changeContent();
		return view;
	}
    
	/*
	 * 设置头部标题
	 */
	@Override
	public void setTitle() {
		// TODO Auto-generated method stub
		title.setText(titleString[mIndex]);
	}
    
	/*
	 *设置头部返回按钮是否可见
	 */
	@Override
	public void setBackBtnVisible() {
		// TODO Auto-generated method stub
		back_btn.setVisibility(View.GONE);
	}
	
	/*
	 * 按钮点击事件
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.recent_btn:
			if (mIndex != 0) {
				mIndex = 0;
				recent_btn.setChecked(true);
				setTitle();
				changeContent();
			}
			break;
		case R.id.contact_btn:
			if (mIndex != 1) {
				mIndex = 1;
				contact_btn.setChecked(true);
				setTitle();
				changeContent();
			}
			break;
		case R.id.setting_btn:
			if (mIndex != 2) 
				mIndex = 2;
				setting_btn.setChecked(true);
				setTitle();
				changeContent();
			break;
			default:
				break;
		}
		
	}
	
	/*
	 * 切换显示fragment
	 */
	public void changeContent() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTrasaction = fragmentManager.beginTransaction();
		switch(mIndex) {
		case RECENT_FRAGEMENT:
			if (recentFragment == null) {
				recentFragment = new RecentFragment();
			}
			fragmentTrasaction.replace(R.id.container, recentFragment);
			break;
		case CONTACT_FRAGMENT:
			if (contactFragment == null) {
				contactFragment = new ContactFragment();
			}
			fragmentTrasaction.replace(R.id.container, contactFragment);
			break;
		case SETTING_FRAGMENT:
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
			}
			fragmentTrasaction.replace(R.id.container, settingFragment);
			break;
			default:
				break;
		}
		fragmentTrasaction.commit();
	}

}
