package com.imudges.zhw.action;

import com.imudges.zhw.util.EnvironmentUtils;

import android.content.Context;
import android.util.Log;

public class DefaultAction extends AbstractAction {

	public DefaultAction(EnvironmentUtils env, Context context,
			String[] params) {
		super(env, context, params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
		//���û���������أ���ת������
		if(!env.isListening()){
			Log.i("MessageRouter", "DefaultAction: Listen Switch is off");
			return;
		}
		//ת����Ϣ
	//	String sms = "���ֻ��ո��յ���һ����["+env.getLastPhoneNumber()+"]���͵Ķ��ţ�\n"+params[0];
	//	env.sendSms(env.getTransmitPhoneNumber(), sms);
		
		//��ԭ���߻ظ�
	//	sms = "�����û������˶���ת�����ܣ��������͵Ķ��Ž����Զ�ת������ "+env.getTransmitPhoneNumber();
	//	env.sendSms(env.getLastPhoneNumber(), sms);
		
		Log.i("MessageRouter", "DefaultAction: Send:"+params[0]+" to:"+env.getTransmitPhoneNumber());
	}


}
