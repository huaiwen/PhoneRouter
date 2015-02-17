package com.imudges.zhw.slee;

import com.imudges.zhw.util.EnvironmentUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomeCallBroadcastReceiver extends BroadcastReceiver {//�ӵ��绰�Ĵ���

	EnvironmentUtils env = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		env = new EnvironmentUtils(context);

		// ��ȡ��ǰ״̬
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

		// �����ǰ��������
		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			if (env.isRunning()) {
				if (env.isListening()) {
					String incomeNumber = intent
							.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					Log.i("MessageRouter",
							"IncomeCallBroadcastReceiver: Got a income call from:"
									+ incomeNumber);

					// ����������
					// String sms =
					// "���������ú�����Ϣ�Զ�ת�����ܣ����ĺ�����Ϣ���ᱻת�������󶨵�Ŀ���ֻ�:"+env.getTransmitPhoneNumber();
					// env.sendSms(incomeNumber, sms);

					// ���������ߵ�ת������
					// sms = "�����ֻ��ո��յ� +"+incomeNumber+" �����磬�ش����ѡ�";
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
