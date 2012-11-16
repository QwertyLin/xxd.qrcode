package cn.xxd.qr;

import java.util.ArrayList;
import java.util.List;

import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.bean.QrCodeDb;

import q.util.QUI;
import q.view.UnderlinePageIndicator;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HistoryA extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		//
		QUI.baseHeaderBack(this, "历史记录");
		//
		
		final ViewPager pager = (ViewPager)findViewById(R.id.history_pager);
		pager.setAdapter(new HistoryPagerAdapter(getSupportFragmentManager(), this));
		
		
		UnderlinePageIndicator indicator = (UnderlinePageIndicator)findViewById(R.id.history_indicator);
        indicator.setViewPager(pager);
        indicator.setSelectedColor(getResources().getColor(R.color.base_blue_dark));
        indicator.setBackgroundColor(getResources().getColor(R.color.base_gray_dark));
        
        final RadioGroup rg = ((RadioGroup)findViewById(R.id.history_rg));
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.history_rg_all){
					pager.setCurrentItem(0);
				}else{
					pager.setCurrentItem(1);
				}
			}
		});
		
        indicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == 0){
					rg.check(R.id.history_rg_all);
				}else{
					rg.check(R.id.history_rg_favorite);
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
        
		/*ListView lv = (ListView)findViewById(R.id.base_list);
		
		List<String> datas = new ArrayList<String>();
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		lv.setAdapter(new HistoryAdapter(this, datas));*/
	}
}

class HistoryPagerAdapter extends FragmentPagerAdapter {
	
	private List<QrCode> datas1, datas2;

	public HistoryPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		QrCodeDb db = new QrCodeDb(ctx);
		db.open(false);
		datas1 = db.queryAll();
		datas2 = new ArrayList<QrCode>();
		for(QrCode item : datas1){
			if(item.isFavorite()){
				datas2.add(item);
			}
		}
		db.close();
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0){
			return new HistoryPager(datas1);
		}else{
			return new HistoryPager(datas2);
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
	
}

class HistoryPager extends Fragment {
	
	private List<QrCode> mDatas;
	
	public HistoryPager(List<QrCode> datas){
		mDatas = datas;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("a", 1);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final Activity act = getActivity();
		ListView lv = (ListView)inflater.inflate(R.layout.base_list, null);
		lv.setAdapter(new HistoryAdapter(act, mDatas));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(act, QrCodeA.class)
				.putExtra(QrCodeA.EXTRA_FROM_HISTORY, true)
				.putExtra(QrCodeA.EXTRA_QRCODE, mDatas.get(position)));
			}
		});
		return lv;
	}
}
