package com.imudges.zhw.javabean;

import cn.bmob.v3.BmobObject;

public class TaskQueue extends BmobObject{
	//�û���
    private String username;
    //��������
    private String backlog;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBacklog() {
		return backlog;
	}
	public void setBacklog(String backlog) {
		this.backlog = backlog;
	}
    
}
