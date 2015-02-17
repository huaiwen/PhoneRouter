package com.imudges.phonerouter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.imudges.ldj.utils.HDExplorerActivity;
import com.imudges.zt.util.SetAdapter;
import com.imudges.zt.util.WiperSwitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SettingActivity extends ActionBarActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_gradient_bg));
		ListView list = (ListView) findViewById(R.id.mylist);
		// item��������,λ�� 0�� itemͼƬ��λ��1��item���� λ��2
		List<HashMap<String, Integer>> maplist = new ArrayList<HashMap<String, Integer>>();
		String[] data = { "ת��", "ת�ӵ绰", "ת�Ӷ���", "�ļ�����", "SD��", "�ֻ��洢", "״̬���",
				"����", "����", "�Ƿ���ת�ӹ���", "�˳���ǰ�˺�" };

		HashMap<String, Integer> function_name = new HashMap<String, Integer>();
		function_name.put("0", R.string.settings_position_0);
		function_name.put("1", R.string.settings_position_1);
		function_name.put("2", R.string.settings_position_2);
		function_name.put("3", R.string.settings_position_3);
		function_name.put("4", R.string.settings_position_4);
		function_name.put("5", R.string.settings_position_5);
		function_name.put("6", R.string.settings_position_6);
		function_name.put("7", R.string.settings_position_7);
		function_name.put("8", R.string.settings_position_8);
		function_name.put("9", R.string.settings_position_9);
		function_name.put("10", R.string.settings_position_10);
		// 0���ͣ�ֻ��textView
		// 1���ͣ�ֻ��textView ��ͼƬ
		// 2���� ��ͼƬ textView�� Switch Button
		HashMap<String, Integer> function_image = new HashMap<String, Integer>();
		function_image.put("0", R.drawable.ic_launcher);
		function_image.put("1", R.drawable.ic_launcher);
		function_image.put("2", R.drawable.ic_launcher);
		function_image.put("3", R.drawable.ic_launcher);
		function_image.put("4", R.drawable.ic_launcher);
		function_image.put("5", R.drawable.ic_launcher);
		function_image.put("6", R.drawable.ic_launcher);
		function_image.put("7", R.drawable.ic_launcher);
		function_image.put("8", R.drawable.ic_launcher);
		function_image.put("9", R.drawable.ic_launcher);
		function_image.put("10", R.drawable.ic_launcher);

		HashMap<String, Integer> function_type = new HashMap<String, Integer>();
		function_type.put("0", 0);
		function_type.put("1", 2);
		function_type.put("2", 2);
		function_type.put("3", 2);
		function_type.put("4", 0);
		function_type.put("5", 1);
		function_type.put("6", 1);
		function_type.put("7", 0);
		function_type.put("8", 2);
		function_type.put("9", 2);
		function_type.put("10", 1);

		maplist.add(function_name);
		maplist.add(function_image);
		maplist.add(function_type);

		SetAdapter listItemAdapter = new SetAdapter(this, maplist);
		list.setAdapter(listItemAdapter);
		
		
	}

}
