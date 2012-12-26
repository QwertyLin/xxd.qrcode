package cn.xxd.qr;

import cn.xxd.qr.util.QConfig;
import cn.xxd.qr.util.QSp;
import q.base.ActivityBase;
import q.util.AppUtil;
import q.util.IntentUtil;
import q.util.QLog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AboutA extends ActivityBase implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		//
		initUpdate();
		initVersion();
		//
		TextView tvWebsite = (TextView)findViewById(R.id.about_website);
		tvWebsite.setText(QConfig.BASE_WEBSITE_URL);
		tvWebsite.setOnClickListener(this);
		findViewById(R.id.about_market).setOnClickListener(this);
		//
	}
	
	private void initUpdate(){
		if(QSp.getUpdateUrl(this) != null){
			new AlertDialog.Builder(this)
			.setMessage(R.string.about_update)
			.setNeutralButton(R.string.about_update_go, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						IntentUtil.openBrowser(AboutA.this, "http://android.myapp.com/android/down.jsp?appid=653022");
					} catch (Exception e) {
						Toast.makeText(AboutA.this, R.string.error_browser, Toast.LENGTH_SHORT).show();
					}
				}
			})
			.show();
		}
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
		case R.id.about_market:
			onClickMarket();
			break;
		}
	}
	
	private void onClickWebsite(){
		try {
			IntentUtil.openBrowser(this, QConfig.BASE_WEBSITE_URL);
		} catch (Exception e) {
			Toast.makeText(this, R.string.error_browser, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void onClickMarket(){
		try {
			IntentUtil.openMarket(this);
		} catch (Exception e) {
			Toast.makeText(this, R.string.error_market, Toast.LENGTH_SHORT).show();
		}
		//
		QLog.event(this, QLog.EVENT_ABOUT_MARKET, AppUtil.getChannel(this));
	}
	
	/*private void onClickShare(){
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
	}*/
	
	
}
