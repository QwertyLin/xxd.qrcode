package cn.xxd.qr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class ScanA extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scan);
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		new Thread(){
			public void run() {
				SystemClock.sleep(200);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						startActivity(new Intent(ScanA.this, CaptureA.class));
						finish();
					}
				});
			};
		}.start();
	}
}
