package com.imudges.ldj.servse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.imudges.zhw.javabean.PhoneCallLog;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;

/*
 * 启动该Service得到通话记录信息*/
public class CallLogService extends Service {
	/*
	 * 所得信息按下标一一对应
	 */
	ArrayList<BmobObject> pcl;
	// 此数组保存通话记录中的联系人姓名，如果联系人没有被存储则为null
	ArrayList<String> name = new ArrayList<String>();
	// 此数组保存通话记录中联系人号码
	ArrayList<String> num = new ArrayList<String>();
	// 此数组保存通话记录中该记录类型，1表示呼入，2表示呼出，3表示未接(不包括来电时主动拒接)
	ArrayList type = new ArrayList();
	// 此数组保存通话记录的时间
	ArrayList<String> time = new ArrayList<String>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		pcl = new ArrayList<BmobObject>();
		Thread th = new Thread(new SMSRunnable(this));
		th.start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
	}

	public ArrayList<BmobObject> GetCallLog() {
		ArrayList<BmobObject> tsl = new ArrayList<BmobObject>();
		long callTime;
		Date date;
		ContentResolver cr = getContentResolver();
		final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[] {
				CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
				CallLog.Calls.TYPE, CallLog.Calls.DATE }, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			PhoneCallLog pclg = new PhoneCallLog();
			pclg.setCallNum(cursor.getString(0));
			pclg.setCallName(cursor.getString(1));
			pclg.setCallType(cursor.getInt(2));
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = new Date(Long.parseLong(cursor.getString(3)));
			pclg.setCallDate(sfd.format(date));
			pclg.setUsername(BmobUser.getCurrentUser(getApplicationContext()).getUsername());
			Log.i("通话记录", pclg.toString());
			tsl.add(pclg);
		}
		return tsl;
	}

	class SMSRunnable implements Runnable {
		public Context context;

		public SMSRunnable(Context context) {
			super();
			this.context = context;
		}

		@Override
		public void run() {

			pcl = GetCallLog();
			int i = 0;
			List<BmobObject> sublist = new ArrayList<BmobObject>();
			Log.i("批量上传", "开始分批");
			for (i = 0; i < pcl.size(); i = i + 49) {
				if (i + 50 < pcl.size()) {
					sublist = pcl.subList(i, i + 48);
					Log.i("批量上传", "50一批");
				} else {
					sublist = pcl.subList(i, pcl.size());
					Log.i("批量上传", "没到50一批");
				}
				new BmobObject().insertBatch(context, sublist,
						new SaveListener() {
							@Override
							public void onSuccess() {
								Log.i("上传", "上传成功");

							}

							@Override
							public void onFailure(int code, String msg) {
								// TODO Auto-generated method stub
								Log.i("上传", "上传失败" + msg + " " + code);
							}
						});
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
