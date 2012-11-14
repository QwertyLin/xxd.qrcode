package cn.xxd.qr;

import com.google.zxing.Result;
import com.google.zxing.client.android.PreferencesActivity;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.result.ResultHandler;

import cn.xxd.qr.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CA implements OnClickListener {
	
	private Context mCtx;
	private Activity mAct;
	private CameraManager mCameraMgr;
	private ImageView ivQrcode;
	private View layoutQrcode;
	private TextView tvQrcode;
	
	public CA(Context ctx){
		mCtx = ctx;
		mAct = (Activity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		mAct.findViewById(R.id.home_setting).setOnClickListener(this);
		btnFlash = (ImageButton)mAct.findViewById(R.id.home_flash);
		btnFlash.setOnClickListener(this);
		//
		ivQrcode = (ImageView) mAct.findViewById(R.id.home_qrcode_img);
		layoutQrcode = mAct.findViewById(R.id.home_qrcode_layout);
		tvQrcode = (TextView)mAct.findViewById(R.id.home_qrcode_text);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_flash:
			onClickFlash();
			break;
		case R.id.home_setting:
			mAct.startActivity(new Intent(mAct, PreferencesActivity.class));
			break;
		}
	}
	
	private ImageButton btnFlash;
	private boolean isFlashOn = false;
	private void onClickFlash(){
		isFlashOn = !isFlashOn;
		if(isFlashOn){
			btnFlash.setImageResource(R.drawable.a_device_access_flash_on);
		}else{
			btnFlash.setImageResource(R.drawable.a_device_access_flash_off);
		}
		mCameraMgr.setTorch(isFlashOn);
	}
	
	public void handleDecode(Result rawResult, ResultHandler resultHandler, Bitmap barcode){
		layoutQrcode.setVisibility(View.VISIBLE);
		ivQrcode.setImageBitmap(barcode);
		tvQrcode.setText(resultHandler.getDisplayContents());
	}
	
	public void reset(){
		layoutQrcode.setVisibility(View.GONE);
	}

	
}
