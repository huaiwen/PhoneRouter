package com.imudges.zhw.util;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class MyToast {

	private static Toast toast;

	public static void showToast(Context context, String msg) {

		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	public static void showThreadToast(Context context, String msg) {//在非UI线程中

		if (toast == null) {

			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

		} else {
			toast.setText(msg);
		}
		Looper.prepare();
		toast.show();
		Looper.loop();
	}

	public static void showToast(Context context, int resId) {

		if (toast == null) {
			toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}

	public static void showToastLong(Context context, int resId) {

		if (toast == null) {
			toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}
}
