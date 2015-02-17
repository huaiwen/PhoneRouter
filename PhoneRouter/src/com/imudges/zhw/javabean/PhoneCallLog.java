package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobObject;

public class PhoneCallLog extends BmobObject {
   private String CallName;//联系人姓名
   private String CallDate;//记录时间
   private int CallType;//记录类型 呼入？呼出
   private String Username;//本人用户名
   public String getCallNum() {
	return CallNum;
}
public void setCallNum(String callNum) {
	CallNum = callNum;
}
private String CallNum;//记录的电话号码
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
