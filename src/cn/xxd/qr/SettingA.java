package cn.xxd.qr;

import q.util.QUI;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SettingA extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		QUI.baseHeaderBack(this, "设置");
		//
		findViewById(R.id.setting_guide).setOnClickListener(this);
		findViewById(R.id.setting_market).setOnClickListener(this);
		findViewById(R.id.setting_share).setOnClickListener(this);
		findViewById(R.id.setting_update).setOnClickListener(this);
		findViewById(R.id.setting_about).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_guide:
			onClickGuide();
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
		case R.id.setting_about:
			onClickAbout();
			break;
		}
	}
	
	private void onClickGuide(){
		startActivity(new Intent(this, GuideA.class));
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
		final ProgressDialog pd = ProgressDialog.show(this, "", "正在检查更新...");
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		        @Override
		        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		        	pd.cancel();
		            switch (updateStatus) {
		            case 0: // has update
		                UmengUpdateAgent.showUpdateDialog(SettingA.this, updateInfo);
		                break;
		            case 1: // has no update
		                Toast.makeText(SettingA.this, "没有更新", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            case 2: // none wifi
		                Toast.makeText(SettingA.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            case 3: // time out
		                Toast.makeText(SettingA.this, "超时", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            }
		        }
		});
	}
	
	private void onClickAbout(){
		startActivity(new Intent(this, AboutA.class));
	}
}
