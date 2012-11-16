package cn.xxd.qr;

import java.util.List;

import cn.xxd.qr.bean.QrCode;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import q.util.AdapterBase;

public class FavoriteAdapter extends AdapterBase<QrCode, FavoriteAdapter.Holder> {

	public FavoriteAdapter(Context ctx, List<QrCode> datas) {
		super(ctx, datas);
	}

	class Holder {
		TextView tvText;
		TextView tvTime;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.history_item;
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
