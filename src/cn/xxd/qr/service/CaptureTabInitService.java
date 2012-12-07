package cn.xxd.qr.service;

import cn.xxd.qr.HomeA;
import cn.xxd.qr.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class CaptureTabInitService {
	
	public static final void initLayoutClick(final Activity act, View layout){
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(HomeA.TAB_HEIGHT, FrameLayout.LayoutParams.FILL_PARENT);
		lp.gravity = Gravity.RIGHT;
		act.findViewById(R.id.capture_tab_layout).setLayoutParams(lp);
		layout.setOnTouchListener(new OnTouchListener() {
			    int maxX, maxY;
			    float x, y;
			    float y1, y2, y3;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(maxX == 0){
					maxX = v.getWidth();
					maxY = v.getHeight();
					y2 = maxY / 2;
					y1 = y2 / 2;
					y3 = y1 * 3;
				}
				x = event.getX();
				y = event.getY();
				
				switch(event.getAction()){
				case MotionEvent.ACTION_UP:
					if(x < 0 || x > maxX || y < 0 || y > y3){
						return true;
					}
					//Intent intent = new Intent(ctx, HomeA.class);
					if(y < y1){
						HomeA.POSITION = 3;
						//intent.putExtra(HomeA.EXTRA_POSITION, 3);
					}else if(y < y2){
						HomeA.POSITION = 2;
						//intent.putExtra(HomeA.EXTRA_POSITION, 2);
					}else if(y < y3){
						HomeA.POSITION = 1;
						//intent.putExtra(HomeA.EXTRA_POSITION, 1);
					}
					//ctx.startActivity(intent);
					//((Context)act).startActivity(new Intent(act, HomeA.class));
					act.finish();
					return true;
				}
				return true;
			}
		});
	}
	
}

