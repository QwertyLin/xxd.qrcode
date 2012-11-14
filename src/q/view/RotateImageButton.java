package q.view;

import cn.xxd.qr.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 旋转的View，设置rotation属性
 *
 */
public class RotateImageButton extends ImageButton {
	
	private float rotation;
	private float width, height;
	
	public RotateImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.qview);   
		this.rotation = array.getFloat(R.styleable.qview_arg_float, 0);
        array.recycle(); 	
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
			width = getWidth();
			height = getHeight();
		}
	}

}
