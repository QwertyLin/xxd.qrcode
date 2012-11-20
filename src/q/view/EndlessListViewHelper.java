package q.view;

import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * Init before ListView.setAdapter().
 * Available after setEnable(true).
 *
 */
public class EndlessListViewHelper {
	
	public interface OnEndlessListViewListener {
		boolean onEndlessListViewBackground();
		void onEndlessListViewSuccess();
	}
	
	private OnEndlessListViewListener mListener;
	private ListView mListView;
	private View mViewFooter;
	private boolean mIsEnable, mIsDoing;
	
	public EndlessListViewHelper(ListView listView, View viewFooter, final OnEndlessListViewListener listener){
		mListView = listView;
		mViewFooter = viewFooter;
		mListener = listener;
		listView.addFooterView(viewFooter);
		mIsEnable = true;
		setEnable(false);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(mIsEnable && !mIsDoing && totalItemCount != 0 && firstVisibleItem + visibleItemCount == totalItemCount ){
					System.out.println("onScroll");
					mIsDoing = true;
					new Thread(){
						public void run() {
							if(listener.onEndlessListViewBackground()){
								mHandler.sendEmptyMessage(MSG_SUCCESS);
							}else{
								setEnable(false);
							}
						};
					}.start();
				}
			}
		});
	}
	
	private static final int MSG_SUCCESS = 1, MSG_ENABLE = 3, MSG_DISABLE = 4;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_SUCCESS:
				mListener.onEndlessListViewSuccess();
				break;
			case MSG_ENABLE://enable true
				mListView.removeFooterView(mViewFooter);
				mListView.addFooterView(mViewFooter);
				break;
			case MSG_DISABLE://enable false
				mListView.removeFooterView(mViewFooter);
				break;
			}
			mIsDoing = false;
		};
	};	
	
	public void setEnable(boolean isEnable) {
		if(mIsEnable ^ isEnable){
			mIsEnable = isEnable;
			if(isEnable){
				mHandler.sendEmptyMessage(MSG_ENABLE);
			}else{
				mHandler.sendEmptyMessage(MSG_DISABLE);
			}
			
		}
	}
	
}
