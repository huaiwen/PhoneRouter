package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobObject;

public class PhoneCallLog extends BmobObject {
   private String CallName;//��ϵ������
   private String CallDate;//��¼ʱ��
   private int CallType;//��¼���� ���룿����
   private String Username;//�����û���
   public String getCallNum() {
	return CallNum;
}
public void setCallNum(String callNum) {
	CallNum = callNum;
}
private String CallNum;//��¼�ĵ绰����
public String getCallName() {
	return CallName;
}
public void setCallName(String callName) {
	CallName = callName;
}
public String getCallDate() {
	return CallDate;
}
public void setCallDate(String callDate) {
	CallDate = callDate;
}
public int getCallType() {
	return CallType;
}
public void setCallType(int callType) {
	CallType = callType;
}
public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
@Override
public String toString() {
	return "PhoneCallLog [CallName=" + CallName + ", CallDate=" + CallDate
			+ ", CallType=" + CallType + ", Username=" + Username
			+ ", CallNum=" + CallNum + "]";
}

   
}
