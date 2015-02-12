package com.example.helper;

import com.example.chatapp.R;

import android.app.ProgressDialog;
import android.content.Context;

public class PDHelper {
    private ProgressDialog progressDialog = null;
    private Context mContext = null;
    
    public PDHelper(Context context) {
    	mContext = context;
    	progressDialog = new ProgressDialog(mContext);
    }
    
    public  void show(String message) {
    	progressDialog.setTitle(mContext.getString(R.string.please_wait));
    	progressDialog.setMessage(message);
    	progressDialog.show();
    }
    
    public void show(int resId) {
    	progressDialog.setTitle(mContext.getString(R.string.please_wait));
    	progressDialog.setMessage(mContext.getString(resId));
    	progressDialog.show();
    }
    
    public void dissmiss() {
    	if (progressDialog != null && progressDialog.isShowing()) {
    		progressDialog.dismiss();
    	}
    }
    
}
