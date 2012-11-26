package cn.xxd.qr;

import java.util.ArrayList;
import java.util.List;

import cn.xxd.qr.adapter.ColorAdapter;
import cn.xxd.qr.bean.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import q.util.ActivityDialogBase;

public class ColorDialog extends ActivityDialogBase implements OnItemClickListener {
	
	public static final String EXTRA_COLOR = "color";
	
	private List<Color> colors;
	
	private void initColorValue(){
		colors = new ArrayList<Color>();
		colors.add(new Color("黑色", 0xff000000));
		colors.add(new Color("白色", 0xffffffff));
		colors.add(new Color("暗蓝", 0xff0099CC));
		colors.add(new Color("亮蓝", 0xff33B5E5));
		colors.add(new Color("暗绿", 0xff669900));
		colors.add(new Color("亮绿", 0xff99CC00));
		colors.add(new Color("暗黄", 0xffFF8800));
		colors.add(new Color("亮黄", 0xffFFBB33));
		colors.add(new Color("暗红", 0xffCC0000));
		colors.add(new Color("亮红", 0xffFF4444));
		colors.add(new Color("暗紫", 0xff9933CC));
		colors.add(new Color("亮紫", 0xffAA66CC));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initColorValue();
		super.onCreate(savedInstanceState);
		//
		vClose.setVisibility(View.VISIBLE);
		vClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected View initView() {
		ListView lv = (ListView) getLayoutInflater().inflate(R.layout.base_list, null);
		lv.setVerticalScrollBarEnabled(false);
		lv.setAdapter(new ColorAdapter(this, colors, getIntent().getIntExtra(EXTRA_COLOR, 0)));
		lv.setOnItemClickListener(this);
		return lv;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		setResult(0, new Intent().putExtra(EXTRA_COLOR, colors.get(position).getColor()));
		finish();
	}
}
