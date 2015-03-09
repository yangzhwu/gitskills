package com.example.chatapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.base.BaseActivity;
import com.example.helper.DialogHelper;
import com.example.helper.PDHelper;
import com.example.helper.ToastHelper;
import com.example.image.ImageLoadOptions;
import com.example.user.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserManager;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.MediaStore;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout mAvater_layout = null;
	private RelativeLayout mNick_layout = null;
	private RelativeLayout mSex_layout = null;
	private ImageView mImgAvater = null;
	private TextView mNickname = null;
	private TextView mSex = null;
	private Button mAddFriend = null;
	// 拍照
	private static final int CHOOSE_BY_TAKE_PHOTO = 0;
	// 从相册选取
	private static final int CHOOSE_FROM_PHOTO_ALBUM = 1;
	//拍照后再次剪切图片
	private static final int CROP_CHOOSE_BY_TAKE_PHOTO = 2;
	//头像存储路径
    private File path = null;
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    //是否查看的自己的信息，自己的为false，其他的为true
    private boolean isOtherUser = false;

	@Override
	public void setHeadVisible() {
		header.setVisibility(View.VISIBLE);
	}

	@Override
	public View getContentView() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.activity_personal_info, null);
		mAvater_layout = (RelativeLayout) view.findViewById(R.id.avater_layout);
		mNick_layout = (RelativeLayout) view.findViewById(R.id.nick_layout);
		mSex_layout = (RelativeLayout) view.findViewById(R.id.sex_layout);
		mImgAvater = (ImageView) view.findViewById(R.id.img_avater);
		mNickname = (TextView) view.findViewById(R.id.nick_name);
		mSex = (TextView) view.findViewById(R.id.sex);
		mAddFriend = (Button) view.findViewById(R.id.add_friend);
        if (!isOtherUser) {
		    mAvater_layout.setOnClickListener(this);
		    mNick_layout.setOnClickListener(this);
		    mSex_layout.setOnClickListener(this);
        }
        else {
        	view.findViewById(R.id.img_arrows_avater).setVisibility(View.INVISIBLE);
        	view.findViewById(R.id.img_arrows_nick).setVisibility(View.INVISIBLE);
        	view.findViewById(R.id.img_arrows_sex).setVisibility(View.INVISIBLE);
        	mAddFriend.setVisibility(View.VISIBLE);
        	mAddFriend.setOnClickListener(this);
        }
		initUserInfo();
		return view;
	}

	@Override
	public void setTitle() {
		if (this.getIntent().getExtras() == null) {
		    title.setText(R.string.personal_infomation);
		    isOtherUser = false;
		}
		else {
			isOtherUser = true;
			title.setText(R.string.other_user_information);
		}
	}

	@Override
	public void setBackBtnVisible() {
		back_btn.setVisibility(View.VISIBLE);
	}
	
	/*
	 * 根据用户名加载用户信息，包括头像，昵称，性别等
	 */
	public void initUserInfo() {
		if (!isOtherUser) {
		    UserInfo userInfo = mUserManager.getCurrentUser(UserInfo.class);
		    refreshAvater(userInfo.getAvatar());
		    mNickname.setText(userInfo.getNick());
		    mSex.setText(userInfo.getSex());
		}
		else {
			String userName = this.getIntent().getExtras().getString("userName");
			mUserManager.queryUser(userName, new FindListener<UserInfo>() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ToastHelper.show("数据出错");
				}

				@Override
				public void onSuccess(List<UserInfo> arg0) {
					// TODO Auto-generated method stub
					ToastHelper.show(arg0.size() + " ");
					UserInfo userInfo = arg0.get(0);
					refreshAvater(userInfo.getAvatar());
					mNickname.setText(userInfo.getNick());
					mSex.setText(userInfo.getSex());
				}
				
			});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.avater_layout:  //头像
			DialogInterface.OnClickListener avater_listener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case CHOOSE_BY_TAKE_PHOTO: //拍照获取头像
						takePhoto();
						break;
					case CHOOSE_FROM_PHOTO_ALBUM: //从相册获取
						photoAlbum();
						break;
					default:
						break;
					}
				}
			};
			Dialog avater_dialog = DialogHelper.creatUpLoadAvaterDialog(this, avater_listener);
			avater_dialog.show();
			break;
		case R.id.nick_layout: //昵称
			final StringBuilder sb = new StringBuilder();
			DialogInterface.OnClickListener nick_listener = new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					updataNick(sb);					
				}
			};
			Dialog nick_dialog = DialogHelper.createOneEditTextAndTwoButton(this, mNickname, nick_listener, sb);
			nick_dialog.show();
			break;
		case R.id.sex_layout: //性别
            DialogInterface.OnClickListener sex_listener = new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case MALE:
						if (!mSex.getText().toString().equals("男")) {
							updataSex("男");
						}
						break;
					case FEMALE:
						if (!mSex.getText().toString().equals("女")) {
							updataSex("女");
						}
						break;
					default:
						break;
					}					
				}
			};
			Dialog sex_dialog = DialogHelper.createChooseSexDialog(this, sex_listener);
			sex_dialog.show();
			break;
		case R.id.add_friend:
			//添加对方为好友
			addFriend();
			break;
		default:
			break;
		}
	}
	
	public void addFriend() {
		mPDHelper.show("正在发送请求");
		String objectId = this.getIntent().getExtras().getString("objectId");
		BmobChatManager.getInstance(this).sendTagMessage(BmobConfig.TAG_ADD_CONTACT,
				objectId, new PushListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						mPDHelper.dissmiss();
						ToastHelper.show("发送请求成功，等待对方验证！");
					}

					@Override
					public void onFailure(int arg0, final String arg1) {
						// TODO Auto-generated method stub
						mPDHelper.dissmiss();
						ToastHelper.show("发送请求失败");
					}
				});
	}

	/*
	 * 通过拍照上传头像
	 */
	public void takePhoto() {
		/*
		 * 打开相机，拍摄照片 剪切照片 将剪切好的照片放入sdCard中 将照片上传到服务器 将照片保存在ImageView中
		 */
		if (path == null) {
		    String dir = MyApplication.getInstance().getUserDir(MyApplication.getInstance().getUserManager().getCurrentUserName());
            path = new File(dir, "avater.png");
		}
        if (path.exists()) {
        	path.delete();
        }
        Log.e("path", path.getAbsolutePath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(path));
        startActivityForResult(intent, CHOOSE_BY_TAKE_PHOTO);
	}

	/*
	 * 通过相册选取上传头像
	 */
	public void photoAlbum() {
		/*
		 * 打开相册，选择照片 剪切照片 将剪切好的照片放入sdCard中 将照片上传到服务器 将照片保存在ImageView中
		 */
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, CHOOSE_FROM_PHOTO_ALBUM);
	}
	
	/*
	 * 剪切通过拍照返回的图片
	 */
	private void cropPhoto(File path) {		
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(Uri.fromFile(path), "image/*");  
        intent.putExtra("crop", "true");  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        intent.putExtra("outputX", 100);  
        intent.putExtra("outputY", 100);  
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_CHOOSE_BY_TAKE_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case CHOOSE_FROM_PHOTO_ALBUM://选择照片后返回
			if (data == null) break;
			Bitmap photo = data.getParcelableExtra("data");
			if (photo == null) {
				Log.e("error", "null");
				return;
			}
			saveAvaterToLocal(photo);
			upLoadAvater();
			break;
		case CHOOSE_BY_TAKE_PHOTO: //从拍照返回
			cropPhoto(path);
			break;
		case CROP_CHOOSE_BY_TAKE_PHOTO: //拍照图片剪切完成后返回
			upLoadAvater();
		default:
			break;
		}
	}
    
	/*
	 * 将头像保存在本地
	 */
	private void saveAvaterToLocal(Bitmap photo) {
        String dir = MyApplication.getInstance().getUserDir(MyApplication.getInstance().getUserManager().getCurrentUserName());
        path = new File(dir, "avater.png");
        if (path.exists()) {
        	path.delete();
        }
        FileOutputStream outputStream = null;
        try {
			if (path.createNewFile()) {
				outputStream = new FileOutputStream(path);
				if (photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
					outputStream.flush();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 上传头像到服务器
	 */
	private void upLoadAvater() {
		mPDHelper.show("正在处理");
		if (path == null) return;
		final BmobFile bmobFile = new BmobFile(path);
		bmobFile.upload(this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				String url = bmobFile.getFileUrl(PersonalInfoActivity.this);
				updataUser(url);
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show("上传头像失败 " + arg1);
			}
		});
	}
	
	/*
	 * 更新数据表中头像的URL
	 */
	private void updataUser(final String url) {
		UserInfo userInfo = mUserManager.getCurrentUser(UserInfo.class);
		userInfo.setAvatar(url);
		userInfo.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ToastHelper.show("头像更新成功");
				refreshAvater(url);
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show("头像更新失败");
			}
		});
	}
	
	/*
	 * 更新头像
	 */
	private void refreshAvater(final String url) {
		if (url != null && !TextUtils.isEmpty(url)) {
		    ImageLoader.getInstance().displayImage(url, mImgAvater, ImageLoadOptions.getOptions());
		    mPDHelper.dissmiss();
		}
	}
	
	/*
	 * 性别修改上传到服务器
	 */
	private void updataSex(final String sex) {
		mPDHelper.show("正在处理");
		UserInfo userInfo = mUserManager.getCurrentUser(UserInfo.class);
		userInfo.setSex(sex);
		userInfo.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show("性别修改成功");
				mSex.setText(sex);
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show("性别修改失败");
			}
		});
	}
	
	/*
	 * 昵称修改上传到服务器
	 */
	private void updataNick(final StringBuilder sb) {
		mPDHelper.show("正在处理");
		UserInfo userInfo = mUserManager.getCurrentUser(UserInfo.class);
		String s = sb.toString();
		userInfo.setNick(sb.toString());
		userInfo.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();
				ToastHelper.show("昵称修改成功");
				mNickname.setText(sb.toString());
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				mPDHelper.dissmiss();  
				ToastHelper.show("昵称修改失败");
			}
		});
	}
	
	
}
