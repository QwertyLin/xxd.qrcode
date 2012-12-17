package cn.xxd.qr;

import java.util.Calendar;

import q.util.BitmapUtil;
import q.util.EventHelper;
import q.util.FileMgr;

import com.google.zxing.client.android.camera.CameraManager;
import com.squareup.otto.Subscribe;

import cn.xxd.qr.R;
import cn.xxd.qr.bean.QrCode;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CaptureA2 {
	
	private FragmentActivity mAct;
	private CameraManager mCameraMgr;
	private CheckBox btnMenu;
	
	public CaptureA2(Context ctx){
		mAct = (FragmentActivity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		btnMenu = (CheckBox)mAct.findViewById(R.id.capture_menu);
		btnMenu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				EventHelper.get().post(new CaptureEvent.SwitchMenu(isChecked));
			}
		});
	}
	
	public void onResume(){
		EventHelper.get().register(this);
	}
	
	public void onPause(){
		EventHelper.get().unregister(this);
	}
	
	@Subscribe
	public void onSwitchMenu(CaptureEvent.SwitchMenu event){
		if(event.isChecked){
			mAct.getSupportFragmentManager().beginTransaction()
			.add(R.id.capture_menu_layout, new CaptureAMenu(mAct.findViewById(R.id.capture_menu_sample).getHeight(), mCameraMgr.getTorch()))
			.commit();
		}else{
			EventHelper.get().post(new CaptureEvent.HideMenuStart());
		}
	}
	
	@Subscribe
	public void onClickKeyBack(CaptureEvent.ClickKeyBack event){
		if(btnMenu.isChecked()){
			btnMenu.setChecked(false);
		}else{
			mAct.finish();
		}
	}
	
	@Subscribe
	public void onClickKeyMenu(CaptureEvent.ClickKeyMenu event){
		btnMenu.setChecked(!(btnMenu.isChecked()));
	}
	
	@Subscribe
	public void onHideMenuFinish(CaptureEvent.HideMenuFinish event){
		mAct.getSupportFragmentManager().beginTransaction()
		.remove(mAct.getSupportFragmentManager().findFragmentById(R.id.capture_menu_layout))
		.commit();
	}
		
	@Subscribe
	public void onFindQrCode(final CaptureEvent.FindQrCode event){
		QrCodeA.SCAN_BITMAP = event.barcodeImg;
		final QrCode qrcode = new QrCode();
		qrcode.setText(event.barcode);
		qrcode.setTime(Calendar.getInstance().getTimeInMillis());
		//save bitmap
		new Thread(){
			public void run() {
				System.out.println(event.barcodeImg.getWidth());
				Matrix m = new Matrix();
				m.setRotate(90);
				Bitmap bm = Bitmap.createBitmap(event.barcodeImg, 0, 0, event.barcodeImg.getWidth(), event.barcodeImg.getHeight(), m, false);
				BitmapUtil.save(bm, FileMgr.getInstance(mAct).getScan(qrcode.getTime()));
			};
		}.start();
		//
		mAct.startActivity(new Intent(mAct, QrCodeA.class).putExtra(QrCodeA.EXTRA_QRCODE, qrcode));
	}
	
	@Subscribe
	public void onSwitchFlash(CaptureEvent.SwitchFlash event){
		mCameraMgr.setTorch(!mCameraMgr.getTorch());
	}


}
