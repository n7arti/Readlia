package com.vstavit_nazvanie.readlia;

import java.util.HashMap;

public class Book {
	protected int id;
	protected int image;
	protected HashMap<Integer, Autor> authorhash = new HashMap<>();
	protected HashMap<Integer, Genre> ganrehash = new HashMap<>();
	protected String title;
	protected int pageCount;
	protected int year;
	
	public Book(int id, int image, String title, int pageCount, int year) {
		
		this.id = id;
		this.image = image;
		this.title = title;
		this.pageCount = pageCount;
		this.year = year;
	}
	public Book() {

	}

	public int getYear() {
		return year;
	}
	public String getStrYear() {
		return String.valueOf(year);
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public HashMap<Integer, Autor> getAuthorhash() {
		return authorhash;
	}
	public void setAuthorhash(HashMap<Integer, Autor> authorhash) {
		this.authorhash = authorhash;
	}
	public HashMap<Integer, Genre> getGanrehash() {
		return ganrehash;
	}
	public void setGanrehash(HashMap<Integer, Genre> ganrehash) {
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
