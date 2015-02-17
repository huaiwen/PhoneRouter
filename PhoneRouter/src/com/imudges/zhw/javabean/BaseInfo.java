package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobObject;

/**
 * 储存手机的各种基本信息
 * 
 * @author wymon
 *
 */
public class BaseInfo extends BmobObject {
	private int gsmLevel;
	private int cdmaLevel;
	private int evdoLevel;
	private int BatteryLevel;
	private String BatteryState;
	public String getBatteryState() {
		return BatteryState;
	}

	public void setBatteryState(String batteryState) {
		BatteryState = batteryState;
	}

	private String WifiName;
	private int wifiLevel;
	private boolean isWifi;
	private boolean isGprs;
	private String Username;

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public int getGsmLevel() {
		return gsmLevel;
	}

	public void setGsmLevel(int gsmLevel) {
		this.gsmLevel = gsmLevel;
	}

	public int getCdmaLevel() {
		return cdmaLevel;
	}

	public void setCdmaLevel(int cdmaLevel) {
		this.cdmaLevel = cdmaLevel;
	}

	public int getEvdoLevel() {
		return evdoLevel;
	}

	public void setEvdoLevel(int evdaLevel) {
		this.evdoLevel = evdaLevel;
	}

	public int getBatteryLevel() {
		return BatteryLevel;
	}

	public void setBatteryLevel(int BatteryLevel) {
		this.BatteryLevel = BatteryLevel;
	}

	public String getWifiName() {
		return WifiName;
	}

	public void setWifiName(String wifiName) {
		WifiName = wifiName;
	}

	public int getWifiLevel() {
		return wifiLevel;
	}

	public void setWifiLevel(int wifiLevel) {
		this.wifiLevel = wifiLevel;
	}

	public boolean isWifi() {
		return isWifi;
	}

	public void setWifi(boolean isWifi) {
		this.isWifi = isWifi;
	}

	public boolean isGprs() {
		return isGprs;
	}

	public void setGprs(boolean isGprs) {
		this.isGprs = isGprs;
	}

}
