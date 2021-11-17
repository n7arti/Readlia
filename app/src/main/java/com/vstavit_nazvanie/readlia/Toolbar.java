package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Toolbar {
    /**
     *
     * @param imageView Место установки изображения
     * @param book Изображение какой книги нужно установить
     *
     *             Метод скачивает изображение из Firebase
     *             Устанавливает изображение в выделенное место
     *             Сохраняет в book.pathToImage uri загрузки
     *             При успехе или провале уведомляет Log
     *
     */
    public static void setImage(ImageView imageView, Book book) {
        String str = String.valueOf(book.getId());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");

        rootRef.child("images/" + str + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri_value) {
                Picasso.get().load(uri_value).into(imageView);
                book.setPathToImage(uri_value);
                Log.i("setImage", "Success download image " + str);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Добавить картинку нет фото
                Log.i("setImage", "Error download image " + str + " " + e);
            }
        });
    }

    /**
     *
     * @param book Экземпляр книги для которой записывается информация
     * @param context Контекст
     * @return Ссылку на перезаписанный экземпляр (Идентична входящей ссылке)
     * @throws IOException Исключение обрабатывается в месте вызова
     *
     *             Метод принимает экземпляр книги с известным ID
     *             Создает временный файл на устройстве куда производит скачивание
     *             Вызывает метод записи данных в экземпляр книги и передает туда скаченный файл
     *             При успехе или провале уведомляет Log
     *
     */
    public static Book downloadBookInfo(Book book, Context context) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");
        StorageReference islandRef = rootRef.child("bookInfo/" + book.getId());
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

    /**
     *
     * @param book  Экземпляр книги для которой производится скачивание
     * @param context Контекст
     * @return Скаченный tempFile
     * @throws IOException Исключение обрабатывается в месте вызова
     *
     *            Метод принимает экземпляр книги с известным ID
     *            Создает временный файл на устройстве куда производит скачивание
     *            При успехе или провале уведомляет Log
     *
     */
    public static File downloadBookForWatch(Book book, Context context) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");
        StorageReference islandRef = rootRef.child("books/" + book.getId() + ".txt");
        File tempFile = File.createTempFile("tempBook" + String.valueOf(book.getId()), ".txt", context.getCacheDir());

        islandRef.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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

        return tempFile;
    }
}
