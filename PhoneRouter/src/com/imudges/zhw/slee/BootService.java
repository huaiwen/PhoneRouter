package com.imudges.zhw.slee;

import com.imudges.zhw.util.PhoneStateMonitor;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class BootService extends Service {
	private SmsBroadcastReceiver localSmsBroadcastReceiver = null;
	private IncomeCallBroadcastReceiver localIncomeCallBroadcastReceiver = null;
	private BroadcastReceiver mBatInfoReceiver = new BatteryInfoBroadcastReceiver(); 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		  //�����ֻ��ź�
		  TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			tel.listen(new PhoneStateMonitor(getApplicationContext()),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
							| PhoneStateListener.LISTEN_SERVICE_STATE);
		//��������
		registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		// �������ż���
		IntentFilter localSmsIntentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		localSmsIntentFilter.setPriority(Integer.MAX_VALUE);
		this.localSmsBroadcastReceiver = new SmsBroadcastReceiver();
		registerReceiver(localSmsBroadcastReceiver, localSmsIntentFilter);
		// �����绰����
		IntentFilter localIncomeCallIntentFilter = new IntentFilter(
				"android.intent.action.PHONE_STATE");
		localIncomeCallIntentFilter.setPriority(Integer.MAX_VALUE);
		this.localIncomeCallBroadcastReceiver = new IncomeCallBroadcastReceiver();
		registerReceiver(localIncomeCallBroadcastReceiver,
				localIncomeCallIntentFilter);
	}

}
