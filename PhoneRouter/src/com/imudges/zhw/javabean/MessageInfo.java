package com.imudges.zhw.javabean;

import android.graphics.Bitmap;
import cn.bmob.v3.BmobObject;

public class MessageInfo extends BmobObject{
      private String SmsContent;//��������
      private String SmsDate;//��������
      private String Name;//��ϵ������
      private Bitmap ContactPhoto;//��ϵ��ͼƬ
      private String ContactPhoneNumber;//��ϵ�˵绰����
      private int Type;//���� 1:�ռ��䣬2 ������
      private String UserName;//�����û���
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
