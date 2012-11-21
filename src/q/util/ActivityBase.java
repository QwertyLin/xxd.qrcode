package q.util;

import com.zhuamob.android.ZhuamobLayout;
import com.zhuamob.android.ZhuamobTracking;

import android.app.Activity;
import android.os.Bundle;

public abstract class ActivityBase extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	ZhuamobTracking.onResume(this);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	ZhuamobTracking.onPause(this); 
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	ZhuamobLayout.clean();
    }
}
