package cn.xxd.qr;

import java.util.ArrayList;
import java.util.List;

import cn.xxd.qr.adapter.ColorAdapter;
import cn.xxd.qr.bean.Color;
import cn.xxd.qr.bean.QrCode;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import q.util.ActivityBase;
import q.util.QConfig;
import q.util.QUI;

public class NewA extends ActivityBase implements OnClickListener {
	
	private int mColor, mColorBg;
	private View vColor, vColorBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_new);
		QUI.baseHeaderBackOk(this, "生成二维码", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				QrCode item = new QrCode();
				item.setText("aaa");
				startActivity(new Intent(NewA.this, QrCodeA.class)
				.putExtra(QrCodeA.EXTRA_QRCODE, item)
				.putExtra(QrCodeA.EXTRA_BUILD_COLOR, mColor)
				.putExtra(QrCodeA.EXTRA_BUILD_COLOR_BG, mColorBg));
			}
		});
		//
		initColorValue();
		initColor();
		initColorBg();
	}
	
	private void initColor(){
		mColor = getSharedPreferences(QConfig.SP_NAME, Context.MODE_PRIVATE).getInt(QConfig.SP_KEY_NEW_COLOR, 0xff000000);
		View vNewColor = findViewById(R.id.new_color);
		vNewColor.setOnClickListener(this);
		((TextView)vNewColor.findViewById(R.id.color_kv_k)).setText("图案颜色");
		vColor = vNewColor.findViewById(R.id.color_kv_v);
		vColor.setBackgroundColor(mColor);
	}
	
	private void initColorBg(){
		mColorBg = getSharedPreferences(QConfig.SP_NAME, Context.MODE_PRIVATE).getInt(QConfig.SP_KEY_NEW_COLOR_BG, 0xff000000);
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
		final AlertDialog dialog = new AlertDialog.Builder(this)
		.setSingleChoiceItems(new ColorAdapter(this, colors), 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mColor = colors.get(which).getColor();
				vColor.setBackgroundColor(mColor);
				getSharedPreferences(QConfig.SP_NAME, Context.MODE_PRIVATE)
				.edit().putInt(QConfig.SP_KEY_NEW_COLOR, mColor).commit();
				dialog.dismiss();
			}
		})
		.show();
	}
	
	private void onClickColorBg(){
		final AlertDialog dialog = new AlertDialog.Builder(this)
		.setSingleChoiceItems(new ColorAdapter(this, colors), 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mColorBg = colors.get(which).getColor();
				vColorBg.setBackgroundColor(mColorBg);
				getSharedPreferences(QConfig.SP_NAME, Context.MODE_PRIVATE)
				.edit().putInt(QConfig.SP_KEY_NEW_COLOR_BG, mColorBg).commit();
				dialog.dismiss();
			}
		})
		.show();
	}
	
	private List<Color> colors;
	
	private void initColorValue(){
		colors = new ArrayList<Color>();
		colors.add(new Color("黑色", 0xff000000));
		colors.add(new Color("白色", 0xffffffff));
		colors.add(new Color("暗蓝", 0xff0099CC));
		colors.add(new Color("亮蓝", 0xff33B5E5));
		colors.add(new Color("暗绿", 0xff669900));
		colors.add(new Color("亮绿", 0xff99CC00));
		colors.add(new Color("暗黄", 0xffFF8800));
		colors.add(new Color("亮黄", 0xffFFBB33));
		colors.add(new Color("暗红", 0xffCC0000));
		colors.add(new Color("亮红", 0xffFF4444));
		colors.add(new Color("暗紫", 0xff9933CC));
		colors.add(new Color("亮紫", 0xffAA66CC));
	}
}

