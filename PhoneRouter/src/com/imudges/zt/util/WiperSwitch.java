package com.imudges.zt.util;

import com.imudges.phonerouter.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class WiperSwitch extends View implements OnTouchListener {

	private Bitmap bg_on, bg_off, slip_btn,left,right,buttonbg;
	private Rect canBeSee;//��������
	private float timelyX;// ʵʱX����
	private boolean isSlipping = false;// ��ť�Ƿ��ڻ���
	private boolean currentState = false;// ��ǰ����״̬
	private boolean isSetChangedListener = false;
	private OnChangedListener onChangedListener;
	private boolean isSetState;

	public WiperSwitch(Context context) {
		super(context);
		init();

	}

	public WiperSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();

	}

	public WiperSwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	private void init() {
		bg_on = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_left_1);
		bg_off = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_right_1);
		slip_btn = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_1);
		left=BitmapFactory.decodeResource(getResources(),R.drawable.left);
		right=BitmapFactory.decodeResource(getResources(),R.drawable.right1);
		
		canBeSee=new Rect(0,0,bg_off.getWidth(),bg_off.getHeight());
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.clipRect(canBeSee);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//ʵ�ʻ�ͼ����
		float x;
		float y=0;
		if(isSlipping){//���ڻ���
			if (timelyX >= bg_on.getWidth())// �Ƿ񻮳�ָ����Χ,�������α��ܵ���ͷ,����������ж�

				x = bg_on.getWidth() - slip_btn.getWidth() / 2;// ��ȥ�α�1/2�ĳ���...

			else if (timelyX < 0) {
				x = 0;
			} else {
				x = timelyX - slip_btn.getWidth() / 2;
			}
		}else{//ֹͣ����
			if(currentState){
				x=bg_off.getWidth()-slip_btn.getWidth();
			}else{
				x=0;
			}
		}
		if(isSetState){//�Ƿ�����״̬
			x=bg_on.getWidth()-slip_btn.getWidth();
			isSetState=!isSetState;
		}
		if(x < 0){//touchָ���Ƴ���߽�
			x = 0;
		}else if(x > bg_on.getWidth() - slip_btn.getWidth()){
			x = bg_on.getWidth() - slip_btn.getWidth();
		}
		
		canvas.drawBitmap(bg_on, x - bg_on.getWidth() + slip_btn.getWidth(), 0,
				paint);
		canvas.drawBitmap(bg_off, x, y, paint);
		
		canvas.drawBitmap(left,canBeSee.left,y, paint);
		canvas.drawBitmap(right, canBeSee.right - slip_btn.getWidth() / 2-3,
				0, paint);
		
		canvas.drawBitmap(slip_btn, x, y, paint);
		
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			timelyX = event.getX();
			break;

		case MotionEvent.ACTION_DOWN:
			if (event.getX() > bg_on.getWidth()
					|| event.getY() > bg_on.getHeight())
				return false;
			isSlipping = true;
			timelyX = event.getX();
			break;

		case MotionEvent.ACTION_UP:
			isSlipping = false;
			boolean preStateUp = currentState;
			if (event.getX() >= (bg_on.getWidth() / 2)) {
				timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
				currentState = true;
			} else {
				timelyX = slip_btn.getWidth() / 2;
				currentState = false;
			}
			if (isSetChangedListener && (preStateUp != currentState)) {
				onChangedListener.OnChanged(currentState);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			isSlipping = false;
			boolean preStateCancel = currentState;
			if (timelyX >= (bg_on.getWidth() / 2)) {
				timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
				currentState = true;
			} else {
				timelyX = slip_btn.getWidth() / 2;
				currentState = false;
			}

			if (isSetChangedListener && (preStateCancel != currentState)) {
				onChangedListener.OnChanged(currentState);
			}
			break;
		}
		invalidate();
		return true;
	}

	public interface OnChangedListener {
		abstract void OnChanged(boolean CheckState);
	}

	public void setOnChangedListener(OnChangedListener listener) {
		isSetChangedListener = true;
		onChangedListener = listener;
	}

	public void setState(boolean isSetState) {
		this.isSetState = isSetState;
		currentState = isSetState;
	}

}
