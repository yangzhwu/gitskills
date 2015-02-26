package com.example.chatapp;

import cn.bmob.v3.listener.SaveListener;

import com.example.base.BaseActivity;
import com.example.constant.BroadcastConstant;
import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.example.user.UserInfo;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private EditText mPhoneNumber = null;
    private EditText mPassWord = null;
    private EditText mConfirmPassWord = null;
    private Button mRegister = null;
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register:
			register();
			break;
			default:
			    break;
		}
	}

	@Override
	public void setHeadVisible() {
		// TODO Auto-generated method stub
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.activity_register, null);
		mPhoneNumber = (EditText) view.findViewById(R.id.phone_number);
		mPassWord = (EditText) view.findViewById(R.id.pass_word);
		mConfirmPassWord = (EditText) view.findViewById(R.id.confirm_pass_word);
		mRegister = (Button) view.findViewById(R.id.register);
		mRegister.setOnClickListener(this);
		return view;
	}
	
	/*
	 * 注册函数
	 */
	public void register() {
		String phoneNumber = mPhoneNumber.getText().toString();
		String passWord = mPhoneNumber.getText().toString();
		String confirmPassWord = mConfirmPassWord.getText().toString();
		
		if (TextUtils.isEmpty(phoneNumber)) {
			ToastHelper.show(R.string.empty_phone_number);
			return;
		}
		if (TextUtils.isEmpty(passWord)) {
			ToastHelper.show(R.string.empty_pass_word);
		}
		if (!passWord.equals(passWord)) {
			ToastHelper.show(R.string.not_same_pass_word);
			return;
		}
		mPDHelper.show(R.string.registing);
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(phoneNumber);
		userInfo.setPassword(passWord);
		userInfo.signUp(this, new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show(R.string.register_failed);
				ToastHelper.show(arg1);
			}

			@Override
			public void onSuccess() {
				//注册成功后发送注册成功广播，关闭登陆界面
				//关闭注册界面
				//上传位置信息
				//跳转到主界面
				Intent register_success = new Intent(BroadcastConstant.REGISTER_SUCCESS);
				sendBroadcast(register_success);
				mPDHelper.dissmiss();
				Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
	}
    
	/*
	 * 设置标题
	 */
	@Override
	public void setTitle() {
		// TODO Auto-generated method stub
		title.setText(this.getString(R.string.register_text));
	}
	
	/*
	 * 设置返回按钮是否可见
	 */
	@Override
	public void setBackBtnVisible() {
		back_btn.setVisibility(View.VISIBLE);
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}

}
