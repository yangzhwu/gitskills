package com.example.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    private static Context mContext = null;
    
    public static void init(Context context) {
    	mContext = context;
    }
    
    public static void show(String message) {
    	Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
    
    public static void show(int resId) {
    	Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }
}
