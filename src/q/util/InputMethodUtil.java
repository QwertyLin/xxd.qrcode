package q.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法
 */
public class InputMethodUtil {

	/**
	 * @param v EditText类View
	 */
	public static final void show(Context ctx, View v){
		v.requestFocus();//先获得焦点
		((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE))
			.showSoftInput(v,InputMethodManager.SHOW_IMPLICIT);
	}
	
	/**
	 * @param v EditText类View
	 */
	public static final void hide(Context ctx, View v){
		((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
