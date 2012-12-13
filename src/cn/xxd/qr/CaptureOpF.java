package cn.xxd.qr;

import com.squareup.otto.Subscribe;

import q.util.OttoHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CaptureOpF extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_footer, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		OttoHelper.get().register(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		OttoHelper.get().unregister(this);
	}
	
	@Subscribe
	public void onCaptureOperaChange(CaptureOperaEvent event){
		System.out.println("111111111");
		
		
		
		/*mAct.getSupportFragmentManager().beginTransaction()
		.add(R.id.capture_opera_layout, new CaptureOpF())
		.commit();*/
	}
	
	public static class CaptureOperaEvent {
		
		private FragmentActivity act;
		private CaptureOpF f;
		
		public CaptureOperaEvent(FragmentActivity act, CaptureOpF f) {
			this.act = act;
			this.f = f;
		}
	}
}
