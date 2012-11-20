package cn.xxd.qr.adapter;

import java.util.List;

import cn.xxd.qr.R;
import cn.xxd.qr.bean.Color;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import q.util.AdapterBase;


public class ColorAdapter extends AdapterBase<Color, ColorAdapter.Holder> {

	public ColorAdapter(Context ctx, List<Color> datas) {
		super(ctx, datas);
	}

	class Holder {
		TextView vName;
		View vColor;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.list_item_color;
	}

	@Override
	protected Holder getHolder(View v) {
		Holder h = new Holder();
		h.vName = (TextView)v.findViewById(R.id.color_name);
		h.vColor = v.findViewById(R.id.color_color);
		return h;
	}

	@Override
	protected void initItem(int position, Color data, Holder holder) {
		holder.vName.setText(data.getName());
		holder.vColor.setBackgroundColor(data.getColor());
	}
	
}
