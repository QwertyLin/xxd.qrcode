package cn.xxd.qr;

import q.util.QConfig;
import q.util.QUI;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutA extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		QUI.baseHeaderBack(this, "关于");
		//
		initVersion();
		//
		TextView tvWebsite = (TextView)findViewById(R.id.about_website);
		tvWebsite.setText(QConfig.BASE_WEBSITE_URL);
		tvWebsite.setOnClickListener(this);
	}
	
	private void initVersion(){
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			((TextView)findViewById(R.id.about_version)).setText("v" + info.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.about_website:
			onClickWebsite();
			break;
		}
	}
	
	private void onClickWebsite(){
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(QConfig.BASE_WEBSITE_URL)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
	
}
