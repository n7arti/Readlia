package com.vstavit_nazvanie.readlia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.vstavit_nazvanie.readlia.MainActivity.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(AndroidJUnit4.class)
public class UnitTest extends Application {

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

        File goodFile = new File("/data/data/com.vstavit_nazvanie.readlia/files/1");
        book1.loadInfo(goodFile);
        assertEquals("file\n" + book1.toString() + "\nTest\n" + book.toString(), book1.toString(), book.toString());
    }

    @Test
    public void testSaveBookInfo() {
        Book book;
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

        assertEquals(book.saveInfo(), book.saveInfo(), "1\n" +
                Uri.parse("android.resource://com.vstavit_nazvanie.readlia/drawable/ic_book_true") + "\n" +
                "Test author\n~\nTest genre\n~\nTest book\n2001");
    }

    @Test
    public void testCompleteListOfBooks() {
        // шаблон для будующих тестирований массива при поиске на сервере списка популярных книг
        ArrayList<Book> bookList;
        int count = 0;

        bookList = CreateTestModul.testUnitCreateBookExample();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i) != null);
                count++;
        }
        assertTrue(count > 3);
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

    @Test
    public void testPageCount() {
        String elevenSymbol = "12345678910"; // эта строка записана в файл
        int pageCount = 0;
        TXTOpener.setSizeOfList(10);
        File file = new File("TempUnitTest");
        TXTOpener.setPathBook(file.getAbsolutePath());
        pageCount = TXTOpener.pageCount();
        assertEquals(pageCount, 2);
    }

    @Test
    public void testCalculateProgress() {
        int ans = Toolbar.calculateProgress(1000, 100);
        assertEquals(10, ans);
    }

    /*
    @Test
    public void addition_correct() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addition_isNotCorrect() throws Exception {
        assertEquals("Numbers isn't equals!", 5, 2 + 2);
    }

    */
}
