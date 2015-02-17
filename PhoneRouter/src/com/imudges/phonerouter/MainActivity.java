package com.imudges.phonerouter;

import com.imudges.lch.GuideActivity;
import com.imudges.zhw.cui.ZHWImageView;
import com.imudges.zhw.fb.SendFeedback;
import com.imudges.zhw.slee.BootService;
import com.imudges.zhw.util.PhoneStateMonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {
	
		
	ZHWImageView title;// 头衔
	ZHWImageView settings;// 设置
	ZHWImageView about;// 关于
	ZHWImageView feedback;// 反馈
	ZHWImageView function_guide;// 功能导览

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_gradient_bg));
		initView();
		initService();
	}

	public void initView() {
		title = (ZHWImageView) findViewById(R.id.title);
		settings = (ZHWImageView) findViewById(R.id.settings_zhwIv);
		about = (ZHWImageView) findViewById(R.id.about_zheIv);
		feedback = (ZHWImageView) findViewById(R.id.feedback_zhwIv);
		function_guide = (ZHWImageView) findViewById(R.id.function_zhwIv);
      
		
		
		settings.setOnClickListener(clickListener);
		feedback.setOnClickListener(clickListener);
		function_guide.setOnClickListener(clickListener);
		about.setOnClickListener(clickListener);
	}
  public void initService(){
	
		//启动初始的服务
		Intent it = new Intent(this,BootService.class);
		startService(it);
  }
	public OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.settings_zhwIv:
				intent = new Intent(MainActivity.this, SettingActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.about_zheIv:
				intent = new Intent(MainActivity.this, AboutActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.feedback_zhwIv:
				intent = new Intent(MainActivity.this, SendFeedback.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.function_zhwIv:
				intent = new Intent(MainActivity.this, GuideActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			

			default:
				break;
			}
		}
	};

}