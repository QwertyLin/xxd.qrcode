package cn.xxd.qr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.google.zxing.WriterException;
import cn.xxd.qr.service.QrCodeEncodeService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import q.util.FileMgr;
import q.util.IntentUtil;
import q.util.QLog;

public class NewA extends Activity implements OnClickListener, OnCheckedChangeListener {
	
	private static final int DEFAULT_COLOR = 0xFF000000;
	private static final String DEFAULT_TEXT = "二维码 X"; 
	
	private int mColor = DEFAULT_COLOR;
	private String mText = DEFAULT_TEXT;
	
	private long time = Calendar.getInstance().getTimeInMillis();
	private Bitmap qrBitmap;
	private TextView vText;
	private ImageView vImg;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_new);
		//
		vText = (TextView)findViewById(R.id.new_text); vText.setText(mText);
		vImg = (ImageView)findViewById(R.id.new_img);
		//
		vText.setOnClickListener(this);
		findViewById(R.id.new_color).setOnClickListener(this);
		findViewById(R.id.new_save).setOnClickListener(this);
		findViewById(R.id.new_share).setOnClickListener(this);
		//
		encode();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.new_text:
			onClickText();
			break;
		case R.id.new_color:
			onClickColor();
			break;
		case R.id.new_save:
			onClickSave();
			break;
		case R.id.new_share:
			onClickShare();
			break;
		}
	}
		
	private void onClickText(){
		final EditText et = new EditText(this);
		if(!mText.equals(DEFAULT_TEXT)){
			et.setText(mText);
			et.setSelection(mText.length());
		}
		new AlertDialog.Builder(this)
		.setView(et)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mText = et.getText().toString().trim();
				if(mText.length() == 0){
					mText = DEFAULT_TEXT;
				}
				vText.setText(mText);
				encode();
			}
		})
		.setNegativeButton("取消", null)
		.show();
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
			IntentUtil.sendImage(this, "", temp.getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//
		QLog.event(this, QLog.EVENT_NEW_SHARE, "");
	}
	
	private void encode(){
		new Thread(){
			public void run() {
				try {
					final Bitmap bmTemp = QrCodeEncodeService.encode(NewA.this, mText, mColor);
					runOnUiThread(new Runnable(){
						public void run() {
							vImg.setImageBitmap(bmTemp);
							if(qrBitmap != null && !qrBitmap.isRecycled()){
								qrBitmap.isRecycled();
							}
							qrBitmap = bmTemp;
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
	
	protected void ________Color(){};
	
	private AlertDialog dialogColor;
	
	private void onClickColor(){
		if(dialogColor == null){
			View v = getLayoutInflater().inflate(R.layout.layout_new_color, null);
			RadioGroup rg = (RadioGroup)v.findViewById(R.id.new_color);
			rg.check(R.id.new_color_black);
			rg.setOnCheckedChangeListener(this);
			dialogColor = new AlertDialog.Builder(this)
			.setView(v)
			.setNeutralButton(R.string.dialog_cancel, null)
			.create();
		}
		dialogColor.show();
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		dialogColor.dismiss();
		RadioButton rb = (RadioButton)group.findViewById(checkedId);
		mColor = rb.getTextColors().getDefaultColor();
		encode();
	}
	
}

