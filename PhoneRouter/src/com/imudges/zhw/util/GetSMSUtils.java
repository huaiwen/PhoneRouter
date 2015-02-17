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
	// 读取出的每条手机短信保存在此数组里!!!!
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

	// 此函数用于获取手机短信
	public ArrayList<MessageInfo> getSmsInPhone() {

		final String SMS_URI_ALL = "content://sms/";// 读取全部
		final String SMS_URI_INBOX = "content://sms/inbox";// 读取收件箱
		final String SMS_URI_SEND = "content://sms/sent";// 读取发件箱
		final String SMS_URI_DRAFT = "content://sms/draft";// 读取草稿箱

		// String smsBuilder = new String();

		try {
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_ALL);// All
			Cursor cursor = cr.query(uri, projection, null, null, "date desc");
			int nameColumn = cursor.getColumnIndex("person");// 联系人姓名列表序号
			int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号
			int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
			int dateColumn = cursor.getColumnIndex("date");// 日期
			int typeColumn = cursor.getColumnIndex("type");// 收发类型 1表示接受
         													// 2表示发送
			if (cursor.moveToFirst()) {
				
				// -----------------------信息----------------
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
		/*		// --------------------------匹配联系人名字--------------------------

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
					// 如果photoid 大于0 表示联系人有头像 ，如果没有给此人设置头像则给他一个默认的
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
						messageInfo.setName("未知联系人");
					} else {
						messageInfo.setName("我");
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
