package com.example.helper;

import com.example.chatapp.MyApplication;
import com.example.chatapp.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class DialogHelper {
    
	/*
	 * 上传头像dialog
	 */
	public static Dialog creatUpLoadAvaterDialog(Context context, final DialogInterface.OnClickListener listener) {
		final Dialog dialog = new Dialog(context, R.style.my_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.upload_avater_dialog, null);
		
		/*
		 * 取消
		 */
		TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				
			}
		});
		
		/*
		 * 拍摄照片
		 */
		TextView choose_by_take_photo = (TextView) view.findViewById(R.id.take_photo);
		choose_by_take_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick(dialog, 0);
				dialog.dismiss();
			}
		});
		
		/*
		 * 从相册选取
		 */
		TextView choose_from_photo_album = (TextView) view.findViewById(R.id.take_from_album);
		choose_from_photo_album.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick(dialog, 1);
				dialog.dismiss();
			}
		});
		
		dialog.setContentView(view);
		setDialogWidth(dialog);		
		return dialog;
	}
	
	/*
	 * 有一个输入框和两个按钮的dialog，如输入昵称的dialog
	 */
	public static Dialog createOneEditTextAndTwoButton(Context context, final TextView textView) {
		final Dialog dialog = new Dialog(context, R.style.my_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.one_edit_two_button, null);
		final EditText nick = (EditText) view.findViewById(R.id.nick);
		TextView confirm = (TextView) view.findViewById(R.id.confirm);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.confirm:
					String nickString = nick.getText().toString();
					if (TextUtils.isEmpty(nickString)) {
						ToastHelper.show("昵称不能为空");
					}
					else {
						textView.setText(nickString);
						dialog.dismiss();
					}
					break;
				case R.id.cancel:
					dialog.dismiss();
					break;
				default:
					break;
				}
				
			}
		};
		confirm.setOnClickListener(listener);
		cancel.setOnClickListener(listener);
		
		dialog.setContentView(view);
		setDialogWidth(dialog);
		return dialog;
	}
	
	/*
	 * 设置dialog的宽度，使所有dialog的宽度相同
	 */
	public static void setDialogWidth(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = MyApplication.screen_width * 4 / 5;
		dialog.getWindow().setAttributes(lp);
	}
}
