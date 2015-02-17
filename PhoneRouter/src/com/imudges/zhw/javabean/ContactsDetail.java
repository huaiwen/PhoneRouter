package com.imudges.zhw.javabean;

import android.util.Log;
import cn.bmob.v3.BmobObject;

public class ContactsDetail extends BmobObject {
	private String ContactName;// 联系人名字
	private String ContactNumber;// 联系人号码
	//private Bitmap ContactPhonto;// 联系人头像
	private String UserName;// 用户名

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	/*public Bitmap getContactPhonto() {
		return ContactPhonto;
	}

	public void setContactPhonto(Bitmap contactPhonto) {
		ContactPhonto = contactPhonto;
	}*/

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	@Override
	public String toString() {
		return "Contacts [ContactName=" + ContactName + ", ContactNumber="
				+ ContactNumber + ", UserName=" + UserName + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this.ContactName.equals(((ContactsDetail) o).getContactName())
				&& this.ContactNumber.equals(((ContactsDetail) o)
						.getContactNumber())
				&& this.UserName.equals(((ContactsDetail) o).getUserName())) {
			Log.e("equals", "一样了，不加");
			return true;
		}
		Log.e("equals", "不一样");
		return false;
	}

}
