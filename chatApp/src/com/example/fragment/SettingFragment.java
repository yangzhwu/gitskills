package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.base.BaseFragment;
import com.example.chatapp.PersonalInfoActivity;
import com.example.chatapp.R;

public class SettingFragment extends BaseFragment implements OnClickListener{
	private RelativeLayout mPersonalInfo = null;
	private Button mLogout = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_setting, null);
    	mPersonalInfo = (RelativeLayout) view.findViewById(R.id.personal_info_layout);
    	mLogout = (Button) view.findViewById(R.id.logout);
    	
    	mPersonalInfo.setOnClickListener(this);
    	mLogout.setOnClickListener(this);
    	return view;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.personal_info_layout:
			Intent intent = new Intent(context, PersonalInfoActivity.class);
			startActivity(intent);
			context.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			break;
		case R.id.logout:
			break;
			default:
				break;
		}
	}
	
}
