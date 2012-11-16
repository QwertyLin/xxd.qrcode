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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QrCode other = (QrCode) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
