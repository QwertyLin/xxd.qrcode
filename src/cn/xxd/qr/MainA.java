package cn.xxd.qr;

import q.util.QConfig;

import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo(QConfig.BASE_PACKAGE_NAME, 0);
			 int currentVersion = info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		
		//
		startActivity(new Intent(this, CaptureActivity.class));
		//startActivity(new Intent(this, FavoriteA.class));
		//startActivity(new Intent(this, QrCodeA.class));
		finish();
	}
	
}
