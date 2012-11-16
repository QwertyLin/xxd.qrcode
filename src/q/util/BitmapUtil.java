package q.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;

public class BitmapUtil {
	
	public static final String toString(Bitmap bm){
		StringBuffer sb = new StringBuffer();
		sb.append(" config=" + bm.getConfig());
		sb.append(" width=" + bm.getWidth());
		sb.append(" height=" + bm.getHeight());
		sb.append(" density=" + bm.getDensity());
		sb.append(" rowBytes=" + bm.getRowBytes());
		return sb.toString();
	}
	
	
	/**
	 * 将Drawable转化为Bitmap
	 */
	public static final Bitmap decode(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(new Canvas(bitmap));
		return bitmap;
	}
	
	/**
	 * 将Bitmap编码为byte[]
	 */
	public static final byte[] toByte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
		return baos.toByteArray();
	}
	
	/**
	 * 复制
	 */
	public static final Bitmap copy(Bitmap bitmap) {
		return bitmap.copy(Config.ARGB_8888, true);
	}
	
	/**
	 * 释放内存
	 */
	public static final void recycle(Bitmap bitmap) {
		System.out.println("==recycle==");
		if(bitmap != null && !bitmap.isRecycled()) {
			System.out.println("--true");
			bitmap.recycle();
			bitmap = null;
			System.gc();
		}
	}
	
	/**
	 * 压缩成JPEG
	 * 
	 * @param quality 图片质量，1~100，一般80
	 */
	public static final Bitmap compressJPEG(Bitmap bitmap, int quality) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();//FileOutputStream
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);            
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
	}
	
	/**
	 * 压缩成PNG
	 */
	public static final Bitmap compressPNG(Bitmap bitmap) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);            
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
	}
	
	public static final boolean save(Bitmap bitmap, String path) {
		try {
			FileOutputStream os = new FileOutputStream(path);
			return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
}
