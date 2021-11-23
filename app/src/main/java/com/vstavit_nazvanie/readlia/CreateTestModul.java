package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateTestModul {

    public Book createBook(int id) {
        Book book;
        Autor autor;
        //Autor autor1;
        //Autor autor2;
        Genre genre;
        //Genre genre1;
        //Genre genre2;
        HashMap<Integer, Autor> autorhash = new HashMap<>();
        HashMap<Integer, Genre> ganrehash = new HashMap<>();
        HashMap<Integer, Book> bookhash = new HashMap<>();

        Uri uri = Uri.parse("android.resource://com.vstavit_nazvanie.readlia/drawable/ic_book_true");
        book = new Book(id, uri, "Test book", 2001);
        /*
        book.setId(1);
        book.setTitle("Test book");
        book.setPageCount(0);
        book.setYear(2001);
        */
        autor = new Autor(345, "Test author");
        //autor1 = new Autor(3555, "Test pie1");
        //autor2 = new Autor(3555, "Test people");
        genre = new Genre(0, "Test genre");
        //genre1 = new Genre(1, "Test simple");
        //genre2 = new Genre(1, "Test sortirovka");

        autorhash.put(0, autor);
        // autorhash.put(1, autor1);
        // autorhash.put(2, autor2);
        ganrehash.put(0, genre);
        // ganrehash.put(1, genre1);
        // ganrehash.put(2, genre2);

        book.setAuthorhash(autorhash);
        book.setGanrehash(ganrehash);

        return book;
    }

    public static Uri testDownload() {
        final Uri[] uri = new Uri[1];
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");

        // создаем ссылку на файл по адресу coin.png
        // вызываем getDownloadUrl() и устанавливаем слушатель успеха,
        // который срабатывает в случае успеха процесса скачивания
        rootRef.child("images/1.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri_value) {
                uri[0] = uri_value;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        return uri[0];
    }

    public static Book testDownloadBookInfo(Book book, Context context) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");
        StorageReference islandRef = rootRef.child("bookInfo/1");
        File localFile = File.createTempFile("temp" + String.valueOf(book.getId()), ".txt", context.getCacheDir());

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                book.loadInfo(localFile);
                Log.i("Download", "BookInfo has been download and save");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("Download", "BookInfo has not been download");
            }
        });
        return book;
    }

    public static File testDownloadBookForWatch(Book book, Context context) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");
        StorageReference islandRef = rootRef.child("books/1.txt");
        File localFile = File.createTempFile("tempBook" + String.valueOf(book.getId()), ".txt", context.getCacheDir());

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Log.i("Download", "Book " + book.getId() + " has been download and save");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("Download", "Book " + book.getId() + " has not been download");
            }
        });
        return localFile;
    }

    public static ArrayList<Book> testCreateBookExample(Context context) {
        ArrayList<Book> booksExample= new ArrayList<>();
        CreateTestModul tool = new CreateTestModul();

        try {
            booksExample.add(Toolbar.downloadBookInfo(tool.createBook(1), context)); // добавили книгу в массив книг
            booksExample.add(Toolbar.downloadBookInfo(tool.createBook(2), context)); // добавили книгу в массив книг
            booksExample.add(Toolbar.downloadBookInfo(tool.createBook(3), context)); // добавили книгу в массив книг
            booksExample.add(Toolbar.downloadBookInfo(tool.createBook(4), context)); // добавили книгу в массив книг
            booksExample.add(Toolbar.downloadBookInfo(tool.createBook(5), context)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
        }
        catch (IOException e) {
            Log.i("testCreateBookExample", String.valueOf(e));
        }

        return booksExample;
    }

    public static ArrayList<Book> testUnitCreateBookExample() {
        ArrayList<Book> booksExample= new ArrayList<>();
        CreateTestModul tool = new CreateTestModul();

            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
            booksExample.add(tool.createBook(1)); // добавили книгу в массив книг

        return booksExample;
    }
}