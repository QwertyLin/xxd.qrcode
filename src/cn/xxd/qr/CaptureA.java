package cn.xxd.qr;

import java.util.Calendar;

import q.util.BitmapUtil;
import q.util.FileMgr;

import com.google.zxing.Result;
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
import android.widget.ImageButton;

public class CaptureA implements OnClickListener {
	
	private Activity mAct;
	private CameraManager mCameraMgr;
	
	public CaptureA(Context ctx){
		mAct = (Activity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		//
		mAct.findViewById(R.id.capture_favorite).setOnClickListener(this);
		mAct.findViewById(R.id.capture_setting).setOnClickListener(this);
		btnFlash = (ImageButton)mAct.findViewById(R.id.capture_flash);
		btnFlash.setOnClickListener(this);
		//
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.capture_favorite:
			mAct.startActivity(new Intent(mAct, HistoryA.class));
			break;
		case R.id.capture_setting:
			mAct.startActivity(new Intent(mAct, SettingA.class));
			break;
		case R.id.capture_flash:
			onClickFlash();
			break;
		}
	}
	
	private ImageButton btnFlash;
	private boolean isFlashOn = false;
	private void onClickFlash(){
		isFlashOn = !isFlashOn;
		if(isFlashOn){
			btnFlash.setImageResource(R.drawable.a_device_access_flash_on_x);
		}else{
			btnFlash.setImageResource(R.drawable.a_device_access_flash_off_x);
		}
		mCameraMgr.setTorch(isFlashOn);
	}
	
	public void handleDecode(Result rawResult, final Bitmap barcode){
		QrCodeA.SCAN_BITMAP = barcode;
		final QrCode qrcode = new QrCode();
		qrcode.setText(rawResult.getText());
		qrcode.setTime(Calendar.getInstance().getTimeInMillis());
		//save bitmap
		new Thread(){
			public void run() {
				System.out.println(barcode.getWidth());
				Matrix m = new Matrix();
				m.setRotate(90);
				Bitmap bm = Bitmap.createBitmap(barcode, 0, 0, barcode.getWidth(), barcode.getHeight(), m, false);
				BitmapUtil.save(bm, FileMgr.getInstance(mAct).getScan(qrcode.getTime()));
			};
		}.start();
		//
		mAct.startActivity(new Intent(mAct, QrCodeA.class)
			.putExtra(QrCodeA.EXTRA_FROM_SCAN, true)
			.putExtra(QrCodeA.EXTRA_QRCODE, qrcode));
		
		if(true){
			return;
		}
		
		/*layoutQrcode.setVisibility(View.VISIBLE);
		ivQrcode.setImageBitmap(barcode);
		tvQrcode.setText(resultHandler.getDisplayContents());
		//
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	    String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
	    //
	    for (int x = 0; x < ResultHandler.MAX_BUTTON_COUNT; x++) {
	        System.out.println(mCtx.getResources().getString(resultHandler.getButtonText(x)));
	        //button.setOnClickListener(new ResultButtonListener(resultHandler, x));
	    }
	    //
	    if (!resultHandler.areContentsSecure()) {
	        ClipboardManager clipboard = (ClipboardManager) mCtx.getSystemService(Context.CLIPBOARD_SERVICE);
	        if (resultHandler.getDisplayContents() != null) {
	          clipboard.setText(resultHandler.getDisplayContents());
	        }
	      }*/
	    //
	    /*private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES =
	    	      EnumSet.of(ResultMetadataType.ISSUE_NUMBER,
	    	                 ResultMetadataType.SUGGESTED_PRICE,
	    	                 ResultMetadataType.ERROR_CORRECTION_LEVEL,
	    	                 ResultMetadataType.POSSIBLE_COUNTRY);*/
	    /*Map<ResultMetadataType,Object> metadata = rawResult.getResultMetadata();
	    if (metadata != null) {
	      StringBuilder metadataText = new StringBuilder(20);
	      for (Map.Entry<ResultMetadataType,Object> entry : metadata.entrySet()) {
	        if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
	          metadataText.append(entry.getValue()).append('\n');
	        }
	      }
	      if (metadataText.length() > 0) {
	        metadataText.setLength(metadataText.length() - 1);
	        metaTextView.setText(metadataText);
	        metaTextView.setVisibility(View.VISIBLE);
	        metaTextViewLabel.setVisibility(View.VISIBLE);
	      }
	    }*/
	}

	
}
