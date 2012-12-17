package cn.xxd.qr;

import com.squareup.otto.Subscribe;

import q.util.EventHelper;
import q.util.QLog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CaptureAMenu extends Fragment implements OnClickListener {
	
	private View mView;
	private boolean mIsFlashOn;
	private int mBtnSize;
	private TextView vFlash;
	
	public CaptureAMenu(int btnSize, boolean isFlashOn){
		QLog.log(this, "CaptureAMenu");
		this.mBtnSize = btnSize;
		this.mIsFlashOn = isFlashOn;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		QLog.log(this, "onCreateView");
		mView = inflater.inflate(R.layout.layout_capture_menu, null);
		//
		vFlash = (TextView)mView.findViewById(R.id.capture_menu_flash_desc);
		//
		FrameLayout.LayoutParams flpMenu = new FrameLayout.LayoutParams(mBtnSize, mBtnSize);
		flpMenu.gravity = Gravity.CENTER;		
		FrameLayout item = null;
		item = (FrameLayout)mView.findViewById(R.id.capture_menu_new);
		item.getChildAt(0).setLayoutParams(flpMenu);
		item.setOnClickListener(this);
		item = (FrameLayout)mView.findViewById(R.id.capture_menu_history);
		item.getChildAt(0).setLayoutParams(flpMenu);
		item.setOnClickListener(this);
		item = (FrameLayout)mView.findViewById(R.id.capture_menu_about);
		item.getChildAt(0).setLayoutParams(flpMenu);
		item.setOnClickListener(this);
		item = (FrameLayout)mView.findViewById(R.id.capture_menu_flash);
		item.getChildAt(0).setLayoutParams(flpMenu);
		item.setOnClickListener(this);
		//
		initFlash();
		return mView;
	}
	
	@Override
	public void onResume() {
		QLog.log(this, "onResume");
		super.onResume();
		EventHelper.get().register(this);
		//
		AnimationSet aniSet = new AnimationSet(false);
		Animation ani = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		aniSet.addAnimation(ani);
		ani = new AlphaAnimation(0.5f, 1);
		aniSet.addAnimation(ani);
		aniSet.setDuration(200);
		mView.clearAnimation();
		mView.startAnimation(aniSet);
	}
	
	@Override
	public void onPause() {
		QLog.log(this, "onPause");
		super.onPause();
		EventHelper.get().unregister(this);
	}
		
	public void initFlash(){
		if(mIsFlashOn){
			vFlash.setText(getString(R.string.capture_menu_flash_off));
			vFlash.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.capture_flash_off, 0, 0);
		}else{
			vFlash.setText(getString(R.string.capture_menu_flash_on));
			vFlash.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.capture_flash_on, 0, 0);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.capture_menu_new: getActivity().startActivity(new Intent(getActivity(), NewA.class)); break;
		case R.id.capture_menu_history: getActivity().startActivity(new Intent(getActivity(), HistoryA.class)); break;
		case R.id.capture_menu_about: getActivity().startActivity(new Intent(getActivity(), AboutA.class)); break;
		case R.id.capture_menu_flash: EventHelper.get().post(new CaptureEvent.SwitchFlash()); break;
		}
	}
	
	@Subscribe
	public void onHideMenuStart(CaptureEvent.HideMenuStart event){
		AnimationSet aniSet = new AnimationSet(false);
		Animation ani = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		aniSet.addAnimation(ani);
		ani = new AlphaAnimation(1, 0.5f);
		aniSet.addAnimation(ani);
		aniSet.setDuration(200);
		aniSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) { }
			
			@Override
			public void onAnimationRepeat(Animation animation) { }
			
			@Override
			public void onAnimationEnd(Animation animation) {
				EventHelper.get().post(new CaptureEvent.HideMenuFinish());
			}
		});
		mView.clearAnimation();
		mView.startAnimation(aniSet);
	}
	
	@Subscribe
	public void onSwitchFlash(CaptureEvent.SwitchFlash event){
		mIsFlashOn = !mIsFlashOn;
		initFlash();
	}
	

}
