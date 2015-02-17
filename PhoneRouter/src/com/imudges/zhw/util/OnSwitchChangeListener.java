package com.imudges.zhw.util;

import com.imudges.zt.util.SetAdapter;
import com.imudges.zt.util.SetAdapter.SecondViewHolder;
import com.imudges.zt.util.WiperSwitch.OnChangedListener;

public class OnSwitchChangeListener implements OnChangedListener {
	public SecondViewHolder svh;
	public int position;
	public EnvironmentUtils enu;
	public SetAdapter sa;
	public OnSwitchChangeListener(SecondViewHolder svh, int position,EnvironmentUtils enu,SetAdapter sa) {
		this.svh = svh;
		this.position = position;
		this.enu = enu;
		this.sa = sa;
	}
	@Override
	public void OnChanged(boolean CheckState) {
		svh.function_state.setText((CheckState ? "开启" : "关闭"));
		enu.setOpen(position, CheckState);
		if(position==1&&!CheckState){//如果 总开关关了，转接电话和短信的都要关
			enu.setOpen(position+1, CheckState);
			enu.setOpen(position+2, CheckState);
			sa.notifyDataSetChanged();
		}else if(position==1&&CheckState){//如果总开关被设置为开启，那么转接电话和短信都要开
			enu.setOpen(position+1, CheckState);
			enu.setOpen(position+2, CheckState);
			sa.notifyDataSetChanged();
		}
		
		if(position==2||position==3){//如果设置 转接电话或短信，要检查总开关，有一个开，总开关就开，两个都关，总开关也要关
			enu.setOpen(position, CheckState);
			if(enu.isOpen(2)||enu.isOpen(3)){
				enu.setOpen(1, true);
			}else{
				enu.setOpen(1, false);
			}
			sa.notifyDataSetChanged();
		}
	}
}