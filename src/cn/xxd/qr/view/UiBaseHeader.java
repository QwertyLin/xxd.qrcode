package cn.xxd.qr.view;

import q.base.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class UiBaseHeader extends q.base.UiBaseHeader {
	
	public static final void btnSaveShare(final Activity act, String text, OnClickListener onClickSave, OnClickListener onClickShare){
		btnBack(act, text);
		ImageButton btn = (ImageButton)act.findViewById(R.id.base_header_btn_right);
		btn.setVisibility(View.VISIBLE);
		//btn.setImageResource(R.drawable.a_social_share);
		btn.setOnClickListener(onClickShare);
		btn = (ImageButton)act.findViewById(R.id.base_header_btn_left);
		btn.setVisibility(View.VISIBLE);
		//btn.setImageResource(R.drawable.a_content_save);
		btn.setOnClickListener(onClickSave);
	}

}
