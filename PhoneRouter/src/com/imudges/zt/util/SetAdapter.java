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
		TextView function_sumup;// 类型总结
	}

	public class FirstViewHolder {

		TextView function_name;// item 名称
	}

	public class SecondViewHolder {

		public TextView function_switchname;// item 名称
		public TextView function_state;// item 的状态
		public WiperSwitch function_switch;// item switch button
	}

	// 数据源
	private List<HashMap<String, Integer>> list;// item文字描述,位置 0，
												// item图片，位置1，item
												// 类型 位置2
	private Context context;// 上下文
	public EnvironmentUtils enu;// 环境变量

	/**
	 * item文字描述,位置 0， item图片，位置1，item 类型 位置2
	 * 
	 * @param context
	 *            上下文
	 * @param list
	 *            item文字描述,位置 0， item图片，位置1，item 类型 位置2
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
		// 产生一个View
		View view = null;
		switch ((Integer) list.get(2).get(String.valueOf(position))) {
		case 0:// 0类型，只有textView,不可以点
			view = mInflater.inflate(R.layout.zero_item, null);
			ZeroViewHolder zvh = new ZeroViewHolder();
			zvh.function_sumup = (TextView) view
					.findViewById(R.id.function_sumup);
			zvh.function_sumup.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
			break;
		case 1:// 1类型，只有textView 可以点

			view = mInflater.inflate(R.layout.first_item, null);
			FirstViewHolder fvh = new FirstViewHolder();

			fvh.function_name = (TextView) view
					.findViewById(R.id.function_name);
			fvh.function_name.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
            view.setOnClickListener(new onViewOnClickListener(position));
			break;
		case 2:// 2类型  textView和 Switch Button

			view = mInflater.inflate(R.layout.second_item, null);
			SecondViewHolder svh = new SecondViewHolder();

			svh.function_switchname = (TextView) view
					.findViewById(R.id.function_switchname);
			svh.function_switchname.setText(context.getResources().getString(
					list.get(0).get(String.valueOf(position))));
			svh.function_state = (TextView) view
					.findViewById(R.id.function_state);

			Log.e("存储的状态", String.valueOf(enu.isOpen(position)));
			svh.function_switch = (WiperSwitch) view
					.findViewById(R.id.function_switch);
			svh.function_switch.setState(enu.isOpen(position));//

			svh.function_state.setText((enu.isOpen(position) ? "开启" : "关闭"));
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
			case 5://sd 卡
			Intent intent = new Intent(context,HDExplorerActivity.class);
			context.startActivity(intent);
				break;
			case 6://手机储存

				break;

			default:
				break;
			}
			
			//enu.sendIntent(position);
		}	
	}
	
	
	
	
	
}