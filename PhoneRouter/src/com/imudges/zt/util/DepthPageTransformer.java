package com.imudges.zt.util;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {

	private static float MIN_SCALE = 0.75f;
	@Override
	public void transformPage(View view, float position) {
		// TODO Auto-generated method stub
		int pageWidth = view.getWidth();
		
		if(position < -1){// [-Infinity,-1)
			//向左滑动出现页面
			view.setAlpha(0);
		} else if (position <= 0) {// [-1,0]
			//向左滑动页面
			view.setAlpha(1);
			view.setTranslationX(0);
			view.setScaleX(1);
			view.setScaleY(1);
		} else if (position <= 1) {// (0,1]
			//页面消失
			view.setAlpha(1 - position);
			view.setTranslationX(pageWidth * -position);
			//页面的规模在MIN_SCALE到1的范围内
			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
		} else {// (1,+Infinity]
			//页面离屏
			view.setAlpha(0);
		}
	}

}
