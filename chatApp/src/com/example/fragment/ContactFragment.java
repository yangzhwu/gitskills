package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.base.BaseFragment;
import com.example.chatapp.NearbyPeopleActivity;
import com.example.chatapp.NewFriend;
import com.example.chatapp.R;
import com.example.chatapp.SearchPeopleActivity;

public class ContactFragment extends BaseFragment implements OnClickListener{
	private RelativeLayout mSearchNearbyPeople = null;
	private RelativeLayout mSearchPeople = null;
	private RelativeLayout mMyFriends = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_contact, null);
		mSearchNearbyPeople = (RelativeLayout) view.findViewById(R.id.search_nearby_people);
		mSearchPeople = (RelativeLayout) view.findViewById(R.id.search_people);
		mMyFriends = (RelativeLayout) view.findViewById(R.id.my_friends);
		
		initListeners();
		return view;
	}
	
	public void initListeners() {
		mSearchNearbyPeople.setOnClickListener(this);
		mSearchPeople.setOnClickListener(this);
		mMyFriends.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.search_nearby_people: //附近的人
			Intent intent_nearby_people = new Intent(context, NearbyPeopleActivity.class);
			startActivity(intent_nearby_people);
			context.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			break;
		case R.id.search_people: //查找用户
			Intent intent = new Intent(context, SearchPeopleActivity.class);
			startActivity(intent);
			context.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			break;
		case R.id.my_friends: //我的好友
			Intent intent_friend = new Intent(context, NewFriend.class);
			startActivity(intent_friend);
			context.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
			break;
			default:
				break;
		}
	}
}
