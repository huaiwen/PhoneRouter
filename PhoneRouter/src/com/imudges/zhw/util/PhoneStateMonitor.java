package com.imudges.zhw.util;

import com.imudges.phonerouter.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
/**
 * 获取手机的信号强度，并转换为信号的格数
 * 以int存在 sp里  
 * 分别是
 * gsmlevel
 * cdmalevel
 * evdolevel
 * @author wymon
 *
 */
public class PhoneStateMonitor extends PhoneStateListener {
	private SharedPreferences settingSP = null;
	private Context context = null;


	public PhoneStateMonitor(Context app) {
		context = app.getApplicationContext();
		settingSP = context.getSharedPreferences(
				app.getResources().getString(R.string.settingPreferencesFile),
				Context.MODE_PRIVATE);// 配置文件
		// TODO Auto-generated constructor stub
	}



	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		super.onSignalStrengthsChanged(signalStrength);
		/*
		 * signalStrength.isGsm() 是否GSM信号 2G or 3G
		 * signalStrength.getCdmaDbm(); 联通3G 信号强度
		 * signalStrength.getCdmaEcio(); 联通3G 载干比
		 * signalStrength.getEvdoDbm(); 电信3G 信号强度
		 * signalStrength.getEvdoEcio(); 电信3G 载干比
		 * signalStrength.getEvdoSnr(); 电信3G 信噪比
		 * signalStrength.getGsmSignalStrength(); 2G 信号强度
		 * signalStrength.getGsmBitErrorRate(); 2G 误码率
		 */
		int gsmlevel,cdmalevel,evdolevel;//三频的信号强度
		gsmlevel= cdmalevel=evdolevel=0;
		int level = 0;
		int cdmaDbm = signalStrength.getCdmaDbm();
		int cdmaEcio = signalStrength.getCdmaEcio();
		int levelDbm = 0;
		int levelEcio = 0;
		int levelEvdoDbm = 0;
		int levelEvdoSnr = 0;
		int evdoDbm = signalStrength.getEvdoDbm();
		int evdoSnr = signalStrength.getEvdoEcio();
		int asu = signalStrength.getGsmSignalStrength();// gsm的信号强度

		/**
		 * 2g 的gsm信号强度
		 */
		if (asu < 2 || asu == 99) {
			level = 0;
		} else if (asu >= 12) {
			level = 4;
		} else if (asu >= 8) {
			level = 3;
		} else if (asu >= 5) {
			level = 2;
		} else {
			level = 1;
		}
          gsmlevel = level;
		/**
		 * 3g的 cdma 信号强度
		 */
		if (cdmaDbm >= -75)
			levelDbm = 4;
		else if (cdmaDbm >= -85)
			levelDbm = 3;
		else if (cdmaDbm >= -95)
			levelDbm = 2;
		else if (cdmaDbm >= -100)
			levelDbm = 1;
		else
			levelDbm = 0;

		if (cdmaEcio >= -90)
			levelEcio = 4;
		else if (cdmaEcio >= -110)
			levelEcio = 3;
		else if (cdmaEcio >= -130)
			levelEcio = 2;
		else if (cdmaEcio >= -150)
			levelEcio = 1;
		else
			levelEcio = 0;

		
		cdmalevel = (cdmaDbm<cdmaEcio)?cdmaDbm:cdmaEcio;
		/**
		 * 3g的中国电信信号强度
		 */
		if (evdoDbm >= -65)
			levelEvdoDbm = 4;
		else if (evdoDbm >= -75)
			levelEvdoDbm = 3;
		else if (evdoDbm >= -90)
			levelEvdoDbm = 2;
		else if (evdoDbm >= -105)
			levelEvdoDbm = 1;
		else
			levelEvdoDbm = 0;

		if (evdoSnr >= 7)
			levelEvdoSnr = 4;
		else if (evdoSnr >= 5)
			levelEvdoSnr = 3;
		else if (evdoSnr >= 3)
			levelEvdoSnr = 2;
		else if (evdoSnr >= 1)
			levelEvdoSnr = 1;
		else
			levelEvdoSnr = 0;

		evdolevel = (levelEvdoDbm<levelEvdoSnr)?levelEvdoDbm:levelEvdoSnr;
		
		
		
		
		Editor editor = settingSP.edit();
		editor.putInt("gsmlevel", gsmlevel);
		editor.putInt("cdmalevel", cdmalevel);
		editor.putInt("evdolevel", evdolevel);
		editor.commit();

	/*	
		String str = "IsGsm : " + signalStrength.isGsm() + "\nCDMA Dbm : "
				+ signalStrength.getCdmaDbm() + "Dbm" + "\nCDMA Ecio : "
				+ signalStrength.getCdmaEcio() + "dB*10" + "\nEvdo Dbm : "
				+ signalStrength.getEvdoDbm() + "Dbm" + "\nEvdo Ecio : "
				+ signalStrength.getEvdoEcio() + "dB*10"
				+ "\nGsm SignalStrength : "
				+ signalStrength.getGsmSignalStrength()
				+ "\nGsm BitErrorRate : "
				+ signalStrength.getGsmBitErrorRate();

		System.out.println("---->" + str);
*/
		/*
		 * mIcon3G.setImageLevel(Math.abs(signalStrength
		 * .getGsmSignalStrength()));
		 */
	}
}

