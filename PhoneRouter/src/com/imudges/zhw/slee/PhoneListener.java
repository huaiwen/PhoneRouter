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
		case TelephonyManager.CALL_STATE_OFFHOOK:// ��ͨ
			iscall = true;
			Log.i("�绰��ͨ", iscall + "iscall");
			startTranscribeAudio();
			/*try {
				recordCallComment();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("CALL_STATE_OFFHOOK", "���쳣��");
				mediaRecorder.stop();
			}*/
			Toast.makeText(c, "����¼��", Toast.LENGTH_SHORT).show();
			break;
		case TelephonyManager.CALL_STATE_RINGING:// ����
		
			Log.i("����", "����haha");
			/*
			 * try { recordCallComment();
			 * 
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); mediaRecorder.stop(); } Toast.makeText(c,
			 * "����¼��", Toast.LENGTH_SHORT).show();
			 */
			break;
		case TelephonyManager.CALL_STATE_IDLE:// ����
			/*
			 * if(mediaRecorder!=null){ mediaRecorder.stop();
			 * mediaRecorder=null; }
			 */
			Log.i("�绰����", iscall + "iscall");
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
		// �ļ�·��
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory().getPath())
				.append("/").append("atestaudio");
		String path = builder.toString();

		File folder = new File(path);
		// �ļ��в������򴴽�
		if (!folder.exists())
			folder.mkdirs();

		mAudioPath = path
				+ "/"
				+ new DateFormat().format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA)) + ".amr";
		File file = new File(mAudioPath);

		// ����¼������
		mMediaRecorder = new MediaRecorder();
		// ����˷�Դ����¼��
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		// ���������ʽ
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// ���ñ����ʽ
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// ��������ļ�
		mMediaRecorder.setOutputFile(file.getAbsolutePath());

		try {
			// �����ļ�
			file.createNewFile();
			// ׼��¼��
			mMediaRecorder.prepare();
			// ��ʼ¼��
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
		 * mediaRecorder.setOutputFile(audioFile.getAbsolutePath()); Log.e("·��",
		 * audioFile.getAbsolutePath()); mediaRecorder.prepare();
		 * mediaRecorder.start();
		 */
		Log.i("�绰��ͨ", "recordCallComment���");
	}

}
