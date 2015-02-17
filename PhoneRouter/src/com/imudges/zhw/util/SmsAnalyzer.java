package com.imudges.zhw.util;

import java.util.Date;
import java.util.Locale;

import com.imudges.zhw.action.AbstractAction;
import com.imudges.zhw.action.DefaultAction;
import com.imudges.zhw.action.ListenSwitchAction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsAnalyzer {
	private String smsSender = "";
	private String smsContent = "";
	private Date smsDate = null;
	private AbstractAction smsAction= null;
	private Context context = null;
	private EnvironmentUtils env = null;
	
	public SmsAnalyzer(EnvironmentUtils env, Context context,Intent intent){
		Log.i("MessageRouter", "SmsAnalyzer:Initialization");
		this.env = env;
		this.context = context;
		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[])bundle.get("pdus");
		
		if(pdus!=null&&pdus.length>0){
			SmsMessage[] smsMessages = new SmsMessage[pdus.length]; 
			for(int i=0;i<smsMessages.length;i++){
				 byte[] pdu = (byte[]) pdus[i];
				 smsMessages[i]  = SmsMessage.createFromPdu(pdu);  
			}
			for(SmsMessage msg:smsMessages){  
                smsContent += msg.getMessageBody(); 
                smsSender = msg.getOriginatingAddress();  
                smsDate = new Date(msg.getTimestampMillis());
			}
			Log.i("MessageRouter", "SmsAnalyzer:sender:"+smsSender+" content:"+smsContent);
		}
		//设置最后发送者号码
		env.setLastPhoneNumber(this.smsSender);
		
		
		String[] splitedContent = this.smsContent.split("#");
		dispatcher(splitedContent);

	}
	
	private void dispatcher(String[] splitedContent){
		
		String command = splitedContent[0].toLowerCase(Locale.getDefault());
		
		String[] params = null;
		int paraLength = splitedContent.length-1;
		if(paraLength>0){
			params = new String[paraLength];
			for(int i=0;i<paraLength;i++){
				params[i] = splitedContent[i+1];
			}
		}
		
		//action 遍历////////
		if(command.equals(ListenSwitchAction.command)){
			this.smsAction = new ListenSwitchAction(this.env, this.context, params);
		
		}else{
			String[] param = new String[1];
			param[0] = this.smsContent;
			this.smsAction = new DefaultAction(this.env,this.context,param);
		}
	}
	
	

	public String getSmsSender() {
		return smsSender;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public Date getSmsDate() {
		return smsDate;
	}

	public AbstractAction getSmsAction() {
		return smsAction;
	}
}
