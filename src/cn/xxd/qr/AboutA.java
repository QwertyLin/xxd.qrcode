package cn.xxd.qr;

import cn.xxd.qr.service.UpdateUtil;
import q.util.ActivityBase;
import q.util.QConfig;
import q.util.QUI;
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
		QUI.baseHeaderBack(this, "关于");
		//
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
		findViewById(R.id.setting_update).setOnClickListener(this);
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
		case R.id.setting_update:
			onClickUpdate();
			break;
		}
	}
	
	private void onClickWebsite(){
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(QConfig.BASE_WEBSITE_URL)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
	
	private void onClickMarket(){
		startActivity(
				new Intent(Intent.ACTION_VIEW)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				.setData(Uri.parse("market://details?id=" + getPackageName()))
				);
	}
	
	private void onClickShare(){
		startActivity(
				Intent.createChooser(
						new Intent(Intent.ACTION_SEND)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
						.setType("text/plain")
						.putExtra(Intent.EXTRA_TEXT, "推荐一款安卓手机很好用的二维码扫描器，小小二维码: http://www.xxd.cn")
						, "分享")
						
				);
	}
	
	private void onClickUpdate(){
		UpdateUtil.check(this, true);
	}
	
}
