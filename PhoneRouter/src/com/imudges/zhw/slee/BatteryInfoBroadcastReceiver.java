package com.imudges.zhw.slee;

import com.imudges.phonerouter.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.BatteryManager;

public class BatteryInfoBroadcastReceiver extends BroadcastReceiver{
	private SharedPreferences settingSP = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		settingSP = context.getSharedPreferences(
				context.getApplicationContext().getResources().getString(R.string.settingPreferencesFile),
				Context.MODE_PRIVATE);// 配置文件
		String action = intent.getAction();
		if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
		
			int status = intent.getIntExtra("status", 0);
			int health = intent.getIntExtra("health", 1);
			int level = intent.getIntExtra("level", 0);//电量
			int scale = intent.getIntExtra("scale", 0);//电池规模
			int temperature = intent.getIntExtra("temperature", 0);//电池温度
			String technology = intent.getStringExtra("technology");//电池类型。。。
			
			//表示当前电池充电状况
			String statusString = "unknown";
		          
		switch (status) {
			case BatteryManager.BATTERY_STATUS_UNKNOWN:
				statusString = "状况未知";
				break;
			case BatteryManager.BATTERY_STATUS_CHARGING:
				statusString = "正在充电";
				break;
			case BatteryManager.BATTERY_STATUS_DISCHARGING:
				statusString = "";
				break;
			case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
				statusString = "电量不足";
				break;
			case BatteryManager.BATTERY_STATUS_FULL:
				statusString = "充满";
				break;
		}
		//电池健康状况      
		String healthString = "unknown";
		        
		switch (health) {
			case BatteryManager.BATTERY_HEALTH_UNKNOWN:
				healthString = "unknown";
				break;
			case BatteryManager.BATTERY_HEALTH_GOOD:
				healthString = "good";
				break;
			case BatteryManager.BATTERY_HEALTH_OVERHEAT:
				healthString = "overheat";
				break;
			case BatteryManager.BATTERY_HEALTH_DEAD:
				healthString = "dead";
				break;
			case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
				healthString = "voltage";
				break;
			case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
				healthString = "unspecified failure";
				break;
		}
		Editor editor = settingSP.edit();
		editor.putString("BATTERY_STATUS", statusString);
		editor.putInt("BATTERY_LEVEL", level);
		editor.commit();     
		
		}
		
	}

}
