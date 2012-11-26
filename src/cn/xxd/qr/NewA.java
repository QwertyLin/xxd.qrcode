package cn.xxd.qr;

import cn.xxd.qr.bean.QrCode;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import q.util.ActivityBase;
import q.util.QSp;
import q.util.QUI;

public class NewA extends ActivityBase implements OnClickListener {
	
	private int mColor, mColorBg;
	private View vColor, vColorBg;
	private EditText vText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_new);
		QUI.baseHeaderBackOk(this, "生成二维码", new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickSave();
			}
		});
		//
		vText = (EditText)findViewById(R.id.new_text);
		initColor();
		initColorBg();
	}
	
	private void onClickSave(){
		String text = vText.getText().toString().trim();
		if(text.equals("")){
			new AlertDialog.Builder(this).setMessage("内容不能为空！").setNeutralButton("我知道了", null).show();
			return;
		}
		QrCode item = new QrCode();
		item.setText(text);
		startActivity(new Intent(this, QrCodeA.class)
		.putExtra(QrCodeA.EXTRA_QRCODE, item)
		.putExtra(QrCodeA.EXTRA_BUILD_COLOR, mColor)
		.putExtra(QrCodeA.EXTRA_BUILD_COLOR_BG, mColorBg));
	}
	
	private void initColor(){
		mColor = QSp.getNewColor(this);
		View vNewColor = findViewById(R.id.new_color);
		vNewColor.setOnClickListener(this);
		((TextView)vNewColor.findViewById(R.id.color_kv_k)).setText("图案颜色");
		vColor = vNewColor.findViewById(R.id.color_kv_v);
		vColor.setBackgroundColor(mColor);
	}
	
	private void initColorBg(){
		mColorBg = QSp.getNewColorBg(this);
		View vNewColorBg = findViewById(R.id.new_color_bg);
		vNewColorBg.setOnClickListener(this);
		((TextView)vNewColorBg.findViewById(R.id.color_kv_k)).setText("背景颜色");
		vColorBg = vNewColorBg.findViewById(R.id.color_kv_v);
		vColorBg.setBackgroundColor(mColorBg);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.new_color:
			onClickColor();
			break;
		case R.id.new_color_bg:
			onClickColorBg();
			break;
		}
	}
	
	private void onClickColor(){
		startActivityForResult(new Intent(this, ColorDialog.class).putExtra(ColorDialog.EXTRA_COLOR, mColor), R.id.new_color);
	}
	
	private void onClickColorBg(){
		startActivityForResult(new Intent(this, ColorDialog.class).putExtra(ColorDialog.EXTRA_COLOR, mColorBg), R.id.new_color_bg);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case R.id.new_color:
			onResultColor(data);
			break;
		case R.id.new_color_bg:
			onResultColorBg(data);
			break;
		}
	}
	
	private void onResultColor(Intent data){
		if(data == null){
			return;
		}
		mColor = data.getIntExtra(ColorDialog.EXTRA_COLOR, 0);
		vColor.setBackgroundColor(mColor);
		QSp.setNewColor(NewA.this, mColor);
	}
	
	private void onResultColorBg(Intent data){
		if(data == null){
			return;
		}
		mColorBg = data.getIntExtra(ColorDialog.EXTRA_COLOR, 0);
		vColorBg.setBackgroundColor(mColorBg);
		QSp.setNewColorBg(NewA.this, mColorBg);
	}
}

