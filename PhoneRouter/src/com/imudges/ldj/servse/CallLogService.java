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
 * ������Service�õ�ͨ����¼��Ϣ*/
public class CallLogService extends Service {
	/*
	 * ������Ϣ���±�һһ��Ӧ
	 */
	ArrayList<BmobObject> pcl;
	// �����鱣��ͨ����¼�е���ϵ�������������ϵ��û�б��洢��Ϊnull
	ArrayList<String> name = new ArrayList<String>();
	// �����鱣��ͨ����¼����ϵ�˺���
	ArrayList<String> num = new ArrayList<String>();
	// �����鱣��ͨ����¼�иü�¼���ͣ�1��ʾ���룬2��ʾ������3��ʾδ��(����������ʱ�����ܽ�)
	ArrayList type = new ArrayList();
	// �����鱣��ͨ����¼��ʱ��
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
			Log.i("ͨ����¼", pclg.toString());
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
			Log.i("�����ϴ�", "��ʼ����");
			for (i = 0; i < pcl.size(); i = i + 49) {
				if (i + 50 < pcl.size()) {
					sublist = pcl.subList(i, i + 48);
					Log.i("�����ϴ�", "50һ��");
				} else {
					sublist = pcl.subList(i, pcl.size());
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

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
