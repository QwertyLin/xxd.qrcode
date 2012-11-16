package q.util;


import java.io.File;


import android.content.Context;
import android.os.Environment;

/**
 * 管理手机内存与SD卡内存中的文件路径
 *
 */
public class FileMgr {
	
	private static FileMgr nInstance;
	
	private FileMgr(Context ctx){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//挂载sd卡
			nRoot = Environment.getExternalStorageDirectory().getPath() + File.separator + "xxd" + File.separator + "qr" + File.separator;
		}else{
			nRoot = ctx.getCacheDir() + File.separator;
		}
		File file = new File(nRoot);
		if(!file.exists()){
			file.mkdirs();
		}
		QLog.kv(this, "init", "root", nRoot);
	}
	
	public static FileMgr getInstance(Context ctx){
		if(nInstance == null){
			synchronized (FileMgr.class) {
				if(nInstance == null){
					nInstance = new FileMgr(ctx);
				}
			}
		}
		return nInstance;
	}
	
	private String nRoot;
	
	private String get(String dir){
		String filePath = nRoot + dir + File.separator;
		File file = new File(filePath);
		if(file.exists() || file.mkdirs()){
			return filePath;
		}else{
			return null;
		}
	}
	
	public String getScan(long time){
		return get("scan") + time + ".jpg";
	}
}
