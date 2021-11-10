package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class BookAdapter extends ArrayAdapter<Book> {

    private LayoutInflater inflater;
    private int layout;
    private List<Book> books;

    public BookAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.books = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageView = view.findViewById(R.id.icon); // создаем связь между местом отображения и элентом (элемент еще не указан)
        TextView nameView = view.findViewById(R.id.text_name); // создали связь без установки
        TextView authorView = view.findViewById(R.id.text_author); // создали связь без установки
        TextView yearView = view.findViewById(R.id.text_year);
        TextView genreView = view.findViewById(R.id.text_genre);

        Book book = books.get(position); //взяли одну книгу из списка

        imageView.setImageResource(book.getImage()); // Устанавливаем в место отображения картинку
        nameView.setText(book.getTitle()); // Устанавливаем название книги
        for (int i = 1; i < book.autorhash.size() + 1; i++)
            authorView.setText(book.getAutorhash().get(i).getName());
        yearView.setText(book.getStrYear());
        String strtotextbox = "";
        for (int i = 1; i < book.ganrehash.size() + 1; i++)
            strtotextbox += book.getGanrehash().get(i).getName() + " ";
        genreView.setText(strtotextbox);

        return view;
    }
}
