package cn.xxd.qr.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import q.util.SqliteBase.ISqlite;

public class QrCode implements ISqlite, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String text;
	private long time;
	private String timeStr;
	private boolean isFavorite;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getTimeStr() {
		if(timeStr == null){
			timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(this.time));
		}
		return timeStr;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	
	
}
