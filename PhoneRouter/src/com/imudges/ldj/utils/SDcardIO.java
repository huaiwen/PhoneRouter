package com.imudges.ldj.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;
/*该类不需手动调用*/
public class SDcardIO {

	public SDcardIO() {
		// TODO Auto-generated constructor stub
	}

	public static boolean saveToDisk(String fileName, byte[] data) {
		boolean flag = false;
		File file = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(new File(file, fileName));
				outputStream.write(data, 0, data.length);
				flag = true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
		}
		return flag;
	}

}
