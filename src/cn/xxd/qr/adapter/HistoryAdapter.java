package cn.xxd.qr.adapter;

import java.util.List;

import q.base.AdapterBase;

import cn.xxd.qr.R;
import cn.xxd.qr.R.id;
import cn.xxd.qr.R.layout;
import cn.xxd.qr.bean.QrCode;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class HistoryAdapter extends AdapterBase<QrCode, HistoryAdapter.Holder> {

	public HistoryAdapter(Context ctx, List<QrCode> datas) {
		super(ctx, datas);
	}

	class Holder {
		TextView tvText;
		TextView tvTime;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.layout_history_item;
	}

	@Override
	protected Holder getHolder(View v) {
		Holder h = new Holder();
		h.tvText = (TextView)v.findViewById(R.id.history_item_text);
		h.tvTime = (TextView)v.findViewById(R.id.history_item_time);
		return h;
	}

	@Override
	protected void initItem(int position, QrCode data, Holder holder) {
		holder.tvText.setText(data.getText());
		holder.tvTime.setText(data.getTimeStr());
	}
	
}
