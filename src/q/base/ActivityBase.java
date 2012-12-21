package q.base;

import com.baidu.mobstat.StatService;

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
    	StatService.onResume(this);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	StatService.onPause(this); 
    }
}
