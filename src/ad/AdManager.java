package ad;

import android.app.Activity;
import android.content.Context;

public class AdManager {
	
	public static void init(Context ctx){
		//net.youmi.android.AdManager.init(ctx,"b91f0970cc72ba26", "a6962e4070cbec1f", 30, false);
		//com.tencent.exmobwin.MobWINManager.init(ctx,com.tencent.exmobwin.Type.MOBWIN_BANNER);
	}
	
	public static void destroy(){
		//com.tencent.exmobwin.MobWINManager.destroy();
	}
	
	public static void add(Activity act){
		/*FrameLayout parent = (FrameLayout)act.findViewById(R.id.ad);
		if(parent == null){
			return;
		}
		//
		//com.tencent.exmobwin.banner.TAdView adView = new com.tencent.exmobwin.banner.TAdView(act); 
		net.youmi.android.AdView adView = new net.youmi.android.AdView(act); 
		//
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		parent.addView(adView, params);  	*/	
	}

}
