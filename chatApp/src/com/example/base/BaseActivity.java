package com.example.base;

import cn.bmob.im.BmobUserManager;

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
	private RelativeLayout header = null;
	private RelativeLayout back_btn = null;
	private TextView title = null;
	private BmobUserManager mUserManager = null;
	private FrameLayout container = null;
	protected PDHelper mPDHelper = null;
    @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	setContentView(R.layout.activity_base);
    	
    	mUserManager = BmobUserManager.getInstance(this);
    	header = (RelativeLayout) this.findViewById(R.id.head);
    	setHeadVisible(header);
    	back_btn = (RelativeLayout) this.findViewById(R.id.back_btn);
    	if (header.getVisibility() == View.VISIBLE) {
    		back_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
				}
    			
    		});
    	}
    	container = (FrameLayout) this.findViewById(R.id.content);
    	View view = getContentView();
    	container.addView(view);
    	mPDHelper = new PDHelper(this);
    }
    
    /*
     * 抽象方法，设置头部布局是否可见
     */
    public abstract void setHeadVisible(RelativeLayout header);
    
    public abstract View getContentView();
}
