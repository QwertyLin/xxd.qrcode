package cn.xxd.qr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.google.zxing.WriterException;

import cn.xxd.qr.service.QrCodeEncodeService;
import cn.xxd.qr.view.UiBaseHeader;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import q.base.ActivityBase;
import q.util.FileMgr;
import q.util.InputMethodUtil;
import q.util.IntentUtil;
import q.util.QLog;

public class NewA extends ActivityBase implements OnClickListener {
	
	private static final String DEFAULT_TEXT = "小小二维码"; 
	
	private int mColor; 
	private String mText = DEFAULT_TEXT;
	
	private long time = Calendar.getInstance().getTimeInMillis();
	private Bitmap qrBitmap;
	private RadioGroup vColor;
	private TextView vText;
	private ImageView vImg;
	private View vInputLayout;
	private EditText vInputText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		UiBaseHeader.btnSaveShare(this, getString(R.string.new_title), 
			new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickSave();
				}
			}, new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickShare();
				}
			});
		addToBaseLayout(getLayoutInflater().inflate(R.layout.layout_new, null));
		//
		vText = (TextView)findViewById(R.id.new_text); vText.setText(mText);
		vColor = (RadioGroup)findViewById(R.id.new_color);
		vImg = (ImageView)findViewById(R.id.new_img);
		vInputLayout = findViewById(R.id.new_input_layout);
		vInputText = (EditText)findViewById(R.id.new_input_text);
		//
		findViewById(R.id.new_text).setOnClickListener(this);
		findViewById(R.id.base_dialog_footer_ok).setOnClickListener(this);
		findViewById(R.id.base_dialog_footer_cancel).setOnClickListener(this);
		//
		vColor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton)findViewById(checkedId);
				mColor = rb.getTextColors().getDefaultColor();
				onEncode();
			}
		});
		vColor.check(R.id.new_color_black);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		InputMethodUtil.hide(this, vInputText);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.new_text:
			onClickText();
			break;
		case R.id.base_dialog_footer_ok:
			onClickInputOk();
			break;
		case R.id.base_dialog_footer_cancel:
			onClickInputCancel();
			break;
		}
	}
	
	private void onClickText(){
		vInputLayout.setVisibility(View.VISIBLE);
		InputMethodUtil.show(this, vInputText);
	}
	
	private void onClickInputOk(){
		vInputLayout.setVisibility(View.GONE);
		InputMethodUtil.hide(this, vInputText);
		//
		mText = vInputText.getText().toString().trim();
		vInputText.setText(mText);
		vInputText.setSelection(mText.length());
		if(mText.length() == 0){
			mText = DEFAULT_TEXT;
		}
		vText.setText(mText);
		qrBitmap = null;
		vImg.setImageBitmap(null);
		//
		onEncode();
	}
	
	private void onClickInputCancel(){
		vInputLayout.setVisibility(View.GONE);
		InputMethodUtil.hide(this, vInputText);
		vInputText.setText(mText);
		vInputText.setSelection(mText.length());
	}
	
	private void onClickSave(){
		File temp = new File(FileMgr.getInstance(this).getNew(time));
		try {
			qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(temp));
			Toast.makeText(this, "已保存至：" + temp.getPath(), Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//
		QLog.event(this, QLog.EVENT_NEW_SAVE, "");
	}
	
	private void onClickShare(){
		File temp = new File(FileMgr.getInstance(this).getNew(time));
		try {
			qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(temp));
			IntentUtil.sendImage(this, "小小二维码分享：" + mText, temp.getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//
		QLog.event(this, QLog.EVENT_NEW_SHARE, "");
	}
	
	private void onEncode(){
		new Thread(){
			public void run() {
				try {
					qrBitmap = QrCodeEncodeService.encode(NewA.this, mText, mColor);
					runOnUiThread(new Runnable(){
						public void run() {
							vImg.setImageBitmap(qrBitmap);
						};
					});
				} catch (WriterException e) {
					e.printStackTrace();
				}
			};
		}.start();
		//统计
		QLog.event(this, QLog.EVENT_NEW, mText);
		QLog.event(this, QLog.EVENT_NEW_COLOR, String.valueOf(mColor));
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(vInputLayout.getVisibility() == View.VISIBLE){
				onClickInputCancel();
				return true;
			}
			startActivity(new Intent(this, CaptureA.class));
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

