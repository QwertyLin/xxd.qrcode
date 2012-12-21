package cn.xxd.qr;

import java.util.Calendar;

import q.util.BitmapUtil;
import q.util.FileMgr;

import com.google.zxing.client.android.camera.CameraManager;
import cn.xxd.qr.R;
import cn.xxd.qr.bean.QrCode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.View.OnClickListener;

public class CaptureA2 implements OnClickListener {
	
	private Activity mAct;
	private CameraManager mCameraMgr;
	
	public CaptureA2(Context ctx){
		mAct = (Activity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		mAct.findViewById(R.id.capture_home).setOnClickListener(this);
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
	
	public void onSwitchFlash(){
		mCameraMgr.setTorch(!mCameraMgr.getTorch());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.capture_home:
			onClickHome();
			break;
		}
	}
	
	private void onClickHome(){
		mAct.startActivity(new Intent(mAct, HomeA.class));
	}


}
