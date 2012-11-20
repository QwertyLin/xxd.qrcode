package cn.xxd.qr;

import java.util.Date;

import q.util.QConfig;

import cn.xxd.qr.bean.HistoryDb;
import cn.xxd.qr.bean.QrCode;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MainA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onError(this);
		UmengUpdateAgent.update(this);
		//
		initVersion();
		//
		//tempInitHistoryData();
		startActivity(new Intent(this, NewA.class));
		//startActivity(new Intent(this, CaptureActivity.class));
		//startActivity(new Intent(this, GuideA.class));
		//startActivity(new Intent(this, AboutA.class));
		//startActivity(new Intent(this, SettingA.class));
		//startActivity(new Intent(this, HistoryA.class));
		//startActivity(new Intent(this, QrCodeA.class));
		finish();
	}
	
	private void initVersion(){
		SharedPreferences sp = getSharedPreferences(QConfig.SP_NAME, Context.MODE_PRIVATE);
		int version = sp.getInt(QConfig.SP_KEY_VERSION, 0);
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			 int currentVersion = info.versionCode;
			 if(currentVersion > version){
				 sp.edit().putInt(QConfig.SP_KEY_VERSION, currentVersion).commit();
				 startActivity(new Intent(this, GuideA.class));
				 finish();
				 return;
			 }
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void tempInitHistoryData(){
		HistoryDb db = new HistoryDb(this);
		db.open(true);
		for(int i = 1; i < 100; i++){
			QrCode item = new QrCode();
			item.setText(String.valueOf(i));
			item.setTime(new Date().getTime());
			db.insert(item);
		}
		db.close();
	}
	
}
