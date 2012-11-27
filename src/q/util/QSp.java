package q.util;

import android.content.Context;

public class QSp {

	private static final String SP_NAME_SETTING = "setting";
	
	private static final String SP_KEY_VERSION = "version";
	public static final int getVersion(Context ctx){
		return ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).getInt(SP_KEY_VERSION, 0xff000000);
	}
	public static final void setVersion(Context ctx, int version){
		ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).edit().putInt(SP_KEY_VERSION, version).commit();
	}
	
	private static final String SP_KEY_NEW_COLOR = "new_color";
	public static final int getNewColor(Context ctx){
		return ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).getInt(SP_KEY_NEW_COLOR, 0xff000000);
	}
	public static final void setNewColor(Context ctx, int color){
		ctx.getSharedPreferences(SP_NAME_SETTING, Context.MODE_PRIVATE).edit().putInt(SP_KEY_NEW_COLOR, color).commit();
	}
	
}
