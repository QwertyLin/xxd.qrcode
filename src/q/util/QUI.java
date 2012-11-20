package q.util;

import cn.xxd.qr.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class QUI {
	
	public static final void baseHeaderText(final Activity act, String text){
		((TextView)act.findViewById(R.id.base_header_text)).setText(text);
	}
		
	public static final void baseHeaderBack(final Activity act, String text){
		baseHeaderText(act, text);
		ImageButton btn = (ImageButton)act.findViewById(R.id.base_header_btn_left);
		btn.setVisibility(View.VISIBLE);
		btn.setImageResource(R.drawable.a_navigation_back);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				act.finish();
			}
		});
	}
	
	public static final void baseHeaderBackOk(final Activity act, String text, OnClickListener onClickOk){
		baseHeaderBack(act, text);
		ImageButton btn = (ImageButton)act.findViewById(R.id.base_header_btn_right);
		btn.setVisibility(View.VISIBLE);
		btn.setImageResource(R.drawable.a_navigation_accept);
		btn.setOnClickListener(onClickOk);
	}

}
