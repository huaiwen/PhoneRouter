package com.imudges.zhw.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.imudges.phonerouter.R;
import com.imudges.zhw.javabean.MessageInfo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

public class GetSMSUtils {
	// ��ȡ����ÿ���ֻ����ű����ڴ�������!!!!
	public ArrayList<MessageInfo> list;
	public ContentResolver cr;
	public MessageInfo messageInfo;
	public Context context;
	public Bitmap defaultmap;

	public GetSMSUtils(Context context) {
		list = new ArrayList<MessageInfo>();
		this.cr = context.getContentResolver();
		defaultmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
	}

	// �˺������ڻ�ȡ�ֻ�����
	public ArrayList<MessageInfo> getSmsInPhone() {

		final String SMS_URI_ALL = "content://sms/";// ��ȡȫ��
		final String SMS_URI_INBOX = "content://sms/inbox";// ��ȡ�ռ���
		final String SMS_URI_SEND = "content://sms/sent";// ��ȡ������
		final String SMS_URI_DRAFT = "content://sms/draft";// ��ȡ�ݸ���

		// String smsBuilder = new String();

		try {
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_ALL);// All
			Cursor cursor = cr.query(uri, projection, null, null, "date desc");
			int nameColumn = cursor.getColumnIndex("person");// ��ϵ�������б����
			int phoneNumberColumn = cursor.getColumnIndex("address");// �ֻ���
			int smsbodyColumn = cursor.getColumnIndex("body");// ��������
			int dateColumn = cursor.getColumnIndex("date");// ����
			int typeColumn = cursor.getColumnIndex("type");// �շ����� 1��ʾ����
         													// 2��ʾ����
			if (cursor.moveToFirst()) {
				
				// -----------------------��Ϣ----------------
				String nameId;
				String phoneNumber;
				String smsbody;
				int type;
				String date;

				do{
				messageInfo = new MessageInfo();
				nameId = cursor.getString(nameColumn);
				phoneNumber = cursor.getString(phoneNumberColumn);
				smsbody = cursor.getString(smsbodyColumn);
				type = cursor.getInt(typeColumn);
				Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd " + "\n" + "hh:mm:ss");
				date = dateFormat.format(d);
               
				String name="";
			
				
				messageInfo.setName(name);
				messageInfo.setSmsContent(smsbody);
				messageInfo.setSmsDate(date);
				messageInfo.setType(type);
				list.add(messageInfo);
				}while(cursor.moveToNext());
		/*		// --------------------------ƥ����ϵ������--------------------------

				Uri personUri = Uri.withAppendedPath(
						ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
						phoneNumber);
				Cursor localCursor = cr.query(personUri, new String[] {
						PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID,
						PhoneLookup._ID }, null, null, null);

				if (localCursor.getCount() != 0) {
					localCursor.moveToFirst();

					String name = localCursor.getString(localCursor
							.getColumnIndex(PhoneLookup.DISPLAY_NAME));
					long photoid = localCursor.getLong(localCursor
							.getColumnIndex(PhoneLookup.PHOTO_ID));
					long contactid = localCursor.getLong(localCursor
							.getColumnIndex(PhoneLookup._ID));
					messageInfo.setName(name);
					messageInfo.setContactPhoneNumber(phoneNumber);
					// ���photoid ����0 ��ʾ��ϵ����ͷ�� �����û�и���������ͷ�������һ��Ĭ�ϵ�
					if (photoid > 0) {
						Uri uri1 = ContentUris.withAppendedId(
								ContactsContract.Contacts.CONTENT_URI,
								contactid);
						InputStream input = ContactsContract.Contacts
								.openContactPhotoInputStream(cr, uri1);
						messageInfo.setContactPhoto(BitmapFactory
								.decodeStream(input));
					} else {
						messageInfo.setContactPhoto(defaultmap);
					}
				} else {
					if (type == 1) {
						messageInfo.setName("δ֪��ϵ��");
					} else {
						messageInfo.setName("��");
					}
					messageInfo.setContactPhoneNumber(phoneNumber);
					messageInfo.setContactPhoto(defaultmap);
				}

				localCursor.close();*/
				
				
			}
		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}
		return list;
	}
	
}
