package com.imudges.zt.util;

import java.util.HashMap;
import java.util.List;

import com.imudges.ldj.utils.HDExplorerActivity;
import com.imudges.phonerouter.R;
import com.imudges.zhw.util.EnvironmentUtils;
import com.imudges.zhw.util.OnSwitchChangeListener;
import com.imudges.zt.util.WiperSwitch.OnChangedListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SetAdapter extends BaseAdapter {

	public class ZeroViewHolder {
		TextView function_sumup;// �����ܽ�
	}

	public class FirstViewHolder {

		TextView function_name;// item ����
	}

	public class SecondViewHolder {

		public TextView function_switchname;// item ����
		public TextView function_state;// item ��״̬
		public WiperSwitch function_switch;// item switch button
	}

	// ����Դ
	private List<HashMap<String, Integer>> list;// item��������,λ�� 0��
												// itemͼƬ��λ��1��item
												// ���� λ��2
	private Context context;// ������
	public EnvironmentUtils enu;// ��������

	/**
	 * item��������,λ�� 0�� itemͼƬ��λ��1��item ���� λ��2
	 * 
	 * @param context
	 *            ������
	 * @param list
	 *            item��������,λ�� 0�� itemͼƬ��λ��1��item ���� λ��2
	 */
	public SetAdapter(Context context, List<HashMap<String, Integer>> list) {
		this.context = context;
		this.list = list;
		enu = new EnvironmentUtils(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.get(0).size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(0).get(String.valueOf(position));
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		if ((list.get(2).get(String.valueOf(position)).equals(Integer
				.valueOf(0)))
				|| list.get(2).get(String.valueOf(position))
						.equals(Integer.valueOf(2)))
			return false;
		else
			return super.isEnabled(position);
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(context);
		// ����һ��View
		View view = null;
		switch ((Integer) list.get(2).get(String.valueOf(position))) {
		case 0:// 0���ͣ�ֻ��textView,�����Ե�
			view = mInflater.inflate(R.layout.zero_item, null);
			ZeroViewHolder zvh = new ZeroViewHolder();
			zvh.function_sumup = (TextView) view
					.findViewById(R.id.function_sumup);
			zvh.function_sumup.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
			break;
		case 1:// 1���ͣ�ֻ��textView ���Ե�

			view = mInflater.inflate(R.layout.first_item, null);
			FirstViewHolder fvh = new FirstViewHolder();

			fvh.function_name = (TextView) view
					.findViewById(R.id.function_name);
			fvh.function_name.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
            view.setOnClickListener(new onViewOnClickListener(position));
			break;
		case 2:// 2����  textView�� Switch Button

			view = mInflater.inflate(R.layout.second_item, null);
			SecondViewHolder svh = new SecondViewHolder();

			svh.function_switchname = (TextView) view
					.findViewById(R.id.function_switchname);
			svh.function_switchname.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
			svh.function_state = (TextView) view
					.findViewById(R.id.function_state);

			Log.e("�洢��״̬", String.valueOf(enu.isOpen(position)));
			svh.function_switch = (WiperSwitch) view
					.findViewById(R.id.function_switch);
			svh.function_switch.setState(enu.isOpen(position));//

			svh.function_state.setText((enu.isOpen(position) ? "����" : "�ر�"));
			svh.function_switch
					.setOnChangedListener(new OnSwitchChangeListener(svh,
							position,this.enu,SetAdapter.this));

			break;
		}

		return view;
	}

	

	public class onViewOnClickListener implements OnClickListener{
        public int position;
        
		public onViewOnClickListener(int position) {
			super();
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			switch (position) {
			case 5://sd ��
			Intent intent = new Intent(context,HDExplorerActivity.class);
			context.startActivity(intent);
				break;
			case 6://�ֻ�����

				break;

			default:
				break;
			}
			
			//enu.sendIntent(position);
		}	
	}
	
	
	
	
	
}