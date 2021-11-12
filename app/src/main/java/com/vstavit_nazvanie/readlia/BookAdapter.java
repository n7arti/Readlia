package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        String strtotextbox = "";
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

        viewHolder.imageView.setImageResource(book.getImage()); // Устанавливаем в место отображения картинку
        viewHolder.nameView.setText(book.getTitle()); // Устанавливаем название книги

        for (int i = 1; i < book.authorhash.size() + 1; i++)
            strtotextbox +=book.getAuthorhash().get(i).getName() + " ";
        viewHolder.authorView.setText(strtotextbox);
        viewHolder.yearView.setText(book.getStrYear());
        strtotextbox = "";
        for (int i = 1; i < book.ganrehash.size() + 1; i++)
            strtotextbox += book.getGanrehash().get(i).getName() + " ";
        viewHolder.genreView.setText(strtotextbox);

        return convertView;
    }
    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView, authorView,yearView, genreView ;
        ViewHolder(View view){
            imageView = view.findViewById(R.id.icon); // создаем связь между местом отображения и элентом (элемент еще не указан)
            nameView = view.findViewById(R.id.text_name); // создали связь без установки
            authorView = view.findViewById(R.id.text_author); // создали связь без установки
            yearView = view.findViewById(R.id.text_year);
            genreView = view.findViewById(R.id.text_genre);
        }
    }
}
