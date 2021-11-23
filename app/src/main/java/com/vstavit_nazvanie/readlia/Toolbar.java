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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Toolbar {
    /**
     * @param imageView Место установки изображения
     * @param book      Изображение какой книги нужно установить
     *                  <p>
     *                  Метод скачивает изображение из Firebase
     *                  Устанавливает изображение в выделенное место
     *                  Сохраняет в book.pathToImage uri загрузки
     *                  При успехе или провале уведомляет Log
     */
    public static void setDownloadImage(ImageView imageView, Book book) {
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
     * @param book    Экземпляр книги для которой записывается информация
     * @param context Контекст
     * @return Ссылку на перезаписанный экземпляр (Идентична входящей ссылке)
     * @throws IOException Исключение обрабатывается в месте вызова
     *                     <p>
     *                     Метод принимает экземпляр книги с известным ID
     *                     Создает временный файл на устройстве куда производит скачивание
     *                     Вызывает метод записи данных в экземпляр книги и передает туда скаченный файл
     *                     При успехе или провале уведомляет Log
     *                     Отслеживает прогресс скачивания
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
                Log.i("Download BookInfo", "BookInfo has been download and save");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("Download BookInfo", "BookInfo has not been download");
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                DateFormat timeFormat = new SimpleDateFormat("ss", Locale.getDefault());
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Date currentDate;
                String timeText;


                do {
                    currentDate = new Date();
                    timeText = timeFormat.format(currentDate);
                    Log.i("Download BookInfo", "Uploaded " + ((int) progress) + "%");
                } while ((int) progress != 100 && !timeText.equals(timeFormat.format(new Date())));
            }
        });
        return book;
    }

    /**
     * @param book    Экземпляр книги для которой производится скачивание
     * @param context Контекст
     * @return Скаченный tempFile
     * @throws IOException Исключение обрабатывается в месте вызова
     *                     <p>
     *                     Метод принимает экземпляр книги с известным ID
     *                     Создает временный файл на устройстве куда производит скачивание
     *                     При успехе или провале уведомляет Log
     *                     Отслеживает прогресс скачивания
     */
    public static File downloadBookForWatch(Book book, DownloadAdapter downloadAdapter, Context context) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReferenceFromUrl("gs://readlia.appspot.com");
        StorageReference islandRef = rootRef.child("books/" + book.getId() + ".txt");
        File tempFile = File.createTempFile("tempBook" + String.valueOf(book.getId()), ".txt", context.getCacheDir());

        islandRef.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                downloadAdapter.finishEvent();
                Log.i("Download BookForWatch", "Book " + book.getId() + " has been download and save");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("Download BookForWatch", "Book " + book.getId() + " has not been download");
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                DateFormat timeFormat = new SimpleDateFormat("ss", Locale.getDefault());
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Date currentDate;
                String timeText;


                do {
                    currentDate = new Date();
                    timeText = timeFormat.format(currentDate);
                    downloadAdapter.setProgress((int) progress);
                    Log.i("Download BookForWatch", "Uploaded " + ((int) progress) + "%");
                } while ((int) progress != 100 && !timeText.equals(timeFormat.format(new Date())));
            }
        });

        return tempFile;
    }

    /**
     * @param authorMass Список авторов
     * @param ganreMass  Список жанров
     * @param bookMass   Список книг
     * @param book       Экзембляр с которого считывается информация
     * @return data[0] = localCountAuthor - Сколько прибавилось авторов
     * <p>
     * data[1] = localCountGanre - Сколько прибавилось жанров
     *          <p>
     *          Метод проходит по HashMap полученного экземпляра
     *          И заполняет 3 ArrayList нужной информацией
     */
    public static int[] addDataToMass(ArrayList<String> authorMass, ArrayList<String> ganreMass, ArrayList<String> bookMass, Book book) {
        int localCountAuthor = 0;
        int localCountGanre = 0;


        for (; localCountAuthor < book.authorhash.size(); localCountAuthor++) {
            authorMass.add(book.authorhash.get(localCountAuthor).getName());
        }
        for (; localCountGanre < book.ganrehash.size(); localCountGanre++) {
            ganreMass.add(book.ganrehash.get(localCountGanre).getName());
        }
        bookMass.add(book.title);

        int[] data = new int[2];
        data[0] = localCountAuthor;
        data[1] = localCountGanre;

        return data;
    }

    /**
     * @param booksExample Список книг
     * @param authorMass   Список авторов
     * @param ganreMass    Список жанров
     * @param bookMass     список книг
     * @return data[0] = countAuthor - Количество авторов;
     * <p>
     * data[1] = countGanre - Количество жанров;
     * <p>
     * data[2] = countBook - Количество Книг;
     *          <p>
     *          Метод проходит по Скаченным bookInfo
     *          и вызывает addDataToMass по количеству экземляров Book
     */
    public static int[] fillFindList(ArrayList<Book> booksExample, ArrayList<String> authorMass, ArrayList<String> ganreMass, ArrayList<String> bookMass) {
        int countAuthor = 0;
        int countGanre = 0;
        int countBook = 0;
        int[] data;


        for (int i = 0; i < booksExample.size(); i++) {
            data = Toolbar.addDataToMass(authorMass, ganreMass, bookMass, booksExample.get(i));
            countAuthor += data[0];
            countGanre += data[1];
            countBook++;
        }

        data = new int[3];
        data[0] = countAuthor;
        data[1] = countGanre;
        data[2] = countBook;

        return data;
    }
}