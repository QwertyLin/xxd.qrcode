package cn.xxd.qr.service;

import java.util.EnumMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeEncodeService {

	public static final Bitmap encode(Context ctx, String text, int color) throws WriterException{
		 WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    int widthS = display.getWidth();
	    int heightS = display.getHeight();
	    int smallerDimension = widthS < heightS ? widthS : heightS;
	    smallerDimension = smallerDimension * 7 / 8;
	    //
	    String contentsToEncode = text;
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
	    	  pixels[offset + x] = result.get(x, y) ? color : 0xFFFFFFFF; //pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
	      }
	    }
	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bitmap;
	}
	
	private static String guessAppropriateEncoding(CharSequence contents) {
	    for (int i = 0; i < contents.length(); i++) {
	      if (contents.charAt(i) > 0xFF) {
	        return "UTF-8";
	      }
	    }
	    return null;
	  }
}
