package com.imudges.ldj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

/*
 * 此类用于获取号码归属地
 *使用时调用lookup方法传入测试号码，为String类型，
 *该方法执行后
 调用gainres()的返回值最后得到的结果，为String类型
 */
public class PhoneNumberAttrbution {
	public String res;//结果
	boolean judge = true;// 用于判断手机号码格式正确

	public void lookup(final String tele) {
		/*
		 * 这里的tele为所测试号码
		 */
		if ("".equals(tele) || tele.length() < 7) {
			judge = false;
			/*
			 * 如果号码不符合手机号码标准则提示错误//dosomething
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
						System.out.println("输出信息");
						System.out.println(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 关闭连接和输入流
					con.disconnect();
					System.out.println("连接已关闭");
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
