package com.imudges.zhw.slee;

import com.imudges.zhw.util.EnvironmentUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomeCallBroadcastReceiver extends BroadcastReceiver {//接到电话的处理

	EnvironmentUtils env = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		env = new EnvironmentUtils(context);

		// 获取当前状态
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

		// 如果当前正在响铃
		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			if (env.isRunning()) {
				if (env.isListening()) {
					String incomeNumber = intent
							.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					Log.i("MessageRouter",
							"IncomeCallBroadcastReceiver: Got a income call from:"
									+ incomeNumber);

					// 发给呼叫者
					// String sms =
					// "本机已设置呼叫信息自动转发功能，您的呼叫信息将会被转发至所绑定的目的手机:"+env.getTransmitPhoneNumber();
					// env.sendSms(incomeNumber, sms);

					// 发给被叫者的转发号码
					// sms = "您的手机刚刚收到 +"+incomeNumber+" 的来电，特此提醒。";
					// env.sendSms(env.getTransmitPhoneNumber(), sms);
				} else {
					Log.i("MessageRouter",
							"IncomeCallBroadcastReceiver: Listen switch is off");
				}
			} else {
				Log.i("MessageRouter",
						"IncomeCallBroadcastReceiver: Main switch is off");
			}
		}
	}
}
