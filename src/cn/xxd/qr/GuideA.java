package cn.xxd.qr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GuideA extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guide);
		//
		GuidePagerAdapter adapter = new GuidePagerAdapter(getSupportFragmentManager());
		ViewPager pager = (ViewPager)findViewById(R.id.guide_pager);
		pager.setAdapter(adapter);
	}
	
}

class GuidePagerAdapter extends FragmentPagerAdapter {

	public GuidePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return new GuidePager();
	}

	@Override
	public int getCount() {
		return 2;
	}
	
}

class GuidePager extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_guide_capture, null);
		//View v = inflater.inflate(R.layout.layout_setting, null);
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("a", 0);
	}
}
