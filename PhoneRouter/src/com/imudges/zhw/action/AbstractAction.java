package com.imudges.zhw.action;

import com.imudges.zhw.util.EnvironmentUtils;

import android.content.Context;

public abstract class AbstractAction {
	public static String aid = null;
	public static String command = null;
	protected String[] params = null;
	protected EnvironmentUtils env = null;
	
	public abstract void doAction();
	
	
	
	public AbstractAction(EnvironmentUtils env, Context context,String[] params){
		this.env = env;
		this.params = params;
	}
	
}
