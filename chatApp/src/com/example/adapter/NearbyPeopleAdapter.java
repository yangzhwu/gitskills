package com.example.adapter;

import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.example.chatapp.R;
import com.example.image.ImageLoadOptions;
import com.example.user.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class NearbyPeopleAdapter extends BaseAdapter{
    private List<UserInfo> mData = null;
    private Context mContext = null;
    
    public NearbyPeopleAdapter(Context context, List<UserInfo> data) {
    	mContext = context;
    	mData = data;
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		UserInfo userInfo = mData.get(position);
		Holder holder = null;
		if (convertView == null) {
		    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nearby_people, null);
		    holder= new Holder();
		    holder.avater = (ImageView) convertView.findViewById(R.id.avater);
		    convertView.setTag(holder);
		}
		else {
			holder = (Holder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(userInfo.getAvatar(), holder.avater, ImageLoadOptions.getOptions());
		return convertView;
	}
		
	private class Holder {
		public ImageView avater = null;
	}

}
