package com.imudges.ldj.servse;

import com.imudges.zhw.javabean.BaseInfo;

import android.os.IBinder;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

/*������Service����ź�ǿ�Ȳ�������ʽ��38�н������ã�������String��*/
public class Signal extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
