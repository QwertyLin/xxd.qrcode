package cn.xxd.qr;

import q.base.ActivityBase;
import q.base.UiBaseHeader;
import q.util.AppUtil;
import q.util.IntentUtil;
import q.util.QConfig;
import q.util.QLog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutA extends ActivityBase implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		UiBaseHeader.btnBack(this, getString(R.string.about));
		addToBaseLayout(getLayoutInflater().inflate(R.layout.layout_about, null));
		//
		initVersion();
		//
		TextView tvWebsite = (TextView)findViewById(R.id.about_website);
		tvWebsite.setText(QConfig.BASE_WEBSITE_URL);
		tvWebsite.setOnClickListener(this);
		//
		findViewById(R.id.setting_market).setOnClickListener(this);
		findViewById(R.id.setting_share).setOnClickListener(this);
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
		case R.id.setting_market:
			onClickMarket();
			break;
		case R.id.setting_share:
			onClickShare();
			break;
		}
	}
	
	private void onClickWebsite(){
		IntentUtil.openBrowser(this, QConfig.BASE_WEBSITE_URL);
	}
	
	private void onClickMarket(){
		startActivity(
				new Intent(Intent.ACTION_VIEW)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				.setData(Uri.parse("market://details?id=" + getPackageName()))
				);
		//
		QLog.event(this, QLog.EVENT_ABOUT_MARKET, AppUtil.getChannel(this));
	}
	
	private void onClickShare(){
		startActivity(
				Intent.createChooser(
						new Intent(Intent.ACTION_SEND)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
						.setType("text/plain")
						.putExtra(Intent.EXTRA_TEXT, "分享APP<小小二维码>，下载：http://www.xxd.cn")
						, "分享")
						
				);
		//
		QLog.event(this, QLog.EVENT_ABOUT_SHARE, "");
	}
	
	
}
