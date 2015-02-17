package com.imudges.zhw.fb;

import com.imudges.phonerouter.R;
import com.imudges.zhw.javabean.Feedback;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class SendFeedback extends ActionBarActivity implements OnClickListener {

	EditText et_content;
	EditText contact;
	static String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendfeedback);
		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_gradient_bg));
		
		
		setTitle("���ͷ���");
		contact = (EditText) findViewById(R.id.contact);
		et_content = (EditText) findViewById(R.id.et_content);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String content = et_content.getText().toString();
		String contactnumber = contact.getText().toString();
		if (!TextUtils.isEmpty(content)) {
			if (content.equals(msg)) {
				Toast.makeText(this, "�����ظ��ύ����", Toast.LENGTH_SHORT).show();
			} else {
				msg = content;
				// ���ͷ�����Ϣ
				sendMessage(content, contactnumber);
				Toast.makeText(this, "��л����֧�֣����ķ�����Ϣ�ѷ��ͣ�", Toast.LENGTH_SHORT).show();
				contact.setText("");
				et_content.setText("");
			}
		} else {
			Toast.makeText(this, "����д��������", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ���ͷ�����Ϣ��������
	 * 
	 * @param message
	 *            ������Ϣ
	 */
	private void sendMessage(String message, String contact) {
		BmobPushManager bmobPush = new BmobPushManager(this);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("isDeveloper", true);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);

		saveFeedbackMsg(message, contact);
	}

	/**
	 * ���淴����Ϣ��������
	 * 
	 * @param msg
	 *            ������Ϣ
	 */
	private void saveFeedbackMsg(String msg, String contact) {
		Feedback feedback = new Feedback();
		feedback.setContent(msg);
		feedback.setContacts(contact);
		feedback.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("bmob", "������Ϣ�ѱ��浽������");
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				Log.e("bmob", "���淴����Ϣʧ�ܣ�" + arg0);
			}
		});
	}

}
