package game.song;

import common.db.Pojo;

public class Song extends Pojo{
	private static final long serialVersionUID = 1L;
	private String name;
	private int price;
	private String mp3URL;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMp3URL() {
		return mp3URL;
	}
	public void setMp3URL(String mp3url) {
		mp3URL = mp3url;
	}
}
