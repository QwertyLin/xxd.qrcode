package ad;

import cn.xxd.qr.R;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;

public class AdManager {
	
	public static void init(Context ctx){
		com.tencent.exmobwin.MobWINManager.init(ctx,com.tencent.exmobwin.Type.MOBWIN_BANNER);
	}
	
	public static void destroy(){
		com.tencent.exmobwin.MobWINManager.destroy();
	}
	
	public static void add(Activity act){
		FrameLayout parent = (FrameLayout)act.findViewById(R.id.ad);
		if(parent == null){
			return;
		}
		com.tencent.exmobwin.banner.TAdView adView = new com.tencent.exmobwin.banner.TAdView(act); 
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		parent.addView(adView, params);  		
	}

}
