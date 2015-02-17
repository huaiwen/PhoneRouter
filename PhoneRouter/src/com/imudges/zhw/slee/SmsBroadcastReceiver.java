package com.imudges.zhw.slee;

import com.imudges.zhw.action.AbstractAction;
import com.imudges.zhw.action.DefaultAction;
import com.imudges.zhw.util.EnvironmentUtils;
import com.imudges.zhw.util.SmsAnalyzer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsBroadcastReceiver extends BroadcastReceiver {//接到短信的处理
	private EnvironmentUtils env;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.env = new EnvironmentUtils(context);//看看设置
		Log.i("MessageRouter", "SmsBroadcastReceiver:get a message!");
		if (env.isRunning()) {
			SmsAnalyzer sa = new SmsAnalyzer(env, context, intent);
			AbstractAction action = sa.getSmsAction();
			action.doAction();

			// 如果不是默认短信的示例，则说明内容为指令，则让让用户收不到
			if (!(action instanceof DefaultAction)) {
				this.abortBroadcast();
			}
		} else {
			Log.i("MessageRouter", "SmsBroadcastReceiver:Main switch is off");
		}

	}

}
