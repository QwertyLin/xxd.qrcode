package cn.xxd.qr.bean;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import q.util.SqliteBase;

public class HistoryDb extends SqliteBase<QrCode> {
	
	private static final int PAGE_COUNT = 10;

	public HistoryDb(Context context) {
		super(context, "history", "history");
	}

	@Override
	protected String getSqlCreateTable() {
		StringBuffer sb = new StringBuffer("CREATE TABLE " + tableName + " ("); 
		sb.append("id INTEGER PRIMARY KEY,"); 
		sb.append("text TEXT,"); 
		sb.append("time INTEGER)");
		return sb.toString();
	}

	@Override
	protected ContentValues buildContentValues(QrCode e) {
		ContentValues cv = new ContentValues();
		cv.put("text", e.getText());
		cv.put("time", e.getTime());
		return cv;
	}

	@Override
	protected QrCode buildEntity(Cursor cs) {
		QrCode item = new QrCode();
		item.setId(cs.getLong(0));
		item.setText(cs.getString(1));
		item.setTime(cs.getLong(2));
		return item;
	}
	
	public List<QrCode> queryAll() {
		return queryAll(1);
	}
	
	public List<QrCode> queryAll(int page) {
		//Cursor cs = db.query(DB_TABLE, new String[] { KEY_ID, KEY_DATA }, null, null, null, null, null);
		int limit = (page - 1) * PAGE_COUNT;
		Cursor cs = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT " + limit + ", " + PAGE_COUNT, null);
		List<QrCode> es = new ArrayList<QrCode>();
		while(cs.moveToNext()){
			es.add(buildEntity(cs));
		}
		return es;
	}

}
