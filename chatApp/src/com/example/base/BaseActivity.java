package com.example.base;

import cn.bmob.im.BmobUserManager;

import com.example.chatapp.MyApplication;
import com.example.chatapp.R;
import com.example.helper.PDHelper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends FragmentActivity{
	protected RelativeLayout header = null;
	protected RelativeLayout back_btn = null;
	protected TextView title = null;
	protected BmobUserManager mUserManager = null;
	private FrameLayout container = null;
	protected PDHelper mPDHelper = null;
	
    @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	setContentView(R.layout.activity_base);
    	
    	mUserManager = MyApplication.getUserManager();
    	mPDHelper = new PDHelper(this);
    	header = (RelativeLayout) this.findViewById(R.id.head);
    	setHeadVisible();
    	back_btn = (RelativeLayout) this.findViewById(R.id.back_btn);
    	setBackBtnVisible();
    	title = (TextView) this.findViewById(R.id.title);
    	if (header.getVisibility() == View.VISIBLE) {
    		setTitle();
    		if (back_btn.getVisibility() == View.VISIBLE) {
    		    back_btn.setOnClickListener(new OnClickListener() {

				    @Override
				    public void onClick(View v) {
					    // TODO Auto-generated method stub
					    finish();
					    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
				    }
    			
    		    });
    		}
    	}
    	container = (FrameLayout) this.findViewById(R.id.content);
    	View view = getContentView();
    	container.addView(view);
    }
    
    /*
     * 抽象方法，设置头部布局是否可见
     */
    public abstract void setHeadVisible();
    
    /*
     * 设置显示的内容
     */
    public abstract View getContentView();
    
    /*
     * 设置头部的标题
     */
    public abstract void setTitle();
    
    /*
     * 设置返回按钮是否可见
     */
    public abstract void setBackBtnVisible();
    
    @Override
    public void onBackPressed() {
    	finish();
    	overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
