package com.vstavit_nazvanie.readlia;

import java.util.HashMap;

public class Autor {
	private int id;
	private HashMap<Integer, Book> bookhash  = new HashMap<>();
	private HashMap<Integer, Genre> ganrehash  = new HashMap<>();
	private String name;
	
	public Autor() {

	}

	public Autor(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HashMap<Integer, Book> getBookhash() {
		return bookhash;
	}
	public void setBookhash(HashMap<Integer, Book> bookhash) {
		this.bookhash = bookhash;
	}
	public HashMap<Integer, Genre> getGanrehash() {
		return ganrehash;
	}
	public void setGanrehash(HashMap<Integer, Genre> ganrehash) {
		this.ganrehash = ganrehash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
