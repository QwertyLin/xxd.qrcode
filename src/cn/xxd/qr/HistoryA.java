package cn.xxd.qr;

import java.util.ArrayList;
import java.util.List;

import q.view.UnderlinePageIndicator;


import android.app.Activity;
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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HistoryA extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		final ViewPager pager = (ViewPager)findViewById(R.id.history_pager);
		pager.setAdapter(new HistoryPagerAdapter(getSupportFragmentManager()));
		
		
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

	public HistoryPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return new HistoryPager(position);
	}

	@Override
	public int getCount() {
		return 2;
	}
	
}

class HistoryPager extends Fragment {
	
	private int mPosition;
	
	public HistoryPager(int position){
		mPosition = position;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			mPosition = savedInstanceState.getInt("position");
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Activity act = getActivity();
		ListView lv = (ListView)inflater.inflate(R.layout.base_list, null);
		List<String> datas = new ArrayList<String>();
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");datas.add("a");
		lv.setAdapter(new HistoryAdapter(act, datas));
		return lv;
	}
}
