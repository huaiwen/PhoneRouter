package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
     //�û��� ���� ���� �Ѿ�Ĭ����
	private String isVip;//��vipô ��Y����N
	private String phoneNamber;//�绰����
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getPhoneNamber() {
		return phoneNamber;
	}
	public void setPhoneNamber(String phoneNamber) {
		this.phoneNamber = phoneNamber;
	}
	

}
