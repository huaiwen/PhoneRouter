package com.imudges.ldj.servse;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
/*启动该Service获取当前网络连接状态
 * 控制台打印结果*/
public class GetNetworkStateInfo extends Service {

	SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm  ");//时间格式     
	Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
	String   str   =   "当前时间"+formatter.format(curDate);//存放时间的字符串

	@Override
	public void onCreate() {
		super.onCreate();
		int isconnected = isNetworkAvailable(this);
		if(isconnected == 0){
			System.out.println("---->"+str+"当前使用WIFI网络"+":"+getInfo());
			//Toast.makeText(getApplicationContext(), str+"当前使用WIFI网络"+":"+getInfo(), 1);
		}
		else if(isconnected == 1)
			System.out.println("---->"+str+"当前使用2G/3G网络");
			//Toast.makeText(getApplicationContext(), str+"当前使用2G/3G网络", 1);
		else{
			System.out.println("---->"+str+"当前无网络连接");
			//Toast.makeText(getApplicationContext(), str+"当前无网络连接", 1);
		}
	}
	
	
	private String getInfo() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String ssid = info.getSSID();//获取当前所连接WIFI名称
		return ssid;
	}

	/*private String intToIp(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
	}*/
	
	public static int isNetworkAvailable(Context context)  {  
	    ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	    NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	    int flag = -1;  
	    if(   (mWifi != null)   &&  (  (mWifi.isAvailable())  ||  (mMobile.isAvailable()  )  )  )  
	    {  
	        if(mWifi.isConnected())  
	        {  
	            flag = 0;  
	        }
	        else if(mMobile.isConnected()){
	        	flag = 1;
	        }
	    }  
	    return flag;  
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	} 
}
