package com.vstavit_nazvanie.readlia;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Book {
	protected int id;
	protected Uri pathToImage;
	protected HashMap<Integer, Autor> authorhash = new HashMap<>();
	protected HashMap<Integer, Genre> ganrehash = new HashMap<>();
	protected String title;
	protected int year;

	public Book(int id, Uri pathToImage, HashMap<Integer, Autor> authorhash, HashMap<Integer, Genre> ganrehash, String title, int year) {
		this.id = id;
		this.pathToImage = pathToImage;
		this.authorhash = authorhash;
		this.ganrehash = ganrehash;
		this.title = title;
		this.year = year;
	}

	public Book(int id, Uri pathToImage, String title, int year) {
		this.id = id;
		this.pathToImage = pathToImage;
		this.title = title;
		this.year = year;
	}
	public Book() {
		this.id = 1;
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
	public Uri getPathToImage() {
		return pathToImage;
	}
	public void setPathToImage(Uri image) {
		this.pathToImage= image;
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

	/**
	 *
	 * @return Строка с полями книги
	 */
	@NonNull
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

		return String.valueOf(saveStr);
	}

	public void loadInfo(File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String str = "";
			Autor autor;
			Genre genre;

			this.id = Integer.parseInt(bf.readLine());
			Log.i("loadProcess", String.valueOf(this.id));
			this.pathToImage = Uri.parse(bf.readLine());
			Log.i("loadProcess", String.valueOf(this.pathToImage));
			str = bf.readLine(); // Test autor
			Log.i("loadProcess", str);
			for (int i = 0; !str.equals("~"); i++) {
				autor = new Autor();
				autor.setId(i); // загрузить идентификатор с базы данных !!!!!!!!!!!!!!!!!!!!!!
				autor.setName(str);
				this.authorhash.put(i, autor);
				str = bf.readLine();
				Log.i("loadProcess", str + " " + i);
			}
			str = bf.readLine();
			Log.i("loadProcess", str);
			for (int i = 0; !str.equals("~"); i++) {
				genre = new Genre();
				genre.setId(i); // загрузить идентификатор с базы данных !!!!!!!!!!!!!!!!!!!!!!!
				genre.setName(str);
				this.ganrehash.put(i, genre);
				str = bf.readLine();
				Log.i("loadProcess", str + " " + i);
			}
			this.title = bf.readLine();
			Log.i("loadProcess", this.title);
			this.year = Integer.parseInt(bf.readLine());
			Log.i("loadProcess", String.valueOf(this.year));
		}
		catch (IOException e) {
			Log.i("loadInfo", String.valueOf(e));
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
