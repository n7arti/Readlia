package com.vstavit_nazvanie.readlia;

import java.util.Map;

public class NetLibrrary {
	private Map<Integer, Autor> autorhash;
	private Map<Integer, Ganre> ganrehash;
	private Map<Integer, User> userhash;
	
	public NetLibrrary() {
		super();
	}
	
	public NetLibrrary(Map<Integer, Autor> autorhash, Map<Integer, Ganre> ganrehash, Map<Integer, User> userhash) {
		super();
		this.autorhash = autorhash;
		this.ganrehash = ganrehash;
		this.userhash = userhash;
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
	public Map<Integer, User> getUserhash() {
		return userhash;
	}
	public void setUserhash(Map<Integer, User> userhash) {
		this.userhash = userhash;
	}
	
	
}
