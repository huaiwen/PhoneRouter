package com.imudges.ldj.servse;

import com.imudges.zhw.javabean.BaseInfo;

import android.os.IBinder;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

/*启动该Service获得信号强度参数，格式第38行进行设置，保存在String中*/
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
