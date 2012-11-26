package cn.xxd.qr.bean;

import java.io.Serializable;

public class Color {
	
	private String name;
	private int color;
	
	public Color(String name, int color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	
	
}
