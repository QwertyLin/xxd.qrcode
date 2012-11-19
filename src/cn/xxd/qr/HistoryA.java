package cn.xxd.qr;

import java.util.List;

import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.bean.HistoryDb;

import q.util.QUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HistoryA extends Activity implements OnItemClickListener {

	public static List<QrCode> DATAS;
	private HistoryAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history);
		//
		QUI.baseHeaderBack(this, "扫描历史");
		//
		HistoryDb db = new HistoryDb(this);
		db.open(false);
		DATAS = db.queryAll(1);
		db.close();
		//
        ListView lv = (ListView)findViewById(R.id.base_list);
        adapter = new HistoryAdapter(this, DATAS);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(HistoryA.this, QrCodeA.class)
		.putExtra(QrCodeA.EXTRA_FROM_FAVORITE, true)
		.putExtra(QrCodeA.EXTRA_QRCODE, DATAS.get(position)));
	}
}