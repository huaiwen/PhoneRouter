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
		
		
		setTitle("发送反馈");
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
				Toast.makeText(this, "请勿重复提交反馈", Toast.LENGTH_SHORT).show();
			} else {
				msg = content;
				// 发送反馈信息
				sendMessage(content, contactnumber);
				Toast.makeText(this, "感谢您的支持，您的反馈信息已发送！", Toast.LENGTH_SHORT).show();
				contact.setText("");
				et_content.setText("");
			}
		} else {
			Toast.makeText(this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 发送反馈信息给开发者
	 * 
	 * @param message
	 *            反馈信息
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
	 * 保存反馈信息到服务器
	 * 
	 * @param msg
	 *            反馈信息
	 */
	private void saveFeedbackMsg(String msg, String contact) {
		Feedback feedback = new Feedback();
		feedback.setContent(msg);
		feedback.setContacts(contact);
		feedback.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("bmob", "反馈信息已保存到服务器");
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				Log.e("bmob", "保存反馈信息失败：" + arg0);
			}
		});
	}

}
