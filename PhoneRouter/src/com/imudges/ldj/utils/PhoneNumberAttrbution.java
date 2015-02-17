package com.imudges.ldj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

/*
 * �������ڻ�ȡ���������
 *ʹ��ʱ����lookup����������Ժ��룬ΪString���ͣ�
 *�÷���ִ�к�
 ����gainres()�ķ���ֵ���õ��Ľ����ΪString����
 */
public class PhoneNumberAttrbution {
	public String res;//���
	boolean judge = true;// �����ж��ֻ������ʽ��ȷ

	public void lookup(final String tele) {
		/*
		 * �����teleΪ�����Ժ���
		 */
		if ("".equals(tele) || tele.length() < 7) {
			judge = false;
			/*
			 * ������벻�����ֻ������׼����ʾ����//dosomething
			 */
		}
		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				HttpURLConnection con = null;
				InputStream in = null;
				String result = "";
				try {

					String addr = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode="
							+ tele + "&userID=";
					URL url = new URL(addr);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("Connection", "keep-alive");
					con.setDoInput(true);// can read the data download form the
											// internet
					int code = con.getResponseCode();
					if (code == 200) {
						in = con.getInputStream();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(in));
						// String result = "";
						String line = "";
						while ((line = reader.readLine()) != null) {
							result += line;
						}
						System.out.println("�����Ϣ");
						System.out.println(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// �ر����Ӻ�������
					con.disconnect();
					System.out.println("�����ѹر�");
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				if (judge == false) {
					res = " ";
				} else {
					res = filterHtml(result);
				}
			}

		}.execute(tele);
	}

	private String filterHtml(String source) {
		if (null == source) {
			return null;
		}
		return source.replaceAll("</?[^>]+>", "").trim();
	}
	
	public String gainres(){
		return res;
	}

}
