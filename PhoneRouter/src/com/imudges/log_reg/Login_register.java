package com.imudges.log_reg;

import cn.bmob.v3.listener.SaveListener;

import com.imudges.phonerouter.MainActivity;
import com.imudges.phonerouter.R;
import com.imudges.zhw.javabean.User;
import com.imudges.zhw.util.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_register extends ActionBarActivity {
	EditText userName;// 用户名
	EditText passWord;// 密码
	TextView forgetPassWord;// 忘记密码
	Button LogInButton;// 登陆按钮
	TextView register;// 注册按钮
	private static final String SHAREDPREFERENCES_NAME = "user_info";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_gradient_bg));
		initView();
	}

	public void initView() {
		userName = (EditText) findViewById(R.id.login_phone_edittext);
		passWord = (EditText) findViewById(R.id.login_password_edittext);
		forgetPassWord = (TextView) findViewById(R.id.login_forget_password_textview);
		LogInButton = (Button) findViewById(R.id.phone_account_login_btn);
		register = (TextView) findViewById(R.id.guide_register);

		forgetPassWord.setOnClickListener(clickListener);
		LogInButton.setOnClickListener(clickListener);
		register.setOnClickListener(clickListener);

	}

	public boolean verification() {// 验证登陆信息的合法与否
		String username = userName.getText().toString();// 用户名
		String password = passWord.getText().toString();// 密码
		if (username.length() == 0 || password.length() == 0) {// 验证是不是没有填
			MyToast.showToast(getApplicationContext(), "请填写用户名和密码！");
			return false;
		}
		return true;
	}

	public OnClickListener clickListener = new OnClickListener() {// 本页面的监听

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_forget_password_textview:// 点击了忘记密码

				break;
			case R.id.phone_account_login_btn:// 点击了登陆
				if (verification()) {// 如果不为空
					User user = new User();
					user.setUsername(userName.getText().toString());
					user.setPassword(passWord.getText().toString());
					Log.e("psw", user.getPassword());
					user.login(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							MyToast.showToast(getApplicationContext(), "登陆成功");
							Log.i("login", "chengogng");
							SharedPreferences preferences = getSharedPreferences(
									SHAREDPREFERENCES_NAME, MODE_PRIVATE);//将信息存入，以免下次再登陆
							 Editor editor = preferences.edit();
						        // 存入数据
							    editor.putBoolean("isLogIned", true);
						        editor.putString("userName", userName.getText().toString());
						        editor.putString("password",passWord.getText().toString());
						        // 提交修改
						        editor.commit();
						        Intent intent =new Intent(Login_register.this,MainActivity.class);
						        Login_register.this.startActivity(intent);
	        					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	        					Login_register.this.finish();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							MyToast.showToast(getApplicationContext(), "登陆失败！"+arg1);
							Log.i("login", "shibai");
						}
					});
				}
				break;
			case R.id.guide_register:// 点击了注册
				Intent it = new Intent(Login_register.this,
						RegisterActivity.class);
				Login_register.this.startActivity(it);

				break;
			default:
				break;
			}

		}
	};

}
