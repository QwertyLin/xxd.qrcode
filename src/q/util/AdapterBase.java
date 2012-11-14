package q.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *
 * @param <T> 数据荣容器的类型
 * @param <H> 布局容器的类型
 */
public abstract class  AdapterBase<T, H> extends BaseAdapter {
	
	protected List<T> mDatas;
	protected Context mCtx;
	protected LayoutInflater mInflater;
	
	public AdapterBase(Context ctx, List<T> datas) {
		this.mCtx = ctx;
		this.mDatas =  datas;
		this.mInflater = ((Activity)ctx).getLayoutInflater();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		H h = null;
		if(v == null){
			v = mInflater.inflate(getLayoutId(), null);
			h = getHolder(v);
			v.setTag(h);
		}else{
			h = (H)v.getTag();
		}
		initItem(position, mDatas.get(position), h);
		return v;
	}
	
	protected abstract int getLayoutId();
	protected abstract H getHolder(View v);
	protected abstract void initItem(int position, T data, H holder);
}
