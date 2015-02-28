package com.example.chatapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.task.BRequest;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.example.adapter.SearchPeopleAdapter;
import com.example.base.BaseActivity;
import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.example.user.UserInfo;
import com.example.view.XListView;
import com.example.view.XListView.IXListViewListener;

public class SearchPeopleActivity extends BaseActivity implements OnClickListener, IXListViewListener, OnItemClickListener {
    private Button mBtnSearch = null;
    private EditText mSearchPhoneNumber = null;
    private XListView mXListView = null;
    private SearchPeopleAdapter mAdapter = null;
    private List<UserInfo> mData = null;
    private int mCurrentPage = 0;
    private String mUserPhone = null;
	@Override
	public void setHeadVisible() {
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		View view = LayoutInflater.from(this).inflate(R.layout.activity_search_people, null);
		mBtnSearch = (Button) view.findViewById(R.id.btn_search);
		mSearchPhoneNumber = (EditText) view.findViewById(R.id.search_phone_number);
		mXListView = (XListView) view.findViewById(R.id.xlistview);
		mBtnSearch.setOnClickListener(this);
		initXListView();
		return view;
	}

	@Override
	public void setTitle() {
		title.setText(R.string.find_people);
	}

	@Override
	public void setBackBtnVisible() {
		back_btn.setVisibility(View.VISIBLE);
	}
	
	/*
	 * 初始化XListView
	 */
	public void initXListView() {
		mXListView.setPullRefreshEnable(false);
		mXListView.setPullLoadEnable(false);
		mData = new ArrayList<UserInfo>();
	    mAdapter = new SearchPeopleAdapter(this, mData);
	    mXListView.setAdapter(mAdapter);
	    mXListView.setXListViewListener(this);
	    mXListView.setOnItemClickListener(this);
	}
    
	/*
	 * 查找按钮点击事件
	 */
	@Override
	public void onClick(View v) {
		mCurrentPage = 0;
		if (v.getId() == R.id.btn_search) {
			mUserPhone = mSearchPhoneNumber.getText().toString();
			if (TextUtils.isEmpty(mUserPhone)) {
				ToastHelper.show(R.string.empty_phone_number);
				return;
			}
			mPDHelper.show(R.string.searching_friends);
			mUserManager.queryUserByPage(false, 0, mUserPhone, new FindListener<UserInfo>() {
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ToastHelper.show(R.string.data_wrong);
					mPDHelper.dissmiss();
				}
				@Override
				public void onSuccess(List<UserInfo> arg0) {
					// TODO Auto-generated method stub
					if (arg0.size() == 0) {
						ToastHelper.show(R.string.no_condition_of_user);
					}
					mData.clear();
					mData.addAll(arg0);
					mAdapter.notifyDataSetChanged();
					mPDHelper.dissmiss();
					if (arg0.size() == BRequest.QUERY_LIMIT_COUNT) {
						mXListView.setPullLoadEnable(true);
					}
					else {
						ToastHelper.show(R.string.complete_load_data);
					}
				}
			});
		}
	}
	
	/*
	 * 查询匹配的用户
	 */
	public void queryMorePeople() {
		mUserManager.queryUserByPage(false, mCurrentPage, mUserPhone, new FindListener<UserInfo>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastHelper.show(R.string.data_wrong);
				mPDHelper.dissmiss();
			}
			@Override
			public void onSuccess(List<UserInfo> arg0) {
				mData.addAll(arg0);
				mAdapter.notifyDataSetChanged();
				mPDHelper.dissmiss();
				mXListView.stopLoadMore();
				if (arg0.size() < BRequest.QUERY_LIMIT_COUNT) {
					ToastHelper.show(R.string.complete_load_data);
					mXListView.setPullLoadEnable(false);
				}
			}
		});
	}
    
	/*
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		
	}
    
	/*
	 * 加载更多
	 */
	@Override
	public void onLoadMore() {
		mUserManager.querySearchTotalCount(mUserPhone, new CountListener() {
			
			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 > mData.size()) {
					mCurrentPage++;
					queryMorePeople();
				}
				else {
					ToastHelper.show(R.string.complete_load_data);
					mXListView.stopLoadMore();
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastHelper.show(R.string.data_wrong);
				mXListView.stopLoadMore();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mPDHelper.dissmiss();
		super.onDestroy();
	}
	
    /*
     * item点击事件
     */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, PersonalInfoActivity.class);
		Bundle bundle = new Bundle();
		//因为listView多加了头布局，所以减1
		bundle.putString("objectId", mData.get(arg2 - 1).getObjectId());
		bundle.putString("userName", mData.get(arg2 - 1).getUsername());
		intent.putExtras(bundle);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}

}
