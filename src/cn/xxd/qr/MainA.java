package cn.xxd.qr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, HistoryA.class));
		finish();
	}
	
}
