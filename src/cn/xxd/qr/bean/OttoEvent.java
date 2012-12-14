package cn.xxd.qr.bean;

import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;

public final class OttoEvent {

	public static final class CaptureMenuCheckedChange { 
		public final FragmentActivity act;
		public final boolean isChecked; 
		public CaptureMenuCheckedChange(FragmentActivity act, boolean isChecked) {
			this.act = act;
			this.isChecked = isChecked;
		}
	}
	
	public static final class CaptureMenuKeyClick {
		public final CheckBox btnMenu;
		public CaptureMenuKeyClick(CheckBox btnMenu){
			this.btnMenu = btnMenu;
		}
	}
	
	public static final class CaptureBackKeyClick {
		public final FragmentActivity act;
		public final CheckBox btnMenu;
		public CaptureBackKeyClick(FragmentActivity act, CheckBox btnMenu){
			this.act = act;
			this.btnMenu = btnMenu;			
		}
	}

}
