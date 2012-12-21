package cn.xxd.qr.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class QSp {

	private static final String SP_NAME_SETTING = "setting";
	
	private static final String SP_KEY_VERSION = "version";
	public static final int getVersion(Context ctx){
		return ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).getInt(SP_KEY_VERSION, 0xff000000);
	}
	public static final void setVersion(Context ctx, int version){
		ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).edit().putInt(SP_KEY_VERSION, version).commit();
	}
	
	
	//================================update=================================================
	
	private static final String SP_NAME_UPDATE = "update";
	
	private static final String SP_KEY_UPDATE_URL = "url_";
	public static final String getUpdateUrl(Context ctx){
		try {
			int versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
			return ctx.getSharedPreferences(SP_NAME_UPDATE, Context.MODE_PRIVATE).getString(SP_KEY_UPDATE_URL + versionCode, null);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final void setUpdateUrl(Context ctx, String updateUrl){
		ctx.getSharedPreferences(SP_NAME_UPDATE, Context.MODE_PRIVATE).edit().clear().commit();
		try {
			int versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
			ctx.getSharedPreferences(SP_NAME_UPDATE, Context.MODE_PRIVATE).edit().putString(SP_KEY_UPDATE_URL + versionCode, updateUrl).commit();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*private static final String SP_KEY_NEW_COLOR = "new_color";
	public static final int getNewColor(Context ctx){
		return ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).getInt(SP_KEY_NEW_COLOR, 0xff000000);
	}
	public static final void setNewColor(Context ctx, int color){
		ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).edit().putInt(SP_KEY_NEW_COLOR, color).commit();
	}*/
	
}
