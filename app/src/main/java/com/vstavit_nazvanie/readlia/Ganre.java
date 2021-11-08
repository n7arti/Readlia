package com.vstavit_nazvanie.readlia;

import java.util.Map;

public class Ganre {
	private int id;
	private Map<Integer, Autor> autorhash;
	private Map<Integer, Book> bookhash;
	private String name;
	
	
	
	public Ganre() {
		
	}

	public Ganre(int id, Map<Integer, Autor> autorhash, Map<Integer, Book> bookhash, String name) {
		
		this.id = id;
		this.autorhash = autorhash;
		this.bookhash = bookhash;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, Autor> getAutorhash() {
		return autorhash;
	}
	public void setAutorhash(Map<Integer, Autor> autorhash) {
		this.autorhash = autorhash;
	}
	public Map<Integer, Book> getBookhash() {
		return bookhash;
	}
	public void setBookhash(Map<Integer, Book> bookhash) {
		this.bookhash = bookhash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
