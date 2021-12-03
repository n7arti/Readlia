package com.vstavit_nazvanie.readlia;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MyBook extends Book {
	private int pageCount;
	private int pageNumber;
	private int progressRead;
	private String pathToBook;


	public MyBook() {
		super();
	}
	public MyBook(Book book) {
		super(book.id, book.pathToImage, book.authorhash, book.ganrehash, book.title, book.year);
		//pathToBook = book.id + "Book";
	}
	public MyBook(int id, Uri pathToImage, String title, int year) {
		super(id, pathToImage, title, year);
		//pathToBook = id + "Book";
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
	public String getPathToBook() {
		return pathToBook;
	}
	public void setPathToBook(String pathToBook) {
		this.pathToBook = pathToBook;
	}

	@NonNull
	@Override
	public String saveInfo() {
		StringBuilder saveStr = new StringBuilder();

		saveStr.append(this.id);
		saveStr.append("\n");
		saveStr.append(this.pathToImage);
		saveStr.append("\n");
		for (int i = 0; i < this.authorhash.size(); i++) {
			saveStr.append(this.authorhash.get(i).getName());
			saveStr.append("\n");
		}
		saveStr.append("~");
		saveStr.append("\n");
		for (int i = 0; i < this.ganrehash.size(); i++) {
			saveStr.append(this.ganrehash.get(i).getName());
			saveStr.append("\n");
		}
		saveStr.append("~");
		saveStr.append("\n");
		saveStr.append(this.title);
		saveStr.append("\n");
		saveStr.append(this.year);
		saveStr.append("\n");
		saveStr.append(this.pageCount);
		saveStr.append("\n");
		saveStr.append(this.pageNumber);
		saveStr.append("\n");
		saveStr.append(this.progressRead);
		saveStr.append("\n");

		return String.valueOf(saveStr);
	}

	@Override
	public void loadInfo(File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String str = "";
			Autor autor;
			Genre genre;

			this.id = Integer.parseInt(bf.readLine());
			Log.i("MyLoadProcess", String.valueOf(this.id));
			this.pathToImage = Uri.parse(bf.readLine());
			Log.i("MyLoadProcess", String.valueOf(this.pathToImage));
			str = bf.readLine(); // Test autor
			Log.i("MyLoadProcess", str);
			for (int i = 0; !str.equals("~"); i++) {
				autor = new Autor();
				autor.setId(i); // загрузить идентификатор с базы данных !!!!!!!!!!!!!!!!!!!!!!
				autor.setName(str);
				this.authorhash.put(i, autor);
				str = bf.readLine();
				Log.i("MyLoadProcess", str + " " + i);
			}
			str = bf.readLine();
			Log.i("MyLoadProcess", str);
			for (int i = 0; !str.equals("~"); i++) {
				genre = new Genre();
				genre.setId(i); // загрузить идентификатор с базы данных !!!!!!!!!!!!!!!!!!!!!!!
				genre.setName(str);
				this.ganrehash.put(i, genre);
				str = bf.readLine();
				Log.i("MyLoadProcess", str + " " + i);
			}
			this.title = bf.readLine();
			Log.i("MyLoadProcess", this.title);
			this.year = Integer.parseInt(bf.readLine());
			Log.i("MyLoadProcess", String.valueOf(this.year));
			this.pageCount = Integer.parseInt(bf.readLine());
			this.pageNumber = Integer.parseInt(bf.readLine());
			this.progressRead = Integer.parseInt(bf.readLine());
		}
		catch (IOException e) {
			Log.i("MyLoadProcess", String.valueOf(e));
		}
		finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@NonNull
	@Override
	public String toString() {
		return saveInfo();
	}
}
