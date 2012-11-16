package cn.xxd.qr;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.bean.QrCodeDb;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;

import q.util.FileMgr;
import q.util.QConfig;
import q.util.QUI;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QrCodeA extends Activity implements OnClickListener {
	
	public static final String EXTRA_QRCODE = "qrcode";
	public static final String EXTRA_FROM_SCAN = "scan", EXTRA_FROM_HISTORY = "history";
	public static Bitmap SCAN_BITMAP;
	
	private QrCode qrcode;
	private ImageView ivImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode);
		QUI.baseHeaderBackDelete(this, "");
		//
		//ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, RESULT);
		Intent intent = getIntent();
		qrcode = (QrCode) intent.getSerializableExtra(EXTRA_QRCODE);
		//
		initImage(intent);
		addToHistory();
		//
		ParsedResult result = ResultParser.parseResult(qrcode.getText());
		System.out.println(result.getType());
		//((ImageView) findViewById(R.id.qrcode_img));
		ivImage = (ImageView)findViewById(R.id.qrcode_img);
		((TextView)findViewById(R.id.qrcode_text)).setText(qrcode.getText());
		
		findViewById(R.id.qrcode_copy).setOnClickListener(this);
		findViewById(R.id.qrcode_favorite).setOnClickListener(this);
		switch(result.getType()){
		case URI:
			View vWebsie = findViewById(R.id.qrcode_website);
			vWebsie.setVisibility(View.VISIBLE);
			vWebsie.setOnClickListener(this);
			break;
		}
		//
	}
	
	private void initImage(final Intent intent){
		new Thread(){
			public void run() {
				if(intent.getBooleanExtra(EXTRA_FROM_SCAN, false)){
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
							// TODO Auto-generated catch block
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
	
	private void addToHistory(){
		QrCodeDb db = new QrCodeDb(this);
		db.open(true);
		db.insert(qrcode);
		db.close();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.qrcode_copy:
			copy(qrcode.getText());
			break;
		case R.id.qrcode_favorite:
			favorite();
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
	
	private void favorite(){
		Toast.makeText(this, "已添加到收藏", Toast.LENGTH_SHORT).show();
		QrCodeDb db = new QrCodeDb(this);
		db.open(true);
		qrcode.setFavorite(true);
		db.update(qrcode);
		db.close();
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
