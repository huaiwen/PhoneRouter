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
		svh.function_state.setText((CheckState ? "����" : "�ر�"));
		enu.setOpen(position, CheckState);
		if(position==1&&!CheckState){//��� �ܿ��ع��ˣ�ת�ӵ绰�Ͷ��ŵĶ�Ҫ��
			enu.setOpen(position+1, CheckState);
			enu.setOpen(position+2, CheckState);
			sa.notifyDataSetChanged();
		}else if(position==1&&CheckState){//����ܿ��ر�����Ϊ��������ôת�ӵ绰�Ͷ��Ŷ�Ҫ��
			enu.setOpen(position+1, CheckState);
			enu.setOpen(position+2, CheckState);
			sa.notifyDataSetChanged();
		}
		
		if(position==2||position==3){//������� ת�ӵ绰����ţ�Ҫ����ܿ��أ���һ�������ܿ��ؾͿ����������أ��ܿ���ҲҪ��
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