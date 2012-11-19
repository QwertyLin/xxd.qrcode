package q.util;

public class QConfig {
	
	public static final String BASE_PACKAGE_NAME = "cn.xxd.qr";
	
	public static final int VIEW_FINDER_MASK_COLOR = 0x60000000; //扫描界面蒙板颜色
	public static final int VIEW_FINDER_LASER_COLOR = 0xFF33B5E5; //扫描界面激光颜色
	public static final int VIEW_FINDER_FRAME_COLOR = 0xFF33B5E5; //扫描界面外框颜色
	
	public static final int QR_SCAN_RESULT_POINT_COLOR = 0xFFFF0000; //扫描结果画在QR图上的寻象点
	
	public static final int QR_IMAGE_FRONT_COLOR = 0xFF33B5E5; //QR图颜色
	public static final int QR_IMAGE_BACK_COLOR = 0xFFFFFFFF;  //QR图底色
	
	//public static final boolean SETTING_DECODE_QR = true; //扫描二维码，一直为true
	public static final boolean SETTING_AUTO_FOCUS = true; //自动对焦
	//public static final boolean SETTING_BULK_MODE = false; //批量扫描模式
	public static final boolean SETTING_PLAY_BEEP = true; //提示音
	public static final boolean SETTING_VIBRATE = true; //振动
	//public static final boolean SETTING_COPY_TO_CLIPBOARD = true; //复制到剪切板
	//public static final boolean SETTING_REMEMBER_DUPLICATES = false; //记住重复，历史记录，无用
	//public static final boolean SETTING_SUPPLEMENTAL = true; //检索更多信息
	public static final String SETTING_CUSTOM_PRODUCT_SEARCH = null; //自定义搜索网址（%s=内容，%f=格式，%t=类型），暂无用
	public static final boolean SETTING_DISABLE_CONTINUOUS_FOCUS = true;//没有持续关注，只使用标准对焦模式
	
	public static final String SP_NAME = "setting";
	public static final String SP_KEY_VERSION = "version";
}
