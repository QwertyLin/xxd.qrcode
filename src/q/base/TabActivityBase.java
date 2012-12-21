package q.base;

import cn.xxd.qr.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public abstract class TabActivityBase extends TabActivity implements OnCheckedChangeListener {
	
	protected RadioGroup radioGroup;
	protected TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_tab);
		//
		tabHost = getTabHost();
		//
		for(int i = 0; i < getCount(); i++){
			tabHost.addTab(tabHost.newTabSpec(String.valueOf(i))
					.setIndicator(String.valueOf(i))
					.setContent(getIntent(i)));
		}
		//
		LayoutInflater inflater = getLayoutInflater();
		radioGroup = ((RadioGroup)findViewById(R.id.tabhost_btns_layout));
		RadioButton btn;
		RadioGroup.LayoutParams btnLp = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.WRAP_CONTENT, 1);
		for(int i = 0; i < getCount(); i++){
			btn = (RadioButton)inflater.inflate(R.layout.base_tab_btn, null);
			btn.setId(i);
			btn.setText(getBtnText(i));
			btn.setCompoundDrawablesWithIntrinsicBounds(0, getBtnDrawable(i), 0, 0);
			btn.setLayoutParams(btnLp);
			radioGroup.addView(btn);
		}
		radioGroup.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		tabHost.setCurrentTab(checkedId);
	}
	
	protected abstract int getCount();
	protected abstract Intent getIntent(int position);
	protected abstract String getBtnText(int position);
	protected abstract int getBtnDrawable(int position);
}
