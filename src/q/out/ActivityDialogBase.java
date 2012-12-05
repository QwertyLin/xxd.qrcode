package q.out;

import q.base.ActivityBase;
import cn.xxd.qr.R;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public abstract class ActivityDialogBase extends ActivityBase {
	
	protected View vOk, vClose, vCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.base_transparent);
		setContentView(R.layout.base_dialog);
		((FrameLayout)findViewById(R.id.base_dialog_layout)).addView(initView());
		//
		vOk = findViewById(R.id.base_dialog_ok);
		vClose = findViewById(R.id.base_dialog_close);
		vCancel = findViewById(R.id.base_dialog_cancel);
	}
	
	protected abstract View initView();
}
