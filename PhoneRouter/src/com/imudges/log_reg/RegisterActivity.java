package com.imudges.log_reg;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.imudges.lch.LaunchActivity;
import com.imudges.phonerouter.MainActivity;
import com.imudges.phonerouter.R;
import com.imudges.phonerouter.R.layout;
import com.imudges.zhw.javabean.User;
import com.imudges.zhw.util.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends ActionBarActivity {
	EditText userName;// �û���
	EditText email;// ����
	EditText password;// ����
	EditText repassword;// �ظ�����

	CheckBox allowTerms;// ѡ���Ƿ���ѭЭ��
	Button registerButton;// ע�ᰴť
	TextView terms;// Э������

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_gradient_bg));
		initView();
	}

	public void initView() {
		userName = (EditText) findViewById(R.id.register_account);
		email = (EditText) findViewById(R.id.register_email);
		password = (EditText) findViewById(R.id.register_account_password);
		repassword = (EditText) findViewById(R.id.register_account_password_twice);

		registerButton = (Button) findViewById(R.id.phone_account_register_btn);
		allowTerms = (CheckBox) findViewById(R.id.guide_phone_check_checkbox);
		terms = (TextView) findViewById(R.id.register_input_phone_terms);

		registerButton.setOnClickListener(clickListener);
		terms.setOnClickListener(clickListener);

		password.setOnTouchListener(new View.OnTouchListener() {// �����Ĵ����¼�
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String username = userName.getText().toString();
				String emailAddress = email.getText().toString();
				if (username.length() != 0 && emailAddress.length() != 0) {
					repassword.setVisibility(View.VISIBLE);
				} else {
					MyToast.showToast(getApplicationContext(), "����д�û��������䣡");
				}
				return false;
			}
		});

	}

	public boolean verification() {
		String username = userName.getText().toString();
		String emailaddress = email.getText().toString();
		String psw = password.getText().toString();
		String repsw = repassword.getText().toString();

		if (username.length() != 0 && emailaddress.length() != 0
				&& psw.equals(repsw)) {
			return true;
		}

		return false;

	}

	public OnClickListener clickListener = new OnClickListener() {// ��ҳ�ļ���

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.phone_account_register_btn:// �����ע�ᰴť
				if (verification()) {
					if (!allowTerms.isChecked()) {
						MyToast.showToast(getApplicationContext(), "���Ķ�����ѡЭ�飡");
						return;
					}
					final User myUser = new User();
					myUser.setUsername(userName.getText().toString());
					myUser.setPassword(password.getText().toString());
					myUser.setEmail(email.getText().toString());
					myUser.signUp(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							MyToast.showToast(
									getApplicationContext(),
									myUser.getUsername() + " "
											+ myUser.getEmail() + " "
											+ myUser.getPassword());
							Log.i("reg", "chengogng");
							Intent intent = new Intent(RegisterActivity.this,
									MainActivity.class);
							RegisterActivity.this.startActivity(intent);
							overridePendingTransition(android.R.anim.fade_in,
									android.R.anim.fade_out);
							RegisterActivity.this.finish();
							MyToast.showToast(getApplicationContext(),
									"ע��ɹ�������½��");
							BmobInstallation installation = BmobInstallation
									.getCurrentInstallation(getApplicationContext());
							installation.subscribe(myUser.getUsername());
							installation.save();

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							MyToast.showToast(getApplicationContext(), "ע��ʧ��"
									+ arg1);
							Log.i("reg", "shibai");
						}
					});
				} else {
					MyToast.showToast(getApplicationContext(), "�뽫ע����Ϣ��д��ȷ��");
				}
				break;
			case R.id.register_input_phone_terms:// �����Э������
				Intent it = new Intent(RegisterActivity.this,
						TermsActivity.class);
				RegisterActivity.this.startActivity(it);
				break;

			default:
				break;
			}

		}
	};
}
