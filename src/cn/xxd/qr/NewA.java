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
import q.util.InputMethodUtil;
import q.util.QSp;
import q.util.QUI;

public class NewA extends ActivityBase implements OnClickListener {
	
	private int mColor;
	private View vColor;
	private EditText vText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		//
		addToBaseLayout(getLayoutInflater().inflate(R.layout.layout_new, null));
		//
		QUI.baseHeaderBackOk(this, "生成二维码", new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickSave();
			}
		});
		//
		vText = (EditText)findViewById(R.id.new_text);
		initColor();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		InputMethodUtil.hide(this, vText);
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
		.putExtra(QrCodeA.EXTRA_BUILD_COLOR, mColor));
	}
	
	private void initColor(){
		mColor = QSp.getNewColor(this);
		View vNewColor = findViewById(R.id.new_color);
		vNewColor.setOnClickListener(this);
		((TextView)vNewColor.findViewById(R.id.color_kv_k)).setText("图案颜色");
		vColor = vNewColor.findViewById(R.id.color_kv_v);
		vColor.setBackgroundColor(mColor);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.new_color:
			onClickColor();
			break;
		}
	}
	
	private void onClickColor(){
		startActivityForResult(new Intent(this, ColorDialog.class).putExtra(ColorDialog.EXTRA_COLOR, mColor), R.id.new_color);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case R.id.new_color:
			onResultColor(data);
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
	
}

