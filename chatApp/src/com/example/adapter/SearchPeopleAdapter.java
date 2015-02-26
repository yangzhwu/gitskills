package com.example.adapter;

import java.util.List;

import com.example.chatapp.R;
import com.example.image.ImageLoadOptions;
import com.example.user.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
		    AbsListView.LayoutParams lParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 240);
		    convertView.setLayoutParams(lParams);
		    holder = new Holder();
		    holder.accountNumber = (TextView) convertView.findViewById(R.id.account_number);
		    holder.nick = (TextView) convertView.findViewById(R.id.nick);
		    holder.sex = (TextView) convertView.findViewById(R.id.sex);
		    holder.searchAvater = (ImageView) convertView.findViewById(R.id.search_avater);
		    convertView.setTag(holder);
		}
		else {
			holder = (Holder) convertView.getTag();
		}
		holder.accountNumber.setText(userInfo.getUsername());
		holder.nick.setText(userInfo.getNick());
		holder.sex.setText(userInfo.getSex());
		
		ImageLoader.getInstance().displayImage(userInfo.getAvatar(), holder.searchAvater, ImageLoadOptions.getOptions());
		return convertView;
	}
	
	private class Holder {
		public TextView accountNumber;
		public TextView nick;
		public TextView sex;
		public ImageView searchAvater;
	}

}
