package cn.xxd.qr;

import java.util.List;

import cn.xxd.qr.adapter.HistoryAdapter;
import cn.xxd.qr.bean.QrCode;
import cn.xxd.qr.bean.HistoryDb;

import q.base.ActivityBase;
import q.view.EndlessListViewHelper;
import q.view.EndlessListViewHelper.OnEndlessListViewListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HistoryA extends ActivityBase implements OnClickListener, OnItemClickListener, OnEndlessListViewListener {

	private List<QrCode> datas;
	private HistoryAdapter adapter;
	private int page = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history);
		//
		 ListView lv =(ListView)findViewById(R.id.history_list);
		//
		initData();
		//
		if(datas.size() == 0){
			findViewById(R.id.history_empty).setVisibility(View.VISIBLE);
		}else{
			new EndlessListViewHelper(lv, getLayoutInflater().inflate(R.layout.base_more, null), this).setEnable(true);
	        adapter = new HistoryAdapter(this, datas);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(this);
			//
			findViewById(R.id.history_clean).setOnClickListener(this);
		}
	}
	
	private void initData(){
		HistoryDb db = new HistoryDb(this);
		db.open(false);
		datas = db.queryAll(page);
		db.close();
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.history_clean:
			onClickClean();
			break;
		}
	}
	
	private void onClickClean(){
		new AlertDialog.Builder(this)
		.setMessage(R.string.history_clean)
		.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					public void run() {
						HistoryDb db = new HistoryDb(HistoryA.this);
						db.open(true);
						db.empty();
						db.close();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								datas.clear();
								adapter.notifyDataSetChanged();
							}
						});
					}
				}.start();
			}
		})
		.setNegativeButton(R.string.dialog_cancel, null)
		.show();
		
	}
}