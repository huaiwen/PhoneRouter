package com.imudges.zhw.slee;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.imudges.zhw.javabean.ContactsDetail;
import com.imudges.zhw.slee.ProviderSingleSMSService.SMSRunnable;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;

public class ProviderLookUpService extends Service {
	public ArrayList<BmobObject> contactsList;
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private static final int PHONES_NUMBER_INDEX = 1;
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	public ProviderLookUpService() {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("ProviderLookUpService", "onStartCommand");
		contactsList = new ArrayList<BmobObject>();
		Thread th = new Thread(new SMSRunnable(this));
		th.start();
		return super.onStartCommand(intent, flags, startId);
	}

	class SMSRunnable implements Runnable {
		public Context context;

		public SMSRunnable(Context context) {
			this.context = context;
		}

		@Override
		public void run() {

			contactsList = GetContacts();
			int i = 0;
			List<BmobObject> sublist = new ArrayList<BmobObject>();

			Log.i("批量上传", "开始分批");

			for (i = 0; i < contactsList.size(); i = i + 49) {
				if (i + 50 < contactsList.size()) {
					sublist = contactsList.subList(i, i + 48);
					Log.i("批量上传", "50一批");
				} else {
					sublist = contactsList.subList(i, contactsList.size());
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

	public ArrayList<BmobObject> GetContacts() {
		ArrayList<BmobObject> gc = new ArrayList<BmobObject>();
		ArrayList<BmobObject> ga = new ArrayList<BmobObject>();
		ContentResolver resolver = this.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		String phoneNumber;
		// 得到联系人名称
		String contactName;
		Long contactid, photoid;
		Bitmap contactPhoto = null;
		if (phoneCursor.moveToFirst()) {
			do {
				ContactsDetail cd = new ContactsDetail();
				// 得到手机号码
				phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				// 得到联系人ID
				contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// 得到联系人头像ID
				photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				/*
				 * // 得到联系人头像Bitamp
				 * 
				 * // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的 if (photoid > 0)
				 * { Uri uri = ContentUris.withAppendedId(
				 * ContactsContract.Contacts.CONTENT_URI, contactid);
				 * InputStream input = ContactsContract.Contacts
				 * .openContactPhotoInputStream(resolver, uri); contactPhoto =
				 * BitmapFactory.decodeStream(input); } else对无头像的用户设置默认头像
				 * 
				 * else { contactPhoto = BitmapFactory.decodeResource(null, 0);
				 * }
				 */
				cd.setContactName(contactName.trim());
				cd.setContactNumber(phoneNumber.trim());
				// cd.setContactPhonto(contactPhoto);
				cd.setUserName(BmobUser.getCurrentUser(getApplicationContext())
						.getUsername());
				Log.i("contacts", cd.toString());
				if (!ga.contains(cd)) {
					gc.add(cd);
					ga.add(cd);
				}
			} while (phoneCursor.moveToNext());
			phoneCursor.close();
		}
		return gc;

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
