package com.imudges.zhw.slee;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import com.imudges.zhw.util.PhoneUtils;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

public class PhoneListener extends PhoneStateListener {
	File audioFile;
	MediaRecorder mediaRecorder, mMediaRecorder; // = new MediaRecorder();
	MediaRecorder recorder;
	Context c;
	String mAudioPath;
	//
	boolean iscall = false;

	//
	public PhoneListener(Context context) {
		c = context;
		iscall = false;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		
		switch (state) {
		case TelephonyManager.CALL_STATE_OFFHOOK:// 接通
			iscall = true;
			Log.i("电话接通", iscall + "iscall");
			startTranscribeAudio();
			/*try {
				recordCallComment();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("CALL_STATE_OFFHOOK", "抛异常了");
				mediaRecorder.stop();
			}*/
			Toast.makeText(c, "开启录音", Toast.LENGTH_SHORT).show();
			break;
		case TelephonyManager.CALL_STATE_RINGING:// 响铃
		
			Log.i("挂了", "挂了haha");
			/*
			 * try { recordCallComment();
			 * 
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); mediaRecorder.stop(); } Toast.makeText(c,
			 * "开启录音", Toast.LENGTH_SHORT).show();
			 */
			break;
		case TelephonyManager.CALL_STATE_IDLE:// 空闲
			/*
			 * if(mediaRecorder!=null){ mediaRecorder.stop();
			 * mediaRecorder=null; }
			 */
			Log.i("电话空闲", iscall + "iscall");
			if (iscall) {
				/*recorder.stop();
				recorder.reset(); // You can reuse the object by going back to
									// setAudioSource() step
				recorder.release(); // Now the object cannot be reused
				// mediaRecorder.stop();
*/				stopTranscribeAudio();
				iscall = false;
			}
			break;
		}
	}

	private void startTranscribeAudio() {
		// 文件路径
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory().getPath())
				.append("/").append("atestaudio");
		String path = builder.toString();

		File folder = new File(path);
		// 文件夹不存在则创建
		if (!folder.exists())
			folder.mkdirs();

		mAudioPath = path
				+ "/"
				+ new DateFormat().format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA)) + ".amr";
		File file = new File(mAudioPath);

		// 创建录音对象
		mMediaRecorder = new MediaRecorder();
		// 从麦克风源进行录音
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		// 设置输出格式
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// 设置编码格式
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// 设置输出文件
		mMediaRecorder.setOutputFile(file.getAbsolutePath());

		try {
			// 创建文件
			file.createNewFile();
			// 准备录制
			mMediaRecorder.prepare();
			// 开始录制
			mMediaRecorder.start();
		} catch (Exception e) {
		}
	}

	private void stopTranscribeAudio() {
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
			
		}
	}

	//
	public void recordCallComment() throws IOException {

		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(Environment.getExternalStorageDirectory()
				.getPath() + "/record_.amr");
		recorder.prepare();
		recorder.start();

		/*
		 * System.out.println(mediaRecorder);
		 * mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		 * mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		 * mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		 * audioFile = File.createTempFile("record_", ".amr");
		 * mediaRecorder.setOutputFile(audioFile.getAbsolutePath()); Log.e("路径",
		 * audioFile.getAbsolutePath()); mediaRecorder.prepare();
		 * mediaRecorder.start();
		 */
		Log.i("电话接通", "recordCallComment完成");
	}

}
