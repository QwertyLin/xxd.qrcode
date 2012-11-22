package q.util;


import com.baidu.mobstat.StatService;
import android.content.Context;
import android.util.Log;

public class QLog {
	
	public static final int EVENT_QRCODE = 1; 
	
	private static boolean available = true;
		
	public static void event(Context ctx, int id, String label){
		StatService.onEvent(ctx, String.valueOf(id), label);
		//MobclickAgent.onEvent(ctx, String.valueOf(id), label);
	}
	
	public static final void kv(String clazz, String method, String key, String value){
		if(available){
			Log.d(clazz + ":" + (method == null ? "" : method), key + " *** " + value);
		}
	}
	
	public static final void kv(Object clazz, String method, String key, String value){
		if(available){
			kv(clazz.getClass().getSimpleName(), method, key, value);
		}
	}
	
	public static final void kv(Object tag, String method, String key, boolean value){
		if(available){
			kv(tag, method, key, String.valueOf(value));
		}
	}
	
	public static final void kv(Object tag, String method, String key, int value){
		if(available){
			kv(tag, method, key, String.valueOf(value));
		}
	}
	
	public static final void kv(Object tag, String method, String key, float value){
		if(available){
			kv(tag, method, key, String.valueOf(value));
		}
	}
	
	public static final void log(String tag, String msg){
		if(available){
			Log.d(tag, msg);
		}
	}
	
	public static final void log(Object tag, String msg){
		if(available){
			Log.d(tag.getClass().getSimpleName(), msg);
		}
	}
}
