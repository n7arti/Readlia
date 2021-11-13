package com.vstavit_nazvanie.readlia;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Toolbar {
    /**
     *
     * @param imageView Место установки изображения
     * @param book Изображение какой книги нужно установить
     *
     *             Метод скачивает изображение из Firebase
     *             Устанавливает изображение в выделенное место
     *             Сохраняет в book.pathToImage uri загрузки
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Error download image" + str);
            }
        });
    }


}
