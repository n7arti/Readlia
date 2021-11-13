package com.vstavit_nazvanie.readlia;

import java.util.Map;

public class User {
	private int id;
	private Map<Integer, UserBook> finish;
	private Map<Integer, UserBook> process;
	private Map<Integer, Autor> autorFavor;
	private Map<Integer, Genre> ganreFavor;
	private String email;
	private String pathbook;
	
	
	
	public User() {
		super();
	}
	public User(int id, Map<Integer, UserBook> finish, Map<Integer, UserBook> process, Map<Integer, Autor> autorFavor,
                Map<Integer, Genre> ganreFavor, String email, String pathbook) {
		
		this.id = id;
		this.finish = finish;
		this.process = process;
		this.autorFavor = autorFavor;
		this.ganreFavor = ganreFavor;
		this.email = email;
		this.pathbook = pathbook;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, UserBook> getFinish() {
		return finish;
	}
	public void setFinish(Map<Integer, UserBook> finish) {
		this.finish = finish;
	}
	public Map<Integer, UserBook> getProcess() {
		return process;
	}
	public void setProcess(Map<Integer, UserBook> process) {
		this.process = process;
	}
	public Map<Integer, Autor> getAutorFavor() {
		return autorFavor;
	}
	public void setAutorFavor(Map<Integer, Autor> autorFavor) {
		this.autorFavor = autorFavor;
	}
	public Map<Integer, Genre> getGanreFavor() {
		return ganreFavor;
	}
	public void setGanreFavor(Map<Integer, Genre> ganreFavor) {
		this.ganreFavor = ganreFavor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPathbook() {
		return pathbook;
	}
	public void setPathbook(String pathbook) {
		this.pathbook = pathbook;
	}
	
}
