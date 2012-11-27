package q.util;

import cn.xxd.qr.R;

import com.baidu.mobstat.StatService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

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
    
    protected void addToBaseLayout(View view){
    	((FrameLayout)findViewById(R.id.base_layout)).addView(view);
    }
}
