package com.imudges.lch;

import java.util.ArrayList;
import java.util.List;

import com.imudges.log_reg.Login_register;
import com.imudges.phonerouter.MainActivity;
import com.imudges.phonerouter.R;
import com.imudges.phonerouter.R.layout;
import com.imudges.zhw.javabean.User;
import com.imudges.zhw.push.MyPushMessageReceiver;
import com.imudges.zhw.util.MyToast;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LaunchActivity extends Activity {

	boolean isFirstIn = false;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟三秒
	private static final long SPLASH_DELAY_MILLIS = 3000;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private Handler mHandler = new Handler() {
		// 用Handler跳转到不同的界面
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bmob.initialize(getApplicationContext(),
				"bbb2d6450fbd217ae3253342ad55bc3a");// bmob初始化
		BmobInstallation.getCurrentInstallation(this).save();
		BmobPush.startWork(getApplicationContext(),
				"bbb2d6450fbd217ae3253342ad55bc3a");// bmob推送初始化
		MyPushMessageReceiver re = new MyPushMessageReceiver();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_homepage);
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}
	}

	// 进入引导界面
	protected void goGuide() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LaunchActivity.this, GuideActivity.class);
		LaunchActivity.this.startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		LaunchActivity.this.finish();
	}

	// 进入扥路界面
	protected void goHome() {
		SharedPreferences preferences = getSharedPreferences("user_info",
				MODE_PRIVATE);
		boolean isLogIned = preferences.getBoolean("isLogIned", false);
		List<String> li = new ArrayList<String>();
        li.add(preferences.getString("userName", ""));
		
		
		if (isLogIned) {// &&user.getUsername()!=null&&user.getPassword()!=null
			User user = new User();
			user.setUsername(preferences.getString("userName", ""));
			user.setPassword(preferences.getString("password", ""));
			Log.e("launch", user.getUsername() + " " + user.getPassword()
					+ "wo qu ");
			if (user.getUsername() == null || user.getPassword() == null) {
				return;
			}
			user.login(getApplicationContext(), new SaveListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					MyToast.showToast(getApplicationContext(), "欢迎回来！");
					Intent intent = new Intent(LaunchActivity.this,
							MainActivity.class);
					LaunchActivity.this.startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
				
					LaunchActivity.this.finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					MyToast.showToast(getApplicationContext(), "登陆失败！" + arg1);
					Intent intent = new Intent(LaunchActivity.this,
							Login_register.class);
					LaunchActivity.this.startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
					LaunchActivity.this.finish();
				}
			});
		} else {
			Intent intent = new Intent(LaunchActivity.this,
					Login_register.class);
			LaunchActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			LaunchActivity.this.finish();
		}
	}

}
