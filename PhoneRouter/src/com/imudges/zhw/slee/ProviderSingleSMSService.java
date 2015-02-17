package com.imudges.zhw.slee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imudges.zhw.javabean.MessageInfo;
import com.imudges.zhw.javabean.ThreadSession;
import com.imudges.zhw.slee.ProviderThreadSMSService.SMSRunnable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ProviderSingleSMSService extends Service {
	private static final String SMS_ALL = "content://sms/";
	// 查询联系人
	private static final String CONTACTS_LOOKUP = "content://com.android.contacts/phone_lookup/";
	Cursor cur_smsdetail;
	Cursor cur_smsdetail_qcontact;
	private int threadId;
	public ArrayList<BmobObject> smsDeatilList;

	public ProviderSingleSMSService() {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String id = intent.getStringExtra("threadId");
		Log.e("Id", id);
		this.threadId = Integer.parseInt(id);
		Log.e("threadId", String.valueOf(this.threadId));
		smsDeatilList = new ArrayList<BmobObject>();
		Thread th = new Thread(new SMSRunnable(this));
		th.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		Log.e("进来了", "oncreate");
		
		super.onCreate();
	}

	class SMSRunnable implements Runnable {
		public Context context;

		public SMSRunnable(Context context) {
			super();
			this.context = context;
		}

		@Override
		public void run() {
			preSMSDetail();
			smsDeatilList = SMSDetailList();
			int i = 0;
			List<BmobObject> sublist = new ArrayList<BmobObject>();
			Log.i("批量上传", "开始分批");
			for (i = 0; i < smsDeatilList.size(); i = i + 49) {
				if (i + 50 < smsDeatilList.size()) {
					sublist = smsDeatilList.subList(i, i + 48);
					Log.i("批量上传", "50一批");
				} else {
					sublist = smsDeatilList.subList(i, smsDeatilList.size());
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

	private void preSMSDetail() {
		Uri uri = Uri.parse(SMS_ALL);
		String[] projection = { "_id", "thread_id", "address", "body", "date",
				"type" };
		cur_smsdetail = getContentResolver().query(uri, projection,
				"thread_id=? ", new String[] { Integer.toString(threadId) },
				"date ASC");
		Log.i("用threadid查询", "完成");
		// 对cursor进行处理，遇到号码后获取对应的联系人名称
		cur_smsdetail_qcontact = new CursorWrapper(cur_smsdetail) {
			public String getString(int columnIndex) {
				if (super.getColumnIndex("address") == columnIndex) {
					String address = super.getString(columnIndex);
					// 读取联系人，查询对应的名称
					Uri uri_qcontact = Uri.parse(CONTACTS_LOOKUP + address);
					Cursor cur = getContentResolver().query(uri_qcontact, null,
							null, null, null);
					if (cur.moveToFirst()) {
						String contactName = cur.getString(cur
								.getColumnIndex("display_name"));
						return contactName;
					}
					return address;
				}
				return super.getString(columnIndex);
			}
		};
	}

	private ArrayList<BmobObject> SMSDetailList() {
		Log.e("SMSDetailList", "进来了");
		ArrayList<BmobObject> tsl = new ArrayList<BmobObject>();
		int contactNameColumn = cur_smsdetail_qcontact
				.getColumnIndex("address");// 联系人名字
		int dateColumn = cur_smsdetail_qcontact.getColumnIndex("date");// 本短信发送的时间
		int msgContentColumn = cur_smsdetail_qcontact.getColumnIndex("body");// 本条短信内容
		int typeCloumn = cur_smsdetail_qcontact.getColumnIndex("type");// 本条短信内容
		if (cur_smsdetail_qcontact.moveToFirst()) {
			Log.i("用cur_smsdetail_qcontact", "moveToFirst");
			String contactName;
			String date;
			String msgContent;
			int type;
			do {
				MessageInfo ts = new MessageInfo();
				contactName = cur_smsdetail_qcontact
						.getString(contactNameColumn);
				date = cur_smsdetail_qcontact.getString(dateColumn);
				msgContent = cur_smsdetail_qcontact.getString(msgContentColumn);
				type = cur_smsdetail_qcontact.getInt(typeCloumn);
				Date d = new Date(Long.parseLong(date));
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd " + "\n" + "hh:mm:ss");
				date = dateFormat.format(d);

				ts.setName(contactName);
				ts.setType(type);
				ts.setSmsContent(msgContent);
				ts.setSmsDate(date);
				ts.setUserName(BmobUser.getCurrentUser(getApplicationContext())
						.getUsername());
				Log.i("messageInfo 内容", ts.toString());
				tsl.add(ts);
			} while (cur_smsdetail_qcontact.moveToNext());
		}
		cur_smsdetail_qcontact.close();
		return tsl;

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
