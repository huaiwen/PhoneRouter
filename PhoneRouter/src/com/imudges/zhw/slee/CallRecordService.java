package com.imudges.zhw.slee;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallRecordService extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getApplicationContext(), "录音服务已经创建!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "录音服务已经销毁!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(getApplicationContext(), "录音服务已经启动!", Toast.LENGTH_LONG).show();
		//
		TelephonyManager telephonymanager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonymanager.listen(new PhoneListener(getApplicationContext()), PhoneStateListener.LISTEN_CALL_STATE);
		
	}

}
