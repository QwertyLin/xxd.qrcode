package q.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

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
}
