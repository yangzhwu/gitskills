package com.example.chatapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.helper.ToastHelper;

import android.R.anim;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.config.BmobConstant;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.im.inteface.OnReceiveListener;
import cn.bmob.im.util.BmobJsonUtil;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.FindListener;

public class MyMessageReceiver extends BroadcastReceiver {

	public static ArrayList<EventListener> ehList = new ArrayList<EventListener>();
	
	public static final int NOTIFY_ID = 0x000;
	public static int mNewNum = 0;//
	BmobUserManager userManager;
	BmobChatUser currentUser;

	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String json = intent.getStringExtra("msg");
		ToastHelper.show("接收到来自服务器的消息" + json);
		currentUser = MyApplication.getUserManager().getCurrentUser();
		parseMessage(context, json);
	}

	private void parseMessage(final Context context, String json) {
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			String tag = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TAG);
			if(tag.equals(BmobConfig.TAG_OFFLINE)){
				if(currentUser!=null){
					if (ehList.size() > 0) {
						for (EventListener handler : ehList)
							handler.onOffline();
					}else{
//						CustomApplcation.getInstance().logout();
					}
				}
			}else{
				String fromId = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TARGETID);
				final String toId = BmobJsonUtil.getString(jo, BmobConstant.PUSH_KEY_TOID);
				String msgTime = BmobJsonUtil.getString(jo,BmobConstant.PUSH_READED_MSGTIME);
				if(tag.equals(BmobConfig.TAG_ADD_CONTACT)){
				    BmobInvitation message = BmobChatManager.getInstance(context).saveReceiveInvite(json, toId);
					if(currentUser!=null){
					    if(toId.equals(currentUser.getObjectId())){
					    	ToastHelper.show("有人添加你为好友");
	                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	                        Notification notification = new Notification(android.R.drawable.stat_notify_chat, "有人添加你为好友", System.currentTimeMillis());
	                        notification.flags = Notification.FLAG_AUTO_CANCEL;
	                        Intent intent = new Intent(context, NewFriend.class);
	                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	                        PendingIntent pendingIntent = PendingIntent.getActivity(context, R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	                        notification.setLatestEventInfo(context, context.getString(R.string.app_name), "有人添加你为好友", pendingIntent);
	                        notificationManager.notify(R.string.app_name, notification);
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}
