package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NetBookAdapter extends ArrayAdapter<Book> {

    private final LayoutInflater inflater;
    private final int layout;
    private final List<Book> books;

    public NetBookAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.books = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        StringBuilder strToTextBox = new StringBuilder();
        ViewHolder viewHolder;


        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position); //взяли одну книгу из списка

        Toolbar.setImage(viewHolder.imageView, book); // Устанавливаем в место отображения картинку
        viewHolder.nameView.setText(book.getTitle()); // Устанавливаем название книги

        for (int i = 1; i < book.authorhash.size() + 1; i++)
            strToTextBox.append(book.getAuthorhash().get(i).getName()).append(" ");
        viewHolder.authorView.setText(strToTextBox.toString());
        viewHolder.yearView.setText(book.getStrYear());
        strToTextBox = new StringBuilder();
        for (int i = 1; i < book.ganrehash.size() + 1; i++)
            strToTextBox.append(book.getGanrehash().get(i).getName()).append(" ");
        viewHolder.genreView.setText(strToTextBox.toString());

        return convertView;
    }

    private static class ViewHolder {
        final ImageView imageView;
        final TextView nameView, authorView, yearView, genreView;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.icon); // создаем связь между местом отображения и элентом (элемент еще не указан)
            nameView = view.findViewById(R.id.text_name); // создали связь без установки
            authorView = view.findViewById(R.id.text_author); // создали связь без установки
            yearView = view.findViewById(R.id.text_year);
            genreView = view.findViewById(R.id.text_genre);
        }
    }
}
