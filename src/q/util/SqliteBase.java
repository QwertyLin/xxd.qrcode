package q.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class SqliteBase<T extends SqliteBase.ISqlite> extends SQLiteOpenHelper {
	
	public interface ISqlite {
		long getId();
		void setId(long id);
	}
	
	protected SQLiteDatabase db;
	protected String tableName;

	public SqliteBase(Context context, String dbName, String tableName) {
		super(context, dbName, null, 1);
		this.tableName = tableName;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(getSqlCreateTable());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
	
	public void open(boolean writable) throws SQLException {
		if(writable){
			db = getWritableDatabase();
		}else{
			db = getReadableDatabase();
		}
	}
	
	public void close(){
		db.close();
		super.close();
	}
	
	public void insert(T e){
		db.insert(tableName, null, buildContentValues(e));
	}
	
	public void insertOrUpdate(T e) {
		if(queryOne(e.getId()) == null){
			db.insert(tableName, null, buildContentValues(e));
		}else{
			update(e);
		}
		//db.execSQL("INSERT INTO "+DB_TABLE+"()
	}
		
	public boolean update(T e) {
		//return db.update("oauth_token", buildContentValues(e), "type=" + e.getType() + " AND id=" + e.getId(), null) > 0;
		return db.update(tableName, buildContentValues(e), "id=" + e.getId(), null) > 0;
		//db.execSQL("UPDATE "+DB_TABLE+" SET "+KEY_DATA+" = ? WHERE "+KEY_ID+" = ?", new Object[]{e.data, Integer.valueOf(e.id)})
	}
		
	public boolean delete(T e) {
		return deleteById(e.getId());
	}
		
	public boolean deleteById(long id) {
		//return db.delete("oauth_token", "type=" + e.getType() + " AND id=" + e.getId(), null) > 0;
		return db.delete(tableName, "id=" + id, null) > 0;
		//db.execSQL("DELETE FROM "+DB_TABLE+" WHERE "+KEY_ID+" = ?", new Object[]{Integer.valueOf(id)});
	}
		
	public List<T> queryAll() {
		//Cursor cs = db.query(DB_TABLE, new String[] { KEY_ID, KEY_DATA }, null, null, null, null, null);
		Cursor cs = db.rawQuery("SELECT * FROM " + tableName, null);
		List<T> es = new ArrayList<T>();
		while(cs.moveToNext()){
			es.add(buildEntity(cs));
		}
		return es;
	}
		
	public T queryOne(long id) throws SQLException {
		//Cursor cs = db.query(true, DB_TABLE, new String[] { KEY_ID, KEY_DATA }, KEY_ID + "=" + id, null, null, null,null, null);
		//Cursor cs = db.rawQuery("SELECT * FROM oauth_token WHERE type = ? AND id = ?", new String[]{String.valueOf(e.getType()), e.getId()});
		Cursor cs = db.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?", new String[]{String.valueOf(id)});
		if(cs.moveToNext()){
			return buildEntity(cs);
		}else{
			return null;
		}
	}
	
	public void empty(){
		db.execSQL("DELETE FROM " + tableName);
		//db.execSQL("update sqlite_sequence set seq=0 where name='"+ tableName +"'");
	}

	/**
	 	StringBuffer sb = new StringBuffer("CREATE TABLE clock (");
		sb.append("id INTEGER PRIMARY KEY,");
		sb.append("name TEXT,");
		return sb.toString();
	 * @return
	 */
	protected abstract String getSqlCreateTable();
	protected abstract ContentValues buildContentValues(T e);
	protected abstract T buildEntity(Cursor cs);
}