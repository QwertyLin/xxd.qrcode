package cn.xxd.qr;

import cn.xxd.qr.service.UpdateService;
import q.util.ActivityBase;
import q.util.QUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SettingA extends ActivityBase implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		QUI.baseHeaderBack(this, "设置");
		//
		findViewById(R.id.setting_market).setOnClickListener(this);
		findViewById(R.id.setting_share).setOnClickListener(this);
		findViewById(R.id.setting_update).setOnClickListener(this);
		findViewById(R.id.setting_about).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_market:
			onClickMarket();
			break;
		case R.id.setting_share:
			onClickShare();
			break;
		case R.id.setting_update:
			onClickUpdate();
			break;
		case R.id.setting_about:
			onClickAbout();
			break;
		}
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
		UpdateService.check(this, true);
	}
	
	private void onClickAbout(){
		startActivity(new Intent(this, AboutA.class));
	}
}
