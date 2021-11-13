package com.vstavit_nazvanie.readlia;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CreateTestModul {

    public Book createModul() {
        Book book = new Book();
        Autor autor;
        Genre genre;
        Genre genre1;
        HashMap<Integer, Autor> autorhash = new HashMap<>();
        HashMap<Integer, Genre> ganrehash = new HashMap<>();
        HashMap<Integer, Book> bookhash = new HashMap<>();

        Uri uri = Uri.parse("android.resource://com.vstavit_nazvanie.readlia/drawable/ic_book_true");
        book = new Book(1, uri, "Test book", 0, 2001);
        /*
        book.setId(1);
        book.setTitle("Test book");
        book.setPageCount(0);
        book.setYear(2001);
        */
        autor = new Autor(345, "Test author");
        genre = new Genre(1, "Test genre");
        genre1 = new Genre(2, "Test simple");

        autorhash.put(1, autor);
        ganrehash.put(1, genre);
        ganrehash.put(2, genre1);

        book.setAuthorhash(autorhash);
        book.setGanrehash(ganrehash);

        return book;
    }

    public static Uri testDownload(int id) {
        String str = String.valueOf(id);
        final Uri[] uri = new Uri[1];
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");

        // создаем ссылку на файл по адресу scoin.png
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

}
