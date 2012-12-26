package q.util;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

public class IntentUtil {
	
	public static final void openBrowser(Context ctx, String url) throws Exception{
		if (url.startsWith("HTTP://")) {
	      url = "http" + url.substring(4);
	    } else if (url.startsWith("HTTPS://")) {
	      url = "https" + url.substring(5);
	    }
		try {
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public static final void openMarket(Context ctx) throws Exception{
		Intent intent = new Intent(Intent.ACTION_VIEW)
		.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		.setData(Uri.parse("market://details?id=" + ctx.getPackageName()));
		try {
			ctx.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
		
	public static final void sendImage(Context ctx, String text, String imagePath) {
		Intent intent = new Intent(Intent.ACTION_SEND).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setType("image/*")//.setType("text/plain")
				.putExtra(Intent.EXTRA_TEXT, text);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
		//
		ctx.startActivity(Intent.createChooser(intent, ""));
	}
	
	/**
	 * 打开相册，返回数据调用resultBitmap()
	 */
	public static final void contentImage(Activity act, int requestCode){
		act.startActivityForResult(
				new Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE).setType("image/*"), 
				requestCode);
	}
	
	/**
	 * 打开相机，返回数据调用resultBitmap()
	 */
	public static final void mediaCamera(Activity act, int requestCode){
		act.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), requestCode);
	}
	
	/**
	 * 返回Bitmap
	 */
	public static final Bitmap resultBitmap(Context ctx, Intent data){
		if(data != null){
			//2种方式
			try {
				return BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(data.getData()));
			} catch (Exception e) {
				e.printStackTrace();
				return data.getParcelableExtra("data");
			}
		}
		return null;
	}
	
	/**
	 * 调用谷歌地图app或在浏览器打开
	 * 
	 * @param lat 纬度
	 * @param lng 经度
	 * @param name 地名
	 */
	public static final void mapGoogle(Context ctx, String lat, String lng, String name){
		boolean isInstallGMap = false;
		List<PackageInfo> packs = ctx.getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if (p.versionName == null) { // system packages
				continue;
			}
			if ("com.google.android.apps.maps".equals(p.packageName)) {
				isInstallGMap = true;
				break;
			}
		}
		if (isInstallGMap) {
			Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lng + "?q=" + (name==null?"":name) ));
			map.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			map.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			ctx.startActivity(map);
		} else {
			Intent it = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q="
							+ lat + "," + lng));
			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			ctx.startActivity(it);
		}
	}

public static class Open {
		
		public static void openFile(Context context,File file){   
	    try{  
	        Intent intent = new Intent();   
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        //设置intent的Action属性    
	        intent.setAction(Intent.ACTION_VIEW);   
	        //获取文件file的MIME类型    
	        String type = getMIMEType(file);   
	        //设置intent的data和Type属性。    
	        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);   
	        //跳转    
	        context.startActivity(intent);     
//	      Intent.createChooser(intent, "请选择对应的软件打开该附件！");   
	        }catch (ActivityNotFoundException e) {  
	            // TODO: handle exception   
	            Toast.makeText(context, "sorry附件不能打开，请下载相关软件！", 500).show();  
	        }  
	    }   
		
		private static String getMIMEType(File file) {   
	           
	        String type="*/*";   
	        String fName = file.getName();   
	        //获取后缀名前的分隔符"."在fName中的位置。    
	        int dotIndex = fName.lastIndexOf(".");   
	        if(dotIndex < 0){   
	            return type;   
	        }   
	        /* 获取文件的后缀名*/   
	        String end=fName.substring(dotIndex,fName.length()).toLowerCase();   
	        if(end=="")return type;   
	        //在MIME和文件类型的匹配表中找到对应的MIME类型。    
	        for(int i=0;i<MIME_MapTable.length;i++){   
	 
	        if(end.equals(MIME_MapTable[i][0]))   
	            type = MIME_MapTable[i][1];   
	    }          
	    return type;   
	}   

		private static String [][]  MIME_MapTable={   
	            //{后缀名，MIME类型}    
	            {".3gp",    "video/3gpp"},   
	            {".apk",    "application/vnd.android.package-archive"},   
	            {".asf",    "video/x-ms-asf"},   
	            {".avi",    "video/x-msvideo"},   
	            {".bin",    "application/octet-stream"},   
	            {".bmp",    "image/bmp"},   
	            {".c",  "text/plain"},   
	            {".class",  "application/octet-stream"},   
	            {".conf",   "text/plain"},   
	            {".cpp",    "text/plain"},   
	            {".doc",    "application/msword"},   
	            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},   
	            {".xls",    "application/vnd.ms-excel"},    
	            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},   
	            {".exe",    "application/octet-stream"},   
	            {".gif",    "image/gif"},   
	            {".gtar",   "application/x-gtar"},   
	            {".gz", "application/x-gzip"},   
	            {".h",  "text/plain"},   
	            {".htm",    "text/html"},   
	            {".html",   "text/html"},   
	            {".jar",    "application/java-archive"},   
	            {".java",   "text/plain"},   
	            {".jpeg",   "image/jpeg"},   
	            {".jpg",    "image/jpeg"},   
	            {".js", "application/x-javascript"},   
	            {".log",    "text/plain"},   
	            {".m3u",    "audio/x-mpegurl"},   
	            {".m4a",    "audio/mp4a-latm"},   
	            {".m4b",    "audio/mp4a-latm"},   
	            {".m4p",    "audio/mp4a-latm"},   
	            {".m4u",    "video/vnd.mpegurl"},   
	            {".m4v",    "video/x-m4v"},    
	            {".mov",    "video/quicktime"},   
	            {".mp2",    "audio/x-mpeg"},   
	            {".mp3",    "audio/x-mpeg"},   
	            {".mp4",    "video/mp4"},   
	            {".mpc",    "application/vnd.mpohun.certificate"},          
	            {".mpe",    "video/mpeg"},     
	            {".mpeg",   "video/mpeg"},     
	            {".mpg",    "video/mpeg"},     
	            {".mpg4",   "video/mp4"},      
	            {".mpga",   "audio/mpeg"},   
	            {".msg",    "application/vnd.ms-outlook"},   
	            {".ogg",    "audio/ogg"},   
	            {".pdf",    "application/pdf"},   
	            {".png",    "image/png"},   
	            {".pps",    "application/vnd.ms-powerpoint"},   
	            {".ppt",    "application/vnd.ms-powerpoint"},   
	            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},   
	            {".prop",   "text/plain"},   
	            {".rc", "text/plain"},   
	            {".rmvb",   "audio/x-pn-realaudio"},   
	            {".rtf",    "application/rtf"},   
	            {".sh", "text/plain"},   
	            {".tar",    "application/x-tar"},      
	            {".tgz",    "application/x-compressed"},    
	            {".txt",    "text/plain"},   
	            {".wav",    "audio/x-wav"},   
	            {".wma",    "audio/x-ms-wma"},   
	            {".wmv",    "audio/x-ms-wmv"},   
	            {".wps",    "application/vnd.ms-works"},   
	            {".xml",    "text/plain"},   
	            {".z",  "application/x-compress"},   
	            {".zip",    "application/x-zip-compressed"},   
	            {"",        "*/*"}     
	        };   
	}
}
