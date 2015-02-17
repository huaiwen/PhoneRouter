package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
     //用户名 密码 邮箱 已经默认了
	private String isVip;//是vip么 是Y，否N
	private String phoneNamber;//电话号码
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
