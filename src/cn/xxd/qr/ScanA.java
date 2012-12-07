package cn.xxd.qr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ScanA extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(HomeA.GO_TO_SCAN){
			HomeA.GO_TO_SCAN = false;
			startActivity(new Intent(this, CaptureA.class));
			HomeA.HOME_A.getRadioGroup().check(HomeA.POSITION);
		}
	}
	

}
