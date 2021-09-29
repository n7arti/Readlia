package com.Readlia;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class Book {
    BufferedImage cover;
    String title;
    String author;
    String genre;
    int pageCount;//количество страниц
    int pageNumber;//текущая страница
    int progressRead;//прогресс чтения в %
    String pathBook;

    public BufferedImage getCover() {
        return cover;
    }

    public void setCover(BufferedImage cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

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

    public void setProgressRead() {
        this.progressRead = this.pageNumber/this.pageCount*100;
    }
    public void read() {
        File fileName = new File(pathBook);
        try {

            FileReader fr = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fr);
            FileInputStream file = new FileInputStream(reader.readLine());
            while (file.read() != -1){
                int i = file.read();
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
