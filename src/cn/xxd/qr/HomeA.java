package cn.xxd.qr;

import q.base.TabActivityBase;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeA extends TabActivityBase {
	
	public static boolean GO_TO_SCAN = false;//是否调整扫描状态，用于处理RadioGroup.check(0)时再一次跳转到扫描
	public static boolean FINISH_FROM_SCAN = false;//是否从扫描状态退出
	public static int POSITION; //初始位置
	public static final int DEFUALT_POSITION = 3; //默认位置为“关于”
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == 0){
					GO_TO_SCAN = true;
				}else{
					POSITION = checkedId; //选择后重新打开
				}
				tabHost.setCurrentTab(checkedId);
			}
		});
	}
	
	@Override
	protected void onResume() {
		//POSITION不能为0，只能1-3
		if(POSITION == 0){ 
			POSITION = DEFUALT_POSITION;
		}
		//
		radioGroup.check(POSITION);
		super.onResume();
		if(FINISH_FROM_SCAN){
			FINISH_FROM_SCAN = false;
			finish();
			return;
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
		return R.drawable.base_checkbox;
	}

}
