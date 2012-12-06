package cn.xxd.qr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import cn.xxd.qr.bean.HistoryDb;
import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.service.QrCodeEncodeService;

import com.google.zxing.WriterException;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import q.base.ActivityBase;
import q.base.UiBaseHeader;
import q.util.FileMgr;
import q.util.IntentUtil;
import q.util.QLog;
import q.util.WindowMgr;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QrCodeA extends ActivityBase implements OnClickListener {
	
	public static final String EXTRA_QRCODE = "qrcode";
	public static Bitmap SCAN_BITMAP;
	
	private QrCode qrcode;
	
	private State state;
	private Bitmap buildBitmap;
	private FileMgr fileMgr;
	private ImageView ivImage;
	
	private enum State {
		IMAGE_SCAN, IMAGE_SRC, IMAGE_BUILD
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		UiBaseHeader.btnBack(this, getString(R.string.qrcode_title));
		addToBaseLayout(getLayoutInflater().inflate(R.layout.layout_qrcode, null));
		//
		int height = WindowMgr.getInstance(this).getHeight();
		//
		fileMgr = FileMgr.getInstance(QrCodeA.this);
		//
		Intent intent = getIntent();
		qrcode = (QrCode) intent.getSerializableExtra(EXTRA_QRCODE);
		initState(intent);
		//
		//
		ivImage = (ImageView)findViewById(R.id.qrcode_img);
		LinearLayout.LayoutParams ivlp = (LinearLayout.LayoutParams)ivImage.getLayoutParams();
		ivlp.width = height / 2;
		ivlp.height = height / 2;
		ivImage.setLayoutParams(ivlp);
		((TextView)findViewById(R.id.qrcode_text)).setText(qrcode.getText());
		//
		findViewById(R.id.qrcode_copy).setOnClickListener(this);
		findViewById(R.id.qrcode_share).setOnClickListener(this);
		//
		ParsedResult result = ResultParser.parseResult(qrcode.getText());
		System.out.println(result.getType());
		switch(result.getType()){
		case URI:
			View vWebsie = findViewById(R.id.qrcode_website);
			vWebsie.setVisibility(View.VISIBLE);
			vWebsie.setOnClickListener(this);
			break;
		}
		//统计
		QLog.event(this, QLog.EVENT_SCAN, qrcode.getText());
	}
		
	private void initState(Intent intent){
		if(qrcode.getId() == 0){
			state = State.IMAGE_SCAN;
			initImageScan();
		}else{
			File imageFile = new File(fileMgr.getScan(qrcode.getTime()));
			if(imageFile.exists() && imageFile.length() > 0){
				state = State.IMAGE_SRC;
				initImageSrc(imageFile);
			}else{
				state = State.IMAGE_BUILD;
				initImageBuild();
			}
		}
	}
	
	private void initImageScan(){
		new Thread(){
			public void run() {
				// save only from scan activity
				HistoryDb db = new HistoryDb(QrCodeA.this);
				db.open(true);
				db.insert(qrcode);
				db.close();
				//
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
			}
		}.start();
	}
	
	private void initImageSrc(final File imageFile){
		new Thread(){
			public void run() {
				final Bitmap bm = BitmapFactory.decodeFile(imageFile.getPath());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ivImage.setImageBitmap(bm);
					}
				});
			}
		}.start();
	}
	
	private void initImageBuild(){
		new Thread(){
			public void run() {
				try {
					buildBitmap = QrCodeEncodeService.encode(QrCodeA.this, qrcode.getText(), 0xFF000000);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ivImage.setImageBitmap(buildBitmap);
						}
					});
				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.qrcode_copy:
			onClickCopy();
			break;
		case R.id.qrcode_share:
			onClickShare();
			break;
		case R.id.qrcode_website:
			onClickWebsite();
			break;
		}
	}
	
	private void onClickCopy(){
		Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(qrcode.getText());
		//
		QLog.event(this, QLog.EVENT_SCAN_COPY, "");
	}
	
	private void onClickShare(){
		String imagePath = null;
		//
		if(state == State.IMAGE_BUILD){
			File temp = new File(fileMgr.getScan(0));
			try {
				buildBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(temp));
				imagePath = temp.getAbsolutePath();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			imagePath = fileMgr.getScan(qrcode.getTime());
		}
		//
		IntentUtil.sendImage(this, "小小二维码分享：" + qrcode.getText(), imagePath);
		//
		QLog.event(this, QLog.EVENT_SCAN_SHARE, "");
	}
	
	private void onClickWebsite(){
		IntentUtil.openBrowser(this, qrcode.getText());
		//
		QLog.event(this, QLog.EVENT_SCAN_BROWSER, qrcode.getText());
	}
	
}
