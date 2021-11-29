package com.vstavit_nazvanie.readlia;

import static org.junit.Assert.assertEquals;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RunWith(AndroidJUnit4.class)
public class TestTest {

        @Test
        public void testLoadBook() {
            Book book;
            Book book1 = new Book();
            Autor autor;
            Genre genre;
            HashMap<Integer, Autor> autorhash = new HashMap<>();
            HashMap<Integer, Genre> ganrehash = new HashMap<>();

            Uri uri = Uri.parse("android.resource://com.vstavit_nazvanie.readlia/drawable/ic_book_true");
            autor = new Autor(345, "Test author");
            genre = new Genre(0, "Test genre");
            autorhash.put(0, autor);
            ganrehash.put(0, genre);

            book = new Book(1, uri, autorhash, ganrehash,"Test book", 2001);
            // конец создания тест-образца
            File goodFile;
            goodFile = new File("C:/Users/Vlad/Desktop/Readlia");

            book1.loadInfo(goodFile);
            assertEquals("file\n" + book1.toString() + "\nTest\n" + book.toString(), book1.toString(), book.toString());
        }

        @Test
    public void testCutString() {
        String zeroSymbol = "";
        String oneSymbol = "1";
        String elevenSymbol = "12345678910";
        String[] text;

        TXTOpener.setSizeOfList(10);
        TXTOpener.setText(5);
        TXTOpener.cutString(zeroSymbol);
        TXTOpener.setPage(2);
        TXTOpener.cutString(oneSymbol);
        TXTOpener.setPage(3);
        TXTOpener.cutString(elevenSymbol);

        text = TXTOpener.getText();
        //Log.i("testCutString", text[1]);
        Log.i("testCutString", text[2]);
        Log.i("testCutString", text[3]);
        Log.i("testCutString", text[4]);

        //assertEquals("", text[1]);
        assertEquals("1", text[2]);
        assertEquals("1234567891", text[3]);
        assertEquals("0", text[4]);
    }
}
