package cn.xxd.qr;

import java.util.Date;

import com.zhuamob.android.ZhuamobTracking;

import q.util.QSp;

import cn.xxd.qr.bean.HistoryDb;
import cn.xxd.qr.bean.QrCode;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MainA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		//initVersion();
		//
		//startService(new Intent(this, UpdateService.class).putExtra(UpdateService.EXTRA_URL, "http://www.xxd.cn/test.apk"));
		//intent

		//tempInitHistoryData();
		//startActivity(new Intent(this, NewA.class));
		startActivity(new Intent(this, CaptureActivity.class));
		//startActivity(new Intent(this, UpdateA.class));
		//startActivity(new Intent(this, GuideA.class));
		//startActivity(new Intent(this, AboutA.class));
		//startActivity(new Intent(this, SettingA.class));
		//startActivity(new Intent(this, HistoryA.class));
		//startActivity(new Intent(this, QrCodeA.class));
		finish();
	}
		
	private void tempInitHistoryData(){
		HistoryDb db = new HistoryDb(this);
		db.open(true);
		for(int i = 1; i < 10; i++){
			QrCode item = new QrCode();
			item.setText(String.valueOf(i));
			item.setTime(new Date().getTime());
			db.insert(item);
		}
		db.close();
	}
	
}
