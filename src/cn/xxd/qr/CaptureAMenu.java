package cn.xxd.qr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CaptureAMenu extends Fragment {
	
	private Data data = new Data();
	
	public CaptureAMenu(int btnSize){
		data.btnSize = btnSize;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_capture_menu, null);
		//
		FrameLayout.LayoutParams flpMenu = new FrameLayout.LayoutParams(data.btnSize, data.btnSize);
		flpMenu.gravity = Gravity.CENTER;
		LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.capture_menu_1);
		for(int i = 0, size = layout1.getChildCount(); i < size; i++){
			((FrameLayout)layout1.getChildAt(i)).getChildAt(0).setLayoutParams(flpMenu);
		}
		LinearLayout layout2 = (LinearLayout) v.findViewById(R.id.capture_menu_2);
		((FrameLayout)layout2.getChildAt(1)).getChildAt(0).setLayoutParams(flpMenu);
		
		/*new Thread(){
			public void run() {
				SystemClock.sleep(200);
				final int width = layout1.getChildAt(0).getHeight();
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
					}
				});
			};
		}.start();*/
		
		//int btnWidth = (WindowUtil.getWidth(getActivity()) - WindowUtil.dip2px(getActivity(), 30)) / 3;
		/**/
		return v;
		//System.out.println("onCreateC");
		//return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("onResumeC");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("onPauseC");
	}
	
	
	public static final class Data {
		public int btnSize;
	}
		
}
