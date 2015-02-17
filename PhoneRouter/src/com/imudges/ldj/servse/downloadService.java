package com.imudges.ldj.servse;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.imudges.ldj.utils.SDcardIO;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;
/*
 * �ж��Ƿ�������internet,�Ҳ��õķ���Ϊ������service����ʱ�����ذٶȵ�LOGOͼƬ�����������ȵ�ʱ��������3�������
 * 1.�����ȵ㣬���ܳɹ����������ͼƬ�����سɹ���
 * 2.�����ȵ㣬����Ҫ������֤���û������¼���������ӷ�ʽ�������������ͼƬ��С����38B��
 * 3.�����ȵ㣬���������������ͼƬ��������
 */
public class downloadService extends Service {

	private final String IMAGE_PATH = "http://www.baidu.com/img/bdlogo.gif";

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				stopSelf();
				Toast.makeText(getApplicationContext(), "�ɹ�������INTERNET", 1).show();
			}
			if (msg.what == 2) {
				Toast.makeText(getApplicationContext(), "���Ǹ��и��ӻ��Ƶ���·����(��������֤)", 1).show();
			}
			if (msg.what == 3){
				Toast.makeText(getApplicationContext(), "�޷�������INTERNET", 1).show();
			}
		}
	};

	 public downloadService() {
		// TODO Auto-generated constructor stub
	}
		
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(IMAGE_PATH);
			try {
				HttpResponse response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() == 200) {
					byte[] result = EntityUtils.toByteArray(response
							.getEntity());
					boolean flag = SDcardIO.saveToDisk("IMG_from_test.gif", result);
					if (flag) {
						// Message message = Message.obtain();
						// message.what = 1;
						File df = new File("/sdcard/IMG_from_test.gif");
						FileInputStream fis = new FileInputStream(df);
						int fileLen = fis.available();
						if(fileLen <= 38)
							handler.sendEmptyMessage(2);
						else{
							handler.sendEmptyMessage(1);
						}		
					}	
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				handler.sendEmptyMessage(3);
				System.out.println("------>");
			}
		}
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new MyThread()).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
