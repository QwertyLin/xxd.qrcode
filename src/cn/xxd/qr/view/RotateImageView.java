package cn.xxd.qr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 旋转的View，设置rotation属性
 *
 */
public class RotateImageView extends ImageView {
	
	private final int ROTATION = -90;
	
	private int width, height;
	
	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs); 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(ROTATION, width/2, height/2);
		super.onDraw(canvas);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(width == 0){
			width = getWidth();
			height = getHeight();
		}
	}

}
