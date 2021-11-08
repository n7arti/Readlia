package com.vstavit_nazvanie.readlia;

import java.util.Map;

public class Autor {
	private int id;
	private Map<Integer, Book> bookhash;
	private Map<Integer, Ganre> ganrehash;
	private String name;
	
	public Autor() {
		
	}

	public Autor(int id, Map<Integer, Book> bookhash, Map<Integer, Ganre> ganrehash, String name) {
		
		this.id = id;
		this.bookhash = bookhash;
		this.ganrehash = ganrehash;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, Book> getBookhash() {
		return bookhash;
	}
	public void setBookhash(Map<Integer, Book> bookhash) {
		this.bookhash = bookhash;
	}
	public Map<Integer, Ganre> getGanrehash() {
		return ganrehash;
	}
	public void setGanrehash(Map<Integer, Ganre> ganrehash) {
		this.ganrehash = ganrehash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
