package com.imudges.lch;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

import com.imudges.log_reg.Login_register;
import com.imudges.phonerouter.MainActivity;
import com.imudges.phonerouter.R;
import com.imudges.phonerouter.R.anim;
import com.imudges.phonerouter.R.id;
import com.imudges.phonerouter.R.layout;
import com.imudges.zhw.javabean.User;
import com.imudges.zt.util.DepthPageTransformer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuideActivity extends Activity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private TextView pageNum;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide);
		inflater = LayoutInflater.from(this);
		// ��ʼ��ҳ��
		initViews();
	}

	// ��ͼƬ�·���TextView
	private void initPageNum() {
		pageNum = (TextView) findViewById(R.id.page_num);
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"font/Roboto-Thin.ttf");
		pageNum.setTypeface(typeface);
		pageNum.setText("");
	}

	private void initViews() {
		// TODO Auto-generated method stub

		views = new ArrayList<View>();
		// ��ʼ������ͼƬ�б�
		views.add(inflater.inflate(R.layout.guide_page_1, null));
		views.add(inflater.inflate(R.layout.guide_page_2, null));
		views.add(inflater.inflate(R.layout.guide_page_3, null));
		// views.add(inflater.inflate(R.layout.fourthpage, null));
		views.add(inflater.inflate(R.layout.guide_page_4, null));
		vpAdapter = new ViewPagerAdapter(views, this);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setPageTransformer(true, new DepthPageTransformer());
		vp.setAdapter(vpAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);
	}

	// ������ʱ����
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// ��ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// pageNum.setText(arg2+1);
	}

	public class ViewPagerAdapter extends PagerAdapter {

		// �����б�
		private List<View> views;
		private Activity activity;
		private static final String SHAREDPREFERENCES_NAME = "first_pref";

		public ViewPagerAdapter(List views, Activity activity) {
			this.views = views;
			this.activity = activity;
		}

		// ����arg1λ�õĽ���
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		// ��õ�ǰ������
		@Override
		public int getCount() {
			if (views != null) {
				return views.size();
			}
			return 0;
		}

		// ��ʼ��arg1λ�õĽ���
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			if (arg1 == 0) {
				AnimationSet animationSet = new AnimationSet(true);
				Animation alphaAnimation = AnimationUtils.loadAnimation(
						GuideActivity.this, R.anim.alpha);
				Animation tAnimation = AnimationUtils.loadAnimation(
						GuideActivity.this, R.anim.trans);
				animationSet.addAnimation(alphaAnimation);
				animationSet.addAnimation(tAnimation);
				ImageView imageView = (ImageView) arg0
						.findViewById(R.id.first_image);
				imageView.startAnimation(animationSet);
			}
			if (arg1 == views.size() - 1) {
				Button mStart = (Button) arg0.findViewById(R.id.mstart);

				mStart.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// �����Ѿ�����
						setGuided();
						goHome();

					}

				});
			}

			return views.get(arg1);
		}

		// �ж��Ƿ��ɶ������ɽ���
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		private void goHome() {
			// ��ת
			User user = BmobUser.getCurrentUser(getApplicationContext(),
					User.class);
			Intent intent;
			if (user != null) {
				intent = new Intent(activity, MainActivity.class);
			} else {
				intent = new Intent(activity, Login_register.class);
			}
			activity.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			activity.finish();
		}

		/**
		 * 
		 * method desc�������Ѿ��������ˣ��´����������ٴ�����
		 */
		private void setGuided() {
			SharedPreferences preferences = activity.getSharedPreferences(
					"first_pref", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			// ��������
			editor.putBoolean("isFirstIn", false);
			// �ύ�޸�
			editor.commit();
		}
	}

	public void onPageSelected(int arg0) {

	}

}