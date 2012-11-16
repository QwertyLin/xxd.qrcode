package cn.xxd.qr.bean;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import q.util.SqliteBase;

public class QrCodeDb extends SqliteBase<QrCode> {

	public QrCodeDb(Context context) {
		super(context, "db", "history");
	}

	@Override
	protected String getSqlCreateTable() {
		StringBuffer sb = new StringBuffer("CREATE TABLE history ("); 
		sb.append("id INTEGER PRIMARY KEY,"); 
		sb.append("text TEXT,"); 
		sb.append("time INTEGER,");
		sb.append("favorite INTEGER)");
		return sb.toString();
	}

	@Override
	protected ContentValues buildContentValues(QrCode e) {
		ContentValues cv = new ContentValues();
		cv.put("text", e.getText());
		cv.put("time", e.getTime());
		if(e.isFavorite()){
			cv.put("favorite", 1);
		}else{
			cv.put("favorite", 0);
		}
		return cv;
	}

	@Override
	protected QrCode buildEntity(Cursor cs) {
		QrCode item = new QrCode();
		item.setId(cs.getLong(0));
		item.setText(cs.getString(1));
		item.setTime(cs.getLong(2));
		if(cs.getInt(3) == 1){
			item.setFavorite(true);
		}
		return item;
	}
	
	public List<QrCode> queryAll() {
		//Cursor cs = db.query(DB_TABLE, new String[] { KEY_ID, KEY_DATA }, null, null, null, null, null);
		Cursor cs = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY id DESC", null);
		List<QrCode> es = new ArrayList<QrCode>();
		while(cs.moveToNext()){
			es.add(buildEntity(cs));
		}
		return es;
	}

}
