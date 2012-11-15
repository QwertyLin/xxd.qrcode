package cn.xxd.qr;

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.client.android.PreferencesActivity;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.result.ResultHandler;

import cn.xxd.qr.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptureA implements OnClickListener {
	
	private Context mCtx;
	private Activity mAct;
	private CameraManager mCameraMgr;
	private ImageView ivQrcode;
	private View layoutQrcode;
	private TextView tvQrcode;
	
	public CaptureA(Context ctx){
		mCtx = ctx;
		mAct = (Activity)ctx;
	}
	
	public void setCameraManager(CameraManager cameraMgr){
		mCameraMgr = cameraMgr;
	}

	public void onCreate(){
		layoutQrcode = mAct.findViewById(R.id.home_qrcode_layout);
		//
		mAct.findViewById(R.id.home_setting).setOnClickListener(this);
		btnFlash = (ImageButton)mAct.findViewById(R.id.home_flash);
		btnFlash.setOnClickListener(this);
		//
		ivQrcode = (ImageView) mAct.findViewById(R.id.home_qrcode_img);
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
	      }
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
	
	public void reset(){
		//layoutQrcode.setVisibility(View.GONE);
	}

	
}
