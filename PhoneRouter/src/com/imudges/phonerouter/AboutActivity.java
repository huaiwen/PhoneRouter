package com.imudges.phonerouter;

import java.util.List;
import com.imudges.zhw.javabean.TaskQueue;
import com.imudges.zhw.util.MyToast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	private ImageView logo_image;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		logo_image = (ImageView) findViewById(R.id.logo_image);
		logo_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BmobQuery<TaskQueue> query = new BmobQuery<TaskQueue>();
				query.addWhereContains("username",
						BmobUser.getCurrentUser(getApplicationContext()).getUsername());
				query.findObjects(AboutActivity.this,
						new FindListener<TaskQueue>() {
							@Override
							public void onSuccess(List<TaskQueue> object) {
								// TODO Auto-generated method stub
								MyToast.showToast(AboutActivity.this, "查询成功：共"
										+ object.size() + "条数据。");
								for (TaskQueue taskQueue : object) {
									MyToast.showToast(AboutActivity.this,
											taskQueue.getBacklog());
								}
							}

							@Override
							public void onError(int code, String msg) {
								// TODO Auto-generated method stub
								MyToast.showToast(AboutActivity.this, "查询失败："
										+ msg);
							}
						});
			}
		});
	}

}
