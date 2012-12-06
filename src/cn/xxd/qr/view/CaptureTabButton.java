package cn.xxd.qr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * 旋转的View，设置rotation属性
 *
 */
public class CaptureTabButton extends RadioButton {
	
	private float rotation;
	private float width, height;
	
	public CaptureTabButton(Context context, AttributeSet attrs) {
		super(context, attrs); 
		setGravity(Gravity.CENTER);
		this.rotation = -90;
	}

	/**
	 * 设置旋转的角度
	 * @param rotation
	 */
	public void setRotate(float rotation){
		this.rotation = rotation;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(rotation, width/2, height/2);
		super.onDraw(canvas);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(width == 0){
			width = getHeight();
			height = width;
			setLayoutParams(new LinearLayout.LayoutParams((int)width, (int)width));
		}
	}

}
