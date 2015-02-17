package com.imudges.ldj.servse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.imudges.phonerouter.R;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GetSIMinfo extends Service {
	// 声明Listview对象
	ListView showView;
	// 声明代表状态名的数组
	String[] statusNames;
	// 声明代表手机状态的集合
	ArrayList<String> statusValues = new ArrayList<String>();

	@Override
	public void onCreate() {
		super.onCreate();
		// setContentView(R.layout.activity_main);
		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		statusNames = getResources().getStringArray(R.array.statusNames);
		String[] simState = getResources().getStringArray(R.array.simState);
		// 获取代表电话网络类型的数组
		String[] phoneType = getResources().getStringArray(R.array.phoneType);

		// 获取设备编号
		statusValues.add(tManager.getDeviceId());

		// 获取系统平台的版本
		statusValues.add(tManager.getDeviceSoftwareVersion() != null ? tManager
				.getDeviceSoftwareVersion() : "未知");

		// 获取网络运营商代号
		statusValues.add(tManager.getNetworkOperator());

		// 获取网络运营商名称
		statusValues.add(tManager.getNetworkOperatorName());

		// 获取手机网络类型
		statusValues.add(phoneType[tManager.getPhoneType()]);

		// 获取设备所在未知
		statusValues.add(tManager.getCellLocation() != null ? tManager
				.getCellLocation().toString() : "未知位置");

		// 获取SIM卡的国别
		statusValues.add(tManager.getSimCountryIso());

		// 获取SIM卡状态
		statusValues.add(simState[tManager.getSimState()]);

		// 获得ListView对象

		ArrayList<Map<String, String>> status = new ArrayList<Map<String, String>>();
		// 遍历statusValues集合，将statusNames、statusValues
		// 的数据封装到List<Map<String , String>>集合中
		for (int i = 0; i < statusValues.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", statusNames[i]);
			map.put("value", statusValues.get(i));
			status.add(map);
		}
		// 使用SimpleAdapter封装List数据
		SimpleAdapter adapter = new SimpleAdapter(this, status, R.layout.simline,
				new String[] { "name", "value" }, new int[] { R.id.name,
						R.id.value });
		// 为ListView设置Adapter
		//showView.setAdapter(adapter);
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
