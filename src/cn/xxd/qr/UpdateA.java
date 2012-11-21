package cn.xxd.qr;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class UpdateA extends Activity {
	
	private ProgressDialog pd;  //进度条
    private File apkFile;  //下载到本地的apk安装文件	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		upgrade();
	}
	
	private void upgrade(){
		//apkFile = QRes.fileAtSDCard(this, "apk.apk");  
		//
		pd = new ProgressDialog(this);   
        pd.setTitle("正在下载...");   
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); 
        pd.setMax(100);
        pd.setProgress(0);
        pd.show();   
        //
        new Thread(task).start();       
	}
	
	private static final int MSG_SUCCESS = 0;
	private static final int MSG_ERROR = 1;
	Runnable task = new Runnable() {	
        public void run() {   
        	/*HttpEntity entity;
        	InputStream in;
        	FileOutputStream out;
			try {
				entity = QHttp.getHttpEntity(ConfigBiz.URL_UPGRADE);
            	in = entity.getContent();
            	out = new FileOutputStream(apkFile);
            	byte[] b = new byte[1024];
            	long length = entity.getContentLength();
            	int count = 0;
            	int readedLength = -1;
            	while( (readedLength = in.read(b)) != -1){
            		out.write(b, 0, readedLength);
                	count += readedLength;
                	pd.setProgress((int)((float)count/length*100));
            	}
            	out.flush();   
                if (out != null) {   
                	out.close();   
                }   
                in.close();
                handler.sendEmptyMessage(MSG_SUCCESS);
        	} catch (Exception e) {
        		handler.sendEmptyMessage(MSG_ERROR);
				e.printStackTrace();
			}*/
        }   
    };   
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			switch(msg.what){
			case MSG_SUCCESS:
				install();   
				finish();
				break;
			case MSG_ERROR:
				new AlertDialog.Builder(UpdateA.this)
				.setTitle("升级失败")
				.setMessage("升级失败，请稍候再试！")
				.setNeutralButton("我知道了", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.show();
			}
		}
	};
    
    private void install() {   
        Intent intent = new Intent(Intent.ACTION_VIEW);   
        intent.setDataAndType(Uri.fromFile(apkFile),   
                "application/vnd.android.package-archive");   
        startActivity(intent);   
    }   

	
}
