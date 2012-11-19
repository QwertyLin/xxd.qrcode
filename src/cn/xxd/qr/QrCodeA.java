package cn.xxd.qr;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import cn.xxd.qr.bean.HistoryDb;
import cn.xxd.qr.bean.QrCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;

import q.util.FileMgr;
import q.util.QConfig;
import q.util.QUI;
import q.util.WindowMgr;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QrCodeA extends Activity implements OnClickListener {
	
	public static final String EXTRA_QRCODE = "qrcode";
	public static final String EXTRA_FROM_SCAN = "scan", EXTRA_FROM_FAVORITE = "favorite";
	public static Bitmap SCAN_BITMAP;
	
	private QrCode qrcode;
	private ImageView ivImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_qrcode);
		QUI.baseHeaderBack(this, "");
		int height = WindowMgr.getInstance(this).getHeight();
		//ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, RESULT);
		//
		Intent intent = getIntent();
		qrcode = (QrCode) intent.getSerializableExtra(EXTRA_QRCODE);
		//
		boolean isFromScan = intent.getBooleanExtra(EXTRA_FROM_SCAN, false);
		initImage(isFromScan);
		if(isFromScan){// save only from scan activity
			save(qrcode);
		}
		//
		ParsedResult result = ResultParser.parseResult(qrcode.getText());
		System.out.println(result.getType());
		//
		ivImage = (ImageView)findViewById(R.id.qrcode_img);
		LinearLayout.LayoutParams ivlp = (LinearLayout.LayoutParams)ivImage.getLayoutParams();
		ivlp.width = height / 3;
		ivlp.height = height / 3;
		ivImage.setLayoutParams(ivlp);
		((TextView)findViewById(R.id.qrcode_text)).setText(qrcode.getText());
		
		findViewById(R.id.qrcode_copy).setOnClickListener(this);
		switch(result.getType()){
		case URI:
			View vWebsie = findViewById(R.id.qrcode_website);
			vWebsie.setVisibility(View.VISIBLE);
			vWebsie.setOnClickListener(this);
			break;
		}
		//
	}
	
	private void save(QrCode qrcode){
		HistoryDb db = new HistoryDb(this);
		db.open(true);
		db.insert(qrcode);
		db.close();
	}
	
	private void initImage(final boolean isFromScan){
		new Thread(){
			public void run() {
				if(isFromScan){
					Matrix m = new Matrix();
					m.setRotate(90);
					final Bitmap bm = Bitmap.createBitmap(SCAN_BITMAP, 0, 0, SCAN_BITMAP.getWidth(), SCAN_BITMAP.getHeight(), m, false);
					SCAN_BITMAP = null;
					runOnUiThread(new Runnable() {
						@Override	
						public void run() {
							ivImage.setImageBitmap(bm);
						}
					});
				}else{
					File file = new File(FileMgr.getInstance(QrCodeA.this).getScan(qrcode.getTime()));
					System.out.println(file.getPath());
					if(file.exists() && file.length() > 0){
						final Bitmap bm = BitmapFactory.decodeFile(file.getPath());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ivImage.setImageBitmap(bm);
							}
						});
					}else{
						try {
							final Bitmap bm = initImageEncode();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ivImage.setImageBitmap(bm);
								}
							});
						} catch (WriterException e) {
							e.printStackTrace();
						}
					}
				}
			};
		}.start();
	}
	
	private Bitmap initImageEncode() throws WriterException{
		 WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    int widthS = display.getWidth();
	    int heightS = display.getHeight();
	    int smallerDimension = widthS < heightS ? widthS : heightS;
	    smallerDimension = smallerDimension * 7 / 8;
	    //
	    String contentsToEncode = qrcode.getText();
	    if (contentsToEncode == null) {
	      return null;
	    }
	    Map<EncodeHintType,Object> hints = null;
	    String encoding = guessAppropriateEncoding(contentsToEncode);
	    if (encoding != null) {
	      hints = new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
	      hints.put(EncodeHintType.CHARACTER_SET, encoding);
	    }
	    MultiFormatWriter writer = new MultiFormatWriter();
	    BitMatrix result = null;
	    try {
	      result = writer.encode(contentsToEncode, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
	    } catch (IllegalArgumentException iae) {
	      // Unsupported format
	    	return null;
	    }
	    int width = result.getWidth();
	    int height = result.getHeight();
	    int[] pixels = new int[width * height];
	    for (int y = 0; y < height; y++) {
	      int offset = y * width;
	      for (int x = 0; x < width; x++) {
	    	  //TODO QR图颜色
	    	  pixels[offset + x] = result.get(x, y) ? QConfig.QR_IMAGE_FRONT_COLOR : QConfig.QR_IMAGE_BACK_COLOR; //pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
	      }
	    }
	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bitmap;
	}
	
	private static String guessAppropriateEncoding(CharSequence contents) {
	    // Very crude at the moment
	    for (int i = 0; i < contents.length(); i++) {
	      if (contents.charAt(i) > 0xFF) {
	        return "UTF-8";
	      }
	    }
	    return null;
	  }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.qrcode_copy:
			copy(qrcode.getText());
			break;
		case R.id.qrcode_website:
			openURL(qrcode.getText());
			break;
		case R.id.qrcode_download:
			break;
		}
	}
	
	private void copy(String text){
		Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(text);
	}
	
	private void openURL(String url) {
	    // Strangely, some Android browsers don't seem to register to handle HTTP:// or HTTPS://.
	    // Lower-case these as it should always be OK to lower-case these schemes.
	    if (url.startsWith("HTTP://")) {
	      url = "http" + url.substring(4);
	    } else if (url.startsWith("HTTPS://")) {
	      url = "https" + url.substring(5);
	    }
	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
	
}
