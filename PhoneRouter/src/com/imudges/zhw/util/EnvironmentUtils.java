package com.imudges.zhw.util;

import java.lang.reflect.Method;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.imudges.ldj.utils.HDExplorerActivity;
import com.imudges.ldj.utils.WifiAdmin;
import com.imudges.phonerouter.R;
import com.imudges.zhw.javabean.BaseInfo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.sax.StartElementListener;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * 环境工具类
 * */
public class EnvironmentUtils {
	private Context context = null;
	private SharedPreferences settingSP = null;
	private ConnectivityManager mCM;// 调用adil的连接器

	public EnvironmentUtils(Context app) {
		// 初始化Setting SharedPreferences
		context = app.getApplicationContext();
		mCM = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统服务
		settingSP = context.getSharedPreferences(
				app.getResources().getString(R.string.settingPreferencesFile),
				Context.MODE_PRIVATE);// 配置文件
	}

	public boolean isOpen(int position) {

		return settingSP.getBoolean(String.valueOf(position), true);
	}

	public void setOpen(int position, boolean isOpen) {
		Editor editor = settingSP.edit();
		editor.putBoolean(String.valueOf(position), isOpen);
		editor.commit();
		Log.e("设置位置和状态",
				String.valueOf(position) + " " + String.valueOf(isOpen));
	}

	public void sendIntent(int position) {
		switch (position) {
		case 5:// sd 卡

			break;
		case 6:// 手机储存

			break;

		default:
			break;
		}
	}

	public boolean isFirstRun() {
		return settingSP.getBoolean("isFirstRun", true);
	}

	public String getTransmitPhoneNumber() {// 转发的号码
		return settingSP.getString("transmitPhoneNumber", "13800138000");
	}

	public void setTransmitPhoneNumber(String str) {
		Editor editor = settingSP.edit();
		editor.putString("transmitPhoneNumber", str);
		editor.commit();
	}

	public String getLastPhoneNumber() {// 最后一次来电或短信的号码
		return settingSP.getString("lastPhoneNumber", "");
	}

	public void setLastPhoneNumber(String str) {
		Editor editor = settingSP.edit();
		editor.putString("lastPhoneNumber", str);
		editor.commit();
	}

	public boolean isRunning() {// 总开关，开了没
		return settingSP.getBoolean("totalSwitch", false);
	}

	public void setRunning(boolean value) {
		Editor editor = settingSP.edit();
		editor.putBoolean("totalSwitch", value);
		editor.commit();
	}

	public boolean isListening() {// 设置是否转发
		return settingSP.getBoolean("enableSwitch", false);
	}

	public void setListening(boolean value) {
		Editor editor = settingSP.edit();
		editor.putBoolean("enableSwitch", value);
		editor.commit();
	}

	/**
	 * 清理配置
	 */
	public void clearPrefences() {
		Editor editor = settingSP.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * untested
	 * */
	public boolean isNumber(String str) {
		return str.matches("[0-9]+");
	}

	/**
	 * 发短信
	 * 
	 * @param address
	 *            发给谁
	 * @param content
	 *            内容
	 */
	public void sendSms(String address, String content) {

		if (content.contains("[发送自手机路由器]")) {
			Log.i("MessageRouter", "EnvironmentUtils: Message Iteration:"
					+ content);
			return;
		}
		// content += "\n[发送自手机路由器]";
		SmsManager smsManager = SmsManager.getDefault();
		// 拆分多条
		List<String> divideContents = smsManager.divideMessage(content);
		for (String text : divideContents) {
			smsManager.sendTextMessage(address, null, text, null, null);
		}
		Log.i("MessageRouter", "EnvironmentUtils: SMS:\"" + content
				+ "\" has been send to:" + address);
	}

	// 开启/关闭GPRS
	private void setGprsEnabled(String methodName, boolean isEnable) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = new Class[1];
		argClasses[0] = boolean.class;
		try {
			Method method = cmClass.getMethod(methodName, argClasses);
			method.invoke(mCM, isEnable);
			Log.i("Open", "opened");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean gprsIsOpenMethod(String methodName) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;
		Boolean isOpen = false;
		try {
			Method method = cmClass.getMethod(methodName, argClasses);

			isOpen = (Boolean) method.invoke(mCM, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}

	public boolean isGprsOpen() {
		return gprsIsOpenMethod("getMobileDataEnabled");
	}

	public boolean openGprs() {
		boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled");
		Log.i("openGPRS  isOpen", String.valueOf(isOpen));
		if (!isOpen) {
			setGprsEnabled("setMobileDataEnabled", true);
		}
		return isOpen;
	}

	public boolean closeGprs() {
		boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled");
		Log.i("openGPRS  isOpen", String.valueOf(isOpen));
		if (isOpen) {
			setGprsEnabled("setMobileDataEnabled", false);
		}
		return isOpen;
	}

	/**
	 * 得到当前的手机基本状态信息
	 * 
	 * @return BaseInfo
	 */
	public BaseInfo getCurrentBaseInfo() {
		// 信号
		int gsmLevel = settingSP.getInt("gsmlevel", 4);
		int cdmaLevel = settingSP.getInt("cdmalevel", 4);
		int evdoLevel = settingSP.getInt("evdolevel", 4);
		// wifi
		WifiAdmin wifiAdmin = new WifiAdmin(context);
		Boolean isWifi = wifiAdmin.isWifiCon();
		String CurentSSID = wifiAdmin.getCurrentSSID();
		int WifiLevel = wifiAdmin.getWifiLevel();
		// 电池
		int BatteryLevel = settingSP.getInt("BATTERY_LEVEL", 0);
		String BatteryState = settingSP.getString("BATTERY_STATUS", "");
		BaseInfo bi = new BaseInfo();
		bi.setUsername(BmobUser.getCurrentUser(context).getUsername());
		bi.setCdmaLevel(cdmaLevel);
		bi.setEvdoLevel(evdoLevel);
		bi.setGsmLevel(gsmLevel);

		bi.setWifi(isWifi);
		bi.setWifiName(CurentSSID);
		bi.setWifiLevel(WifiLevel);

		bi.setBatteryLevel(BatteryLevel);
		bi.setBatteryState(BatteryState);
		return bi;
	}

	public void submitBaseInfo() {
		BaseInfo bi = new BaseInfo();
		bi = getCurrentBaseInfo();
		bi.save(context, new SaveListener() {

			@Override
			public void onSuccess() {
				Log.i("上传baseInfo", "成功");

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("上传baseInfo", "失败");

			}
		});
	}

}