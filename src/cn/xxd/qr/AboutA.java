package cn.xxd.qr;

import cn.xxd.qr.util.QConfig;
import cn.xxd.qr.util.QSp;
import q.base.ActivityBase;
import q.util.IntentUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
		//
	}
	
	private void initUpdate(){
		if(QSp.getUpdateUrl(this) != null){
			new AlertDialog.Builder(this)
			.setMessage(R.string.about_update)
			.setNeutralButton(R.string.about_update_go, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					IntentUtil.openBrowser(AboutA.this, "http://android.myapp.com/android/down.jsp?appid=653022");
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
		}
	}
	
	private void onClickWebsite(){
		IntentUtil.openBrowser(this, QConfig.BASE_WEBSITE_URL);
	}
	
	
	/*private void onClickMarket(){
		startActivity(
				new Intent(Intent.ACTION_VIEW)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				.setData(Uri.parse("market://details?id=" + getPackageName()))
				);
		//
		QLog.event(this, QLog.EVENT_ABOUT_MARKET, AppUtil.getChannel(this));
	}*/
	
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
