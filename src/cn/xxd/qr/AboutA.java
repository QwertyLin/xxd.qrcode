package cn.xxd.qr;

import q.util.QUI;
import android.app.Activity;
import android.os.Bundle;

public class AboutA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		QUI.baseHeaderBack(this, "关于");
	}
	
}
