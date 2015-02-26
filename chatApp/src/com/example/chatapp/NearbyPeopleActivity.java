package com.example.chatapp;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cn.bmob.im.task.BRequest;
import cn.bmob.v3.listener.FindListener;

import com.example.adapter.NearbyPeopleAdapter;
import com.example.base.BaseActivity;
import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.example.user.UserInfo;
import com.example.view.XListView;

public class NearbyPeopleActivity extends BaseActivity implements XListView.IXListViewListener, OnItemClickListener{
    private XListView mXListView = null;
    private List<UserInfo> mData = null;
    private NearbyPeopleAdapter mAdapter = null;
    private int mCurrentPage = 0;
    private boolean isRefresh = true;
    private UserInfo userInfo = null;
    
	@Override
	public void setHeadVisible() {
		// TODO Auto-generated method stub
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.activity_nearby_people, null);
		mXListView = (XListView) view.findViewById(R.id.near_people_list);
		initXList();
		refreshNearbyPeople();
		userInfo = mUserManager.getCurrentUser(UserInfo.class);
		return view;
	}

	@Override
	public void setTitle() {
		// TODO Auto-generated method stub
		title.setText(R.string.nearby_people);
	}

	@Override
	public void setBackBtnVisible() {
		// TODO Auto-generated method stub
		back_btn.setVisibility(View.VISIBLE);
	}
	
	public void initXList() {
		mXListView.setPullLoadEnable(false);
		mXListView.setPullRefreshEnable(false);
		mData = new ArrayList<UserInfo>();
		mAdapter = new NearbyPeopleAdapter(this, mData);
		mXListView.setAdapter(mAdapter);
		mXListView.setXListViewListener(this);
		mXListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		isRefresh = false;
		double lat = userInfo.getLocation().getLatitude();
		double lng = userInfo.getLocation().getLongitude();
		mUserManager.queryNearByListByPage(isRefresh, 0, "mLocation", lng, lat, true, null, null, new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("error", "查找失败");
				mXListView.stopLoadMore();
			}

			@Override
			public void onSuccess(List<UserInfo> arg0) {
				// TODO Auto-generated method stub
				mData.addAll(arg0);
				mAdapter.notifyDataSetChanged();
				if (arg0.size() <= BRequest.QUERY_LIMIT_COUNT) {
					ToastHelper.show("数据加载完毕");
					mXListView.setPullLoadEnable(false);
				}
				else {
					mXListView.setPullLoadEnable(true);
				}
				mPDHelper.dissmiss();
				mXListView.stopLoadMore();
			}
		});
	}
	
	public void refreshNearbyPeople() {
		mPDHelper.show("正在查找附近的人");
		isRefresh = true;
		double lat = MyApplication.getInstance().getLat();
		double lng = MyApplication.getInstance().getLng();
		mUserManager.queryNearByListByPage(isRefresh, 0, "mLocation", lng, lat, true, null, null, new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("error", arg1);
				mPDHelper.dissmiss();
				mXListView.stopLoadMore();
			}

			@Override
			public void onSuccess(List<UserInfo> arg0) {
				// TODO Auto-generated method stub
				mData.clear();
				mData.addAll(arg0);
				mAdapter.notifyDataSetChanged();
				if (mData.size() <= BRequest.QUERY_LIMIT_COUNT) {
					ToastHelper.show("数据加载完毕");
					mXListView.setPullLoadEnable(false);
				}
				else {
					mXListView.setPullLoadEnable(true);
				}
				mPDHelper.dissmiss();
				mXListView.stopLoadMore();
			}
		});
	}

}
