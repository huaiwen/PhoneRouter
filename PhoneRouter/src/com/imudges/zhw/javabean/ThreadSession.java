package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobObject;

public class ThreadSession extends BmobObject{
	private String Contact;//联系人姓名
	private String last_date;//最后的日期
	private String last_msg;//最后一条短信
	private int msg_count;//这个会话一共有多少短信
	private String username;//本用户是谁
	private int threadId; //这个会话的id
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String getLast_msg() {
		return last_msg;
	}
	public void setLast_msg(String last_msg) {
		this.last_msg = last_msg;
	}
	public int getMsg_count() {
		return msg_count;
	}
	public void setMsg_count(int msg_count) {
		this.msg_count = msg_count;
	}
	@Override
	public String toString() {
		return "ThreadSession [Contact=" + Contact + ", last_date=" + last_date
				+ ", last_msg=" + last_msg + ", msg_count=" + msg_count
				+ ", username=" + username + ", threadId=" + threadId + "]";
	}
	
	
}
