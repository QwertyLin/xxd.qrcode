package cn.xxd.qr;

import java.util.Calendar;

import q.util.BitmapUtil;
import q.util.FileMgr;
import q.util.WindowUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.xxd.qr.bean.QrCode;

import com.google.zxing.client.android.camera.CameraManager;

public class CaptureA2 implements OnClickListener {
	
	private Activity mAct;
	private CameraManager mCameraMgr;
	private ImageButton vLight;
	
	public CaptureA2(Context ctx){
		mAct = (Activity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		vLight = (ImageButton)mAct.findViewById(R.id.capture_light);
		vLight.setOnClickListener(this);
		mAct.findViewById(R.id.capture_home).setOnClickListener(this);
	}
	
	public void onWindowFocusChanged(){
		System.out.println("change");
		LinearLayout layout = (LinearLayout) mAct.findViewById(R.id.capture_op_layout);
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)layout.getLayoutParams();
		int margin = (WindowUtil.getHeight(mAct) - (WindowUtil.getWidth(mAct) * 7 / 8) ) / 8;
		lp.setMargins(0, 0, margin, 0);
		layout.setLayoutParams(lp);
	}
		
	public void onFindQrCode(final Bitmap barcodeImg, String barcode){
		QrCodeA.SCAN_BITMAP = barcodeImg;
		final QrCode qrcode = new QrCode();
		qrcode.setText(barcode);
		qrcode.setTime(Calendar.getInstance().getTimeInMillis());
		//save bitmap
		new Thread(){
			public void run() {
				System.out.println(barcodeImg.getWidth());
				Matrix m = new Matrix();
				m.setRotate(90);
				Bitmap bm = Bitmap.createBitmap(barcodeImg, 0, 0, barcodeImg.getWidth(), barcodeImg.getHeight(), m, false);
				BitmapUtil.save(bm, FileMgr.getInstance(mAct).getScan(qrcode.getTime()));
			};
		}.start();
		//
		mAct.startActivity(new Intent(mAct, QrCodeA.class).putExtra(QrCodeA.EXTRA_QRCODE, qrcode));
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.capture_home:
			onClickHome();
			break;
		case R.id.capture_light:
			onClickLight();
		}
	}
	
	private void onClickHome(){
		mAct.startActivity(new Intent(mAct, HomeA.class));
	}

	public void onClickLight(){
		if(mCameraMgr.getTorch()){
			vLight.setImageResource(R.drawable.capture_light_normal);
			mCameraMgr.setTorch(false);
		}else{
			vLight.setImageResource(R.drawable.capture_light_pressed);
			mCameraMgr.setTorch(true);
		}
	}
}
