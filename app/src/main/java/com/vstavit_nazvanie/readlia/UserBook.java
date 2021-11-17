package com.vstavit_nazvanie.readlia;

import java.io.File;
import java.util.HashMap;

public class UserBook extends Book {
	private int pageCount;
	private int pageNumber;
	private int progressRead;

	public UserBook() {
		super();
	}
	public UserBook(Book book) {
		super(book.id, book.pathToImage, book.authorhash, book.ganrehash, book.title, book.year);

	}
	/*
	public UserBook(int id, int image, HashMap<Integer, Autor> autorhash, HashMap<Integer, Genre> ganrehash,
					String title, int pageCount, int pageCount2, int pageNumber, int progressRead) {
		super(id, image, autorhash, ganrehash, title, pageCount);
		pageCount = pageCount2;
		this.pageNumber = pageNumber;
		this.progressRead = progressRead;
	}
	*/

	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getProgressRead() {
		return progressRead;
	}
	public void setProgressRead(int progressRead) {
		this.progressRead = progressRead;
	}
	
}
