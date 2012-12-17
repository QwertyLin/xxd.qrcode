package cn.xxd.qr;

import android.graphics.Bitmap;

public final class CaptureEvent {
	
	/**
	 * 菜单CheckBox选中状态变化。
	 * 如果菜单布局处于打开状态，关闭菜单；否则打开菜单。
	 */
	public static final class SwitchMenu {
		public final boolean isChecked;
		public SwitchMenu(boolean isChecked){
			this.isChecked = isChecked;
		}
	}
	
	/**
	 * 物理按钮返回键。
	 * 如果菜单布局处于打开状态，关闭菜单；否则退出。
	 */
	public static final class ClickKeyBack {}
	
	/**
	 * 物理按钮菜单键。
	 * 如果菜单布局处于打开状态，关闭菜单；否则打开菜单。
	 * @param event
	 */
	public static final class ClickKeyMenu {}
	
	/**
	 * 隐藏菜单布局开始阶段。
	 * 动画效果。
	 */
	public static final class HideMenuStart {}
	/**
	 * 隐藏菜单布局结束阶段。
	 * 卸载布局。
	 */
	public static final class HideMenuFinish {}
	
	public static final class FindQrCode {
		public final Bitmap barcodeImg;
		public final String barcode;
		public FindQrCode(Bitmap barcodeImg, String barcode){
			this.barcodeImg = barcodeImg;
			this.barcode = barcode;
		}
	}
	
	public static final class SwitchFlash {}
	
	

}
