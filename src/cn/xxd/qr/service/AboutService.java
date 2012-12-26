package cn.xxd.qr.service;

import java.io.IOException;

import cn.xxd.qr.util.QSp;

import q.util.QHttpUtil;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AboutService {
	
	private static final String UPDATE_URL = "http://www.xxd.cn/update.php?v=";
	
	public static final void checkUpdate(final Context ctx){
		new Thread(){
			public void run() {
				try {
					ApplicationInfo info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(),  PackageManager.GET_META_DATA);
					String channel = String.valueOf(info.metaData.get("BaiduMobAd_CHANNEL"));
					//
					String url = UPDATE_URL + ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode + "&c=" + channel;
					String result = QHttpUtil.get(url);
					if(result != null && result.startsWith("http")){
						System.out.println("has update");
						QSp.setUpdateUrl(ctx, result);
					}else{
						System.out.println("no update");
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}

}
