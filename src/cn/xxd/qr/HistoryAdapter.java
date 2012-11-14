package cn.xxd.qr;

import java.util.List;

import android.content.Context;
import android.view.View;
import q.util.AdapterBase;

public class HistoryAdapter extends AdapterBase<String, HistoryAdapter.Holder> {

	public HistoryAdapter(Context ctx, List<String> datas) {
		super(ctx, datas);
		// TODO Auto-generated constructor stub
	}

	class Holder {
		
	}

	@Override
	protected int getLayoutId() {
		return R.layout.history_item;
	}

	@Override
	protected Holder getHolder(View v) {
		return new Holder();
	}

	@Override
	protected void initItem(int position, String data, Holder holder) {
		// TODO Auto-generated method stub
		
	}
	
}
