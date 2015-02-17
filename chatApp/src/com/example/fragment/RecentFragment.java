package com.example.fragment;

import com.example.base.BaseFragment;
import com.example.chatapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RecentFragment extends BaseFragment{
	private ListView mRecentList = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_recent, null);
		mRecentList = (ListView) view.findViewById(R.id.recent_list);
        
		return view;
	}
}
