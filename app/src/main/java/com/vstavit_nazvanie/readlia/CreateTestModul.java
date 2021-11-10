package com.vstavit_nazvanie.readlia;

import java.util.HashMap;

public class CreateTestModul {

    public Book createModul() {
        Book book = new Book();
        Autor autor;
        Genre genre;
        Genre genre1;
        HashMap<Integer, Autor> autorhash = new HashMap<>();
        HashMap<Integer, Genre> ganrehash = new HashMap<>();
        HashMap<Integer, Book> bookhash  = new HashMap<>();


        book = new Book(1, R.drawable.ic_day_and_night, "Test book", 0, 2001);
        autor = new Autor(345, "Test author");
        genre = new Genre(1, "Test genre");
        genre1 = new Genre(2, "Test genre1fdsfgseadfsa");


        autorhash.put(1, autor);
        ganrehash.put(1, genre);
        ganrehash.put(2, genre1);

        book.setAuthorhash(autorhash);
        book.setGanrehash(ganrehash);

        return book;
    }
}
