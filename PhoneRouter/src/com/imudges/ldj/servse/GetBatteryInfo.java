package com.imudges.ldj.servse;


import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;

public class GetBatteryInfo extends Activity {

	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
			
				int status = intent.getIntExtra("status", 0);
				int health = intent.getIntExtra("health", 1);
				int level = intent.getIntExtra("level", 0);//����
				int scale = intent.getIntExtra("scale", 0);//��ع�ģ
				int temperature = intent.getIntExtra("temperature", 0);//����¶�
				String technology = intent.getStringExtra("technology");//������͡�����
				
				//��ʾ��ǰ��س��״��
				String statusString = "unknown";
			          
			switch (status) {
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					statusString = "״��δ֪";
					break;
				case BatteryManager.BATTERY_STATUS_CHARGING:
					statusString = "���ڳ��";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					statusString = "";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					statusString = "��������";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					statusString = "����";
					break;
			}
			//��ؽ���״��      
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
			        
			
			
			}
	}
};
	
	/*public void onCreate() {
		registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}*/
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

	}
	
	protected void onResume() {
		super.onResume();
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    unregisterReceiver(mBatInfoReceiver);
	  }

	

	
}

