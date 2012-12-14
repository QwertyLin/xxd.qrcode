package cn.xxd.qr;

import cn.xxd.qr.bean.OttoEvent;

import com.squareup.otto.Subscribe;

public final class CaptureHandle {

	/**
	 * 菜单CheckBox选中状态变化。
	 * 如果菜单布局处于打开状态，关闭菜单；否则打开菜单。
	 * @param event
	 */
	@Subscribe
	public final void onMenuCheckedChange(OttoEvent.CaptureMenuCheckedChange event){		
		if(event.isChecked){
			event.act.getSupportFragmentManager().beginTransaction()
			.add(R.id.capture_menu_layout, new CaptureMenuF(event.act.findViewById(R.id.capture_menu_sample).getHeight()))
			.commit();
		}else{
			event.act.getSupportFragmentManager().beginTransaction()
			.remove(event.act.getSupportFragmentManager().findFragmentById(R.id.capture_menu_layout))
			.commit();
		}
	}
	
	/**
	 * 物理按钮菜单键。
	 * 如果菜单布局处于打开状态，关闭菜单；否则打开菜单。
	 * @param event
	 */
	@Subscribe
	public final void onMenuKeyClick(OttoEvent.CaptureMenuKeyClick event){
		event.btnMenu.setChecked(!(event.btnMenu.isChecked()));
	}
	
	/**
	 * 物理按钮返回键。
	 * 如果菜单布局处于打开状态，关闭菜单；否则退出。
	 * @param event
	 */
	@Subscribe
	public final void onBackKeyClick(OttoEvent.CaptureBackKeyClick event){
		if(event.btnMenu.isChecked()){
			event.btnMenu.setChecked(false);
		}else{
			event.act.finish();
		}
	}
}
