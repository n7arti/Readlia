package com.vstavit_nazvanie.readlia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Genre {
	private int id;
	private HashMap<Integer, Autor> autorhash = new HashMap<>();
	private HashMap<Integer, Book> bookhash = new HashMap<>();
	private String name;

	public Genre() {

	}

	public Genre(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HashMap<Integer, Autor> getAutorhash() {
		return autorhash;
	}
	public void setAutorhash(HashMap<Integer, Autor> autorhash) {
		this.autorhash = autorhash;
	}
	public HashMap<Integer, Book> getBookhash() {
		return bookhash;
	}
	public void setBookhash(HashMap<Integer, Book> bookhash) {
		this.bookhash = bookhash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
