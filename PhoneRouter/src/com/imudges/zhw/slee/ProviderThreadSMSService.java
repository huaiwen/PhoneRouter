package com.imudges.zhw.slee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.imudges.zhw.javabean.ThreadSession;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ProviderThreadSMSService extends Service {
	public HashMap<String, String> number_name;// key �Ǻ��룬value ��name
	public ContentResolver cr;
	private static final String CONVERSATIONS = "content://sms/conversations/";// �Ự
	private static final String CONTACTS_LOOKUP = "content://com.android.contacts/phone_lookup/";// ��ѯ��ϵ��
	private Cursor cursor_smslist;
	private Cursor cursor_smslist_qcontact;
	private List<BmobObject> tslist;
	public ProviderThreadSMSService() {
	}
	@Override
	public void onCreate() {
		
		super.onCreate();
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("������", "oncreate");
		tslist = new ArrayList<BmobObject>();
		Thread th = new Thread(new SMSRunnable(this));
		th.start();
		return super.onStartCommand(intent, flags, startId);
	}


	class SMSRunnable implements Runnable {
		public Context context;
		public SMSRunnable(Context context) {
			super();
			this.context = context;
		}
		@Override
		public void run() {
			preList();
			tslist = SMSList();
			int i = 0;
			List<BmobObject> sublist = new ArrayList<BmobObject>();
			Log.i("�����ϴ�", "��ʼ����");
			for (i = 0; i < tslist.size(); i = i + 49) {
				if (i + 50 < tslist.size()) {
					sublist = tslist.subList(i, i + 48);
					Log.i("�����ϴ�", "50һ��");
				} else {
					sublist = tslist.subList(i, tslist.size());
					Log.i("�����ϴ�", "û��50һ��");
				}
				new BmobObject().insertBatch(context, sublist,
						new SaveListener() {
							@Override
							public void onSuccess() {
								Log.i("�ϴ�", "�ϴ��ɹ�");

							}

							@Override
							public void onFailure(int code, String msg) {
								// TODO Auto-generated method stub
								Log.i("�ϴ�", "�ϴ�ʧ��" + msg + " " + code);
							}
						});
			}
		
		}

	}

	public void preList() {
		Log.e("SMSList", "������");
		Uri uri = Uri.parse(CONVERSATIONS);
		String[] projection = new String[] { "groups.group_thread_id AS _id",
				"groups.msg_count AS msg_count",
				"groups.group_date AS last_date", "sms.body AS last_msg",
				"sms.address AS contact" };

		cursor_smslist = getContentResolver().query(uri, projection, null,
				null, "last_date DESC"); // ��ѯ�������ڵ���

		// ��cursor���д�������������ȡ��Ӧ����ϵ������
		cursor_smslist_qcontact = new CursorWrapper(cursor_smslist) {
			@Override
			public String getString(int columnIndex) {
				if (super.getColumnIndex("contact") == columnIndex) {
					String contact = super.getString(columnIndex);
					// ��ȡ��ϵ�ˣ���ѯ��Ӧ������
					Uri uri_qcontact = Uri.parse(CONTACTS_LOOKUP + contact);
					Cursor cur = getContentResolver().query(uri_qcontact, null,
							null, null, null);
					if (cur.moveToFirst()) {
						String contactName = cur.getString(cur
								.getColumnIndex("display_name"));
						return contactName;
					}
					return contact;
				}
				return super.getString(columnIndex);
			}
		};
	}

	public ArrayList<BmobObject> SMSList() {
		// ��ȡ�Ự��Ϣ
		Log.e("SMSList", "������");
		ArrayList<BmobObject> tsl = new ArrayList<BmobObject>();
		int threadIdColumn = cursor_smslist_qcontact.getColumnIndex("_id");// ����Ự��id
		int contactColumn = cursor_smslist_qcontact.getColumnIndex("contact");// ��ϵ������
		int last_dateColumn = cursor_smslist_qcontact
				.getColumnIndex("last_date");// ���һ�ε�ʱ��
		int last_msgColumn = cursor_smslist_qcontact.getColumnIndex("last_msg");// ���һ����������
		int msg_countColumn = cursor_smslist_qcontact
				.getColumnIndex("msg_count");
		if (cursor_smslist_qcontact.moveToFirst()) {
			String contact;
			String last_date;
			String last_msg;
			String date;
			int msg_counts;
			int threadId;
			do {
				ThreadSession ts = new ThreadSession();

				threadId = cursor_smslist_qcontact.getInt(threadIdColumn);
				contact = cursor_smslist_qcontact.getString(contactColumn);
				last_date = cursor_smslist_qcontact.getString(last_dateColumn);

				Date d = new Date(Long.parseLong(last_date));
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd " + "\n" + "hh:mm:ss");
				date = dateFormat.format(d);

				last_msg = cursor_smslist_qcontact.getString(last_msgColumn);
				msg_counts = cursor_smslist_qcontact.getInt(msg_countColumn);
				ts.setThreadId(threadId);
				ts.setContact(contact);
				ts.setLast_date(date);
				ts.setLast_msg(last_msg);
				ts.setMsg_count(msg_counts);
				ts.setUsername(BmobUser.getCurrentUser(getApplicationContext())
						.getUsername());
				Log.e("eheh",ts.toString());
				tsl.add(ts);
			} while (cursor_smslist_qcontact.moveToNext());
		}
		cursor_smslist_qcontact.close();
		return tsl;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
