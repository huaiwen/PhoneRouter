package com.imudges.zhw.action;

import com.imudges.zhw.util.EnvironmentUtils;

import android.content.Context;
import android.util.Log;

public class ListenSwitchAction extends AbstractAction {
	public static String aid = "com.imudges.messagerouter.action.ListenSwitchAction";
	public static String command = "listen";
	
	
	public ListenSwitchAction(EnvironmentUtils env, Context context,String[] params) {
		super(env, context,params);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
		try{
			
			if("on".equals(params[1])){//开启
				env.setListening(true);
				env.sendSms(env.getLastPhoneNumber(), "短信侦听已开启！");
				Log.i("MessageRouter", "ListenSwitchAction:Start listen");
				
			
			}else if("off".equals(params[1])){//关闭
				env.setListening(false);
				env.sendSms(env.getLastPhoneNumber(), "短信侦听已关闭！");
				Log.i("MessageRouter", "ListenSwitchAction:End listen");
			
			}
		}catch(IllegalArgumentException exception){
			env.sendSms(env.getLastPhoneNumber(), exception.getMessage());
			Log.i("MessageRouter", "Exception:"+exception.getMessage());
		}
	}

	
	
}
