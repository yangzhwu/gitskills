package com.example.adapter;

import java.util.List;

import com.example.chatapp.R;
import com.example.user.UserInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchPeopleAdapter extends BaseAdapter{
    private Context mContext = null;
    private List<UserInfo> mData = null;
    
    public SearchPeopleAdapter(Context context, List<UserInfo> data) {
    	mContext = context;
    	mData = data;
    }
    
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		UserInfo userInfo = mData.get(position);
		if (convertView == null) {
		    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_people, null);
		    holder = new Holder();
		    holder.userName = (TextView) convertView.findViewById(R.id.user_name);
		    convertView.setTag(holder);
		}
		else {
			holder = (Holder) convertView.getTag();
		}
		holder.userName.setText(userInfo.getUsername());
		return convertView;
	}
	
	private class Holder {
		public TextView userName;
	}

}
