package com.vstavit_nazvanie.readlia;

import android.widget.ImageView;

import java.util.Map;

public class Book {
	protected int id;
	protected ImageView image;
	protected Map<Integer, Autor> autorhash;
	protected Map<Integer, Ganre> ganrehash;
	protected String title;
	protected int pageCount;
	
	public Book(int id, ImageView image, Map<Integer, Autor> autorhash, Map<Integer, Ganre> ganrehash, String title,
			int pageCount) {
		
		this.id = id;
		this.image = image;
		this.autorhash = autorhash;
		this.ganrehash = ganrehash;
		this.title = title;
		this.pageCount = pageCount;
	}
	public Book() {
		this.id = 1;
		this.image.setImageResource(R.drawable.ic_day_and_night);
		//this.autorhash = {1, "Олег"};
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ImageView getImage() {
		return image;
	}
	public void setImage(ImageView image) {
		this.image = image;
	}
	public Map<Integer, Autor> getAutorhash() {
		return autorhash;
	}
	public void setAutorhash(Map<Integer, Autor> autorhash) {
		this.autorhash = autorhash;
	}
	public Map<Integer, Ganre> getGanrehash() {
		return ganrehash;
	}
	public void setGanrehash(Map<Integer, Ganre> ganrehash) {
		this.ganrehash = ganrehash;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
