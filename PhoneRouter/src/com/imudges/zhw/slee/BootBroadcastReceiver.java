package com.imudges.zhw.slee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * ����������BroadcastReceiver
 * �������ڿ���ʱ�����ܿ����Ƿ�򿪣�������һ��Notification
 * */
public class BootBroadcastReceiver extends BroadcastReceiver {
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//�����Զ���������
		Log.i("PhoneRouter", "BootBroadcastReceiver: System boot");
		Intent service = new Intent(context, BootService.class);
		context.startService(service);
	}
}
