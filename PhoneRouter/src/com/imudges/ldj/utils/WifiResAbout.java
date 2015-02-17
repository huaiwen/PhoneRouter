package com.imudges.ldj.utils;

/*此类实现获取WIFI扫描得到的热点结果
 * 首先需创建WifiResAbout mWifiAdmin= new WifiResAbout(Context);
 * 调用mWifiAdmin.openNetCard()可以打开WLAN;
 * 调用mWifiAdmin.closeNetCard()可以关闭WLAN
 * 调用mWifiAdmin.getScanResult()得到热点扫描结果，返回String,该字符串的格式可以在WifiResAbout的第74行进行设置
 **/
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiResAbout {
	private final static String TAG = "WifiAdmin";
	//用于存放getScanResult()方法中得到的结果
	private StringBuffer mStringBuffer = new StringBuffer();
	//用于存放调用系统方法后得到的结果
	private List<ScanResult> listResult;
	//定义ScanResult对象
	private ScanResult mScanResult;
	// 定义WifiManager对象
	private WifiManager mWifiManager;
	// 定义WifiInfo对象
	private WifiInfo mWifiInfo;

	
	public WifiResAbout(Context context){
		mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	
	public void openNetCard() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	public void closeNetCard() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/*
	扫描函数
	*/
	public void scan() {
		mWifiManager.startScan();
		listResult = mWifiManager.getScanResults();
		if (listResult != null) {
			System.out.println("当前区域存在无线网络，请查看扫描结果");
		} else {
			Log.i(TAG, "当前区域没有无线网络");
		}
	}

	public String getScanResult() {
		if(mStringBuffer != null)
		{
			mStringBuffer = new StringBuffer();
		}
		scan();
		listResult = mWifiManager.getScanResults();
		if(listResult != null){
			for(int i = 0; i < listResult.size(); i++){
				mScanResult = listResult.get(i);
				mStringBuffer = mStringBuffer.append("热点名称").append(i + 1)
						.append(" :").append(mScanResult.SSID).append("   MAC地址")
						.append(mScanResult.BSSID).append("   类型")
						.append(mScanResult.capabilities).append("   频率")
						.append(mScanResult.frequency).append("    信号强度")
						.append(mScanResult.level).append("->")
						.append("\n\n");
			}
		}
		return mStringBuffer.toString();
	}

	

}
