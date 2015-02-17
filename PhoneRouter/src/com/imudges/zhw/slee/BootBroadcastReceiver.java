package com.imudges.zhw.slee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * 开机自启动BroadcastReceiver
 * 仅用来在开机时根据总开关是否打开，而产生一个Notification
 * */
public class BootBroadcastReceiver extends BroadcastReceiver {
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//开机自动启动服务
		Log.i("PhoneRouter", "BootBroadcastReceiver: System boot");
		Intent service = new Intent(context, BootService.class);
		context.startService(service);
	}
}
