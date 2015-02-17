package com.imudges.zhw.javabean;

import android.graphics.Bitmap;
import cn.bmob.v3.BmobObject;

public class MessageInfo extends BmobObject{
      private String SmsContent;//短信内容
      private String SmsDate;//短信日期
      private String Name;//联系人名称
      private Bitmap ContactPhoto;//联系人图片
      private String ContactPhoneNumber;//联系人电话号码
      private int Type;//类型 1:收件箱，2 发件箱
      private String UserName;//本机用户名
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getSmsContent() {
		return SmsContent;
	}
	public void setSmsContent(String smsContent) {
		SmsContent = smsContent;
	}
	public String getSmsDate() {
		return SmsDate;
	}
	public void setSmsDate(String smsDate) {
		SmsDate = smsDate;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Bitmap getContactPhoto() {
		return ContactPhoto;
	}
	public void setContactPhoto(Bitmap contactPhoto) {
		ContactPhoto = contactPhoto;
	}
	public String getContactPhoneNumber() {
		return ContactPhoneNumber;
	}
	public void setContactPhoneNumber(String contactPhoneNumber) {
		ContactPhoneNumber = contactPhoneNumber;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	@Override
	public String toString() {
		return "MessageInfo [SmsContent=" + SmsContent + ", SmsDate=" + SmsDate
				+ ", Name=" + Name + ", ContactPhoto=" + ContactPhoto
				+ ", ContactPhoneNumber=" + ContactPhoneNumber + ", Type="
				+ Type + ", UserName=" + UserName + "]";
	}
	
      
}
