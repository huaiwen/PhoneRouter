package com.imudges.ldj.utils;

/*����ʵ�ֻ�ȡWIFIɨ��õ����ȵ���
 * �����贴��WifiResAbout mWifiAdmin= new WifiResAbout(Context);
 * ����mWifiAdmin.openNetCard()���Դ�WLAN;
 * ����mWifiAdmin.closeNetCard()���Թر�WLAN
 * ����mWifiAdmin.getScanResult()�õ��ȵ�ɨ����������String,���ַ����ĸ�ʽ������WifiResAbout�ĵ�74�н�������
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
	//���ڴ��getScanResult()�����еõ��Ľ��
	private StringBuffer mStringBuffer = new StringBuffer();
	//���ڴ�ŵ���ϵͳ������õ��Ľ��
	private List<ScanResult> listResult;
	//����ScanResult����
	private ScanResult mScanResult;
	// ����WifiManager����
	private WifiManager mWifiManager;
	// ����WifiInfo����
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
	ɨ�躯��
	*/
	public void scan() {
		mWifiManager.startScan();
		listResult = mWifiManager.getScanResults();
		if (listResult != null) {
			System.out.println("��ǰ��������������磬��鿴ɨ����");
		} else {
			Log.i(TAG, "��ǰ����û����������");
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
				mStringBuffer = mStringBuffer.append("�ȵ�����").append(i + 1)
						.append(" :").append(mScanResult.SSID).append("   MAC��ַ")
						.append(mScanResult.BSSID).append("   ����")
						.append(mScanResult.capabilities).append("   Ƶ��")
						.append(mScanResult.frequency).append("    �ź�ǿ��")
						.append(mScanResult.level).append("->")
						.append("\n\n");
			}
		}
		return mStringBuffer.toString();
	}

	

}
