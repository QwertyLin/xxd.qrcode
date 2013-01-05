package q.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	public static final String getChannel(Context ctx){
		try {
			ApplicationInfo info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(),  PackageManager.GET_META_DATA);
			return String.valueOf(info.metaData.get("BaiduMobAd_CHANNEL"));
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	public static final int getVersion(Context ctx){
		try {
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
