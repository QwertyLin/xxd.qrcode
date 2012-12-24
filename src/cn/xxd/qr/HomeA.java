package cn.xxd.qr;

import cn.xxd.qr.util.QSp;
import q.base.TabActivityBase;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class HomeA extends TabActivityBase {
	
	public static int TAB_HEIGHT;
	
	public static boolean GO_TO_SCAN = false;//是否调整扫描状态，用于处理RadioGroup.check(0)时再一次跳转到扫描
	public static boolean FINISH_FROM_SCAN = false;//是否从扫描状态退出
	public static int POSITION; //初始位置
	public static final int DEFUALT_POSITION = 3; //默认位置为“关于”
	
	public static HomeA HOME_A;
	
	public RadioGroup getRadioGroup(){
		return radioGroup;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		if(QSp.getUpdateUrl(this) != null){
			radioGroup.check(3); //有更新是跳到关于
		}else{
			radioGroup.check(1);
		}
	}	
	
	@Override
	protected int getCount() {
		return 4;
	}

	@Override
	protected Intent getIntent(int position) {
		switch(position){
		case 0: return new Intent(this, ScanA.class);
		case 1: return new Intent(this, HistoryA.class);
		case 2: return new Intent(this, NewA.class);
		case 3: return new Intent(this, AboutA.class);
		}
		return null;
	}

	@Override
	protected String getBtnText(int position) {
		switch(position){
		case 0: return getString(R.string.home_scan);
		case 1: return getString(R.string.home_history);
		case 2: return getString(R.string.home_new);
		case 3: return getString(R.string.home_about);
		}
		return null;
	}

	@Override
	protected int getBtnDrawable(int position) {
		switch (position) {
		case 0:
			return R.drawable.home_camera;
		case 1:
			return R.drawable.home_history;
		case 2:
			return R.drawable.home_new;
		case 3:
			return R.drawable.home_about;
		}
		return 0;
	}
	
	

}
