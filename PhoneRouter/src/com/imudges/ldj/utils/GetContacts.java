package com.imudges.ldj.utils;
/*
 此类实现读取联系人信息的功能，
 调用getPhoneContacts即可(该函数会读取手机中的联系人信息，非SIM卡)，
 执行后信息在各ArrayList中存储
 */
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
	/*
	 * 第97行是对无头像的用户设置默认头像，需要进行设置
	 */
public class GetContacts{


	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private static final int PHONES_NUMBER_INDEX = 1;
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** 此数组存储联系人名称 **/
	public ArrayList<String> mContactsName = new ArrayList<String>();

	/** 此数组存储联系人电话 **/
	public ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** 此数组存储联系人头像 **/
	public ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
	//他们按下标一一对应


	public void getPhoneContacts(Context mContext) {
		ContentResolver resolver = mContext.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;

				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} /*
				else对无头像的用户设置默认头像
				*/
				else {
					contactPhoto = BitmapFactory.decodeResource(null, 0);
				}
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
				mContactsPhonto.add(contactPhoto);
			}
			phoneCursor.close();
		}
	}

	private void getSIMContacts(Context mContext) {
		ContentResolver resolver = mContext.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// Sim卡中没有联系人头像
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
			}
			phoneCursor.close();
		}
	}
	
	
	
	
}

