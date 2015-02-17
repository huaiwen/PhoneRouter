package com.imudges.ldj.servse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.imudges.phonerouter.R;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GetSIMinfo extends Service {
	// ����Listview����
	ListView showView;
	// ��������״̬��������
	String[] statusNames;
	// ���������ֻ�״̬�ļ���
	ArrayList<String> statusValues = new ArrayList<String>();

	@Override
	public void onCreate() {
		super.onCreate();
		// setContentView(R.layout.activity_main);
		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		statusNames = getResources().getStringArray(R.array.statusNames);
		String[] simState = getResources().getStringArray(R.array.simState);
		// ��ȡ����绰�������͵�����
		String[] phoneType = getResources().getStringArray(R.array.phoneType);

		// ��ȡ�豸���
		statusValues.add(tManager.getDeviceId());

		// ��ȡϵͳƽ̨�İ汾
		statusValues.add(tManager.getDeviceSoftwareVersion() != null ? tManager
				.getDeviceSoftwareVersion() : "δ֪");

		// ��ȡ������Ӫ�̴���
		statusValues.add(tManager.getNetworkOperator());

		// ��ȡ������Ӫ������
		statusValues.add(tManager.getNetworkOperatorName());

		// ��ȡ�ֻ���������
		statusValues.add(phoneType[tManager.getPhoneType()]);

		// ��ȡ�豸����δ֪
		statusValues.add(tManager.getCellLocation() != null ? tManager
				.getCellLocation().toString() : "δ֪λ��");

		// ��ȡSIM���Ĺ���
		statusValues.add(tManager.getSimCountryIso());

		// ��ȡSIM��״̬
		statusValues.add(simState[tManager.getSimState()]);

		// ���ListView����

		ArrayList<Map<String, String>> status = new ArrayList<Map<String, String>>();
		// ����statusValues���ϣ���statusNames��statusValues
		// �����ݷ�װ��List<Map<String , String>>������
		for (int i = 0; i < statusValues.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", statusNames[i]);
			map.put("value", statusValues.get(i));
			status.add(map);
		}
		// ʹ��SimpleAdapter��װList����
		SimpleAdapter adapter = new SimpleAdapter(this, status, R.layout.simline,
				new String[] { "name", "value" }, new int[] { R.id.name,
						R.id.value });
		// ΪListView����Adapter
		//showView.setAdapter(adapter);
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
