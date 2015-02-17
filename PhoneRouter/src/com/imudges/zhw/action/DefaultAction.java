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
		
		//如果没打开侦听开关，则不转发短信
		if(!env.isListening()){
			Log.i("MessageRouter", "DefaultAction: Listen Switch is off");
			return;
		}
		//转发消息
	//	String sms = "本手机刚刚收到了一条由["+env.getLastPhoneNumber()+"]发送的短信：\n"+params[0];
	//	env.sendSms(env.getTransmitPhoneNumber(), sms);
		
		//给原发者回复
	//	sms = "本机用户开启了短信转发功能，您所发送的短信将会自动转发至： "+env.getTransmitPhoneNumber();
	//	env.sendSms(env.getLastPhoneNumber(), sms);
		
		Log.i("MessageRouter", "DefaultAction: Send:"+params[0]+" to:"+env.getTransmitPhoneNumber());
	}


}
