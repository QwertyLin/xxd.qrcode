package cn.xxd.qr;

import java.util.List;

import cn.xxd.qr.adapter.HistoryAdapter;
import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.bean.HistoryDb;

import q.base.ActivityBase;
import q.base.UiBaseHeader;
import q.view.EndlessListViewHelper;
import q.view.EndlessListViewHelper.OnEndlessListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HistoryA extends ActivityBase implements OnItemClickListener, OnEndlessListViewListener {

	private List<QrCode> datas;
	private HistoryAdapter adapter;
	private int page = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		//
		UiBaseHeader.text(this, getString(R.string.history_title));
		//
		HistoryDb db = new HistoryDb(this);
		db.open(false);
		datas = db.queryAll(page);
		db.close();
		//
        ListView lv = (ListView)getLayoutInflater().inflate(R.layout.base_list, null);
        addToBaseLayout(lv);
        new EndlessListViewHelper(lv, getLayoutInflater().inflate(R.layout.base_more, null), this).setEnable(true);
        adapter = new HistoryAdapter(this, datas);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(HistoryA.this, QrCodeA.class)
		.putExtra(QrCodeA.EXTRA_QRCODE, datas.get(position)));
	}

	@Override
	public boolean onEndlessListViewBackground() {
		page++;
		HistoryDb db = new HistoryDb(this);
		db.open(false);
		List<QrCode> temp = db.queryAll(page);
		db.close();
		if(temp != null && !temp.isEmpty()){
			datas.addAll(temp);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onEndlessListViewSuccess() {
		adapter.notifyDataSetChanged();
	}
}