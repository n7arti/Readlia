package com.vstavit_nazvanie.readlia;

import static com.vstavit_nazvanie.readlia.R.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView mNamesBooks; //список с книгами
    private ConstraintLayout mBackground; // цвет фона
    private TextView mTextMain; // цвет текста заголовка окна
    private ImageButton mDayNightButton;// кнопка день\ночь
    private ImageButton mMyBookButton; // кнопка мои книги
    private ImageButton mLibraryButton; // кнопка библиотека
    private ImageButton mProfileButton; // кнопка профиль
    private ConstraintLayout mLine; // линия около заголовка
    private TextView mlittleMyBookText; // названия кнопоки
    private TextView mlittleLibraryText; // названия кнопоки
    private TextView mlittleProfileText; // названия кнопоки
    private ImageView mBookIcon; // иконка
    private ImageView mLibraryIcon; // иконка
    private ImageView mProfileIcon; // иконка
    private boolean nighttheme; // датчик темы
    private int page; // индикатор страницы

    @Override // При запуске приложения (единожды)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.book); // установка главного экрана
        nighttheme = true;
        page = 0;
        initFindId();

    }

    public void setPropertiesTheme() { // отрисовка цветов темы
        if (nighttheme) { // темная тема
            mBackground.setBackgroundColor(ContextCompat.getColor(this, color.dark));
            mTextMain.setTextColor(ContextCompat.getColor(this, color.light));
            mDayNightButton.setImageResource(drawable.ic_day_and_night);
            mMyBookButton.setImageResource(drawable.ic_button);
            mLibraryButton.setImageResource(drawable.ic_button);
            mProfileButton.setImageResource(drawable.ic_button);
            mLine.setBackgroundColor(ContextCompat.getColor(this, color.light)); // исправить косяк нейминга
            switch (page) {
                case (0): { // нажата кнопка книги
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mBookIcon.setImageResource(drawable.ic_book_true);
                    mLibraryIcon.setImageResource(drawable.ic_library_false);
                    mProfileIcon.setImageResource(drawable.ic_profile_false);
                    break;
                }
                case (1): { // нажата кнопка библиотека
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mBookIcon.setImageResource(drawable.ic_book_false);
                    mLibraryIcon.setImageResource(drawable.ic_library_true);
                    mProfileIcon.setImageResource(drawable.ic_profile_false);
                    break;
                }
                case (2): { // нажата кнопка профиль
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.gray));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.light));
                    mBookIcon.setImageResource(drawable.ic_book_false);
                    mLibraryIcon.setImageResource(drawable.ic_library_false);
                    mProfileIcon.setImageResource(drawable.ic_profile_true);
                    break;
                }
            }
        }
        else { // light theme
            mBackground.setBackgroundColor(ContextCompat.getColor(this, color.white));
            mTextMain.setTextColor(ContextCompat.getColor(this, color.text_light));
            mDayNightButton.setImageResource(drawable.ic_day_and_night_light);
            mMyBookButton.setImageResource(drawable.ic_button_day);
            mLibraryButton.setImageResource(drawable.ic_button_day);
            mProfileButton.setImageResource(drawable.ic_button_day);
            mLine.setBackgroundColor(ContextCompat.getColor(this, color.text_light));
            switch (page) {
                case (0): { // нажата кнопка книги
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.yellow));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mBookIcon.setImageResource(drawable.ic_book_day_true);
                    mLibraryIcon.setImageResource(drawable.ic_library_day_false);
                    mProfileIcon.setImageResource(drawable.ic_profile_day_false);
                    break;
                }
                case (1): { // нажата кнопка библиотека
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.yellow));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mBookIcon.setImageResource(drawable.ic_book_day_false);
                    mLibraryIcon.setImageResource(drawable.ic_library_day_true);
                    mProfileIcon.setImageResource(drawable.ic_profile_day_false);
                    break;
                }
                case (2): { // нажата кнопка профиль
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, color.little_text_light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, color.yellow));
                    mBookIcon.setImageResource(drawable.ic_book_day_false);
                    mLibraryIcon.setImageResource(drawable.ic_library_day_false);
                    mProfileIcon.setImageResource(drawable.ic_profile_day_true);
                    break;

                }
            }
        }

    }

    public void initFindId() { // загрузка всех объектов
        mBackground = findViewById(id.constraintLayout);
        mNamesBooks = (ListView) findViewById(id.namesBooks);
        mDayNightButton = findViewById(id.dayNightButton);
        mMyBookButton = findViewById(id.mybookButton);
        mLibraryButton = findViewById(id.libraryButton);
        mProfileButton = findViewById(id.profileButton);
        mLine = findViewById(id.line);
        mlittleMyBookText = findViewById(id.littleMyBookText);
        mlittleLibraryText = findViewById(id.littleLibraryText);
        mlittleProfileText = findViewById(id.littleProfileText);
        mBookIcon = findViewById(id.bookIcon);
        mLibraryIcon = findViewById(id.libraryIcon);
        mProfileIcon = findViewById(id.profileIcon);
        switch (page) {
            case (0): {
                mTextMain = findViewById(id.textMyBook);
                break;
            }
            case (1): {
                mTextMain = findViewById(id.textLibrary);
                break;
            }
            case (2): {
                mTextMain = findViewById(id.textProfile);
                break;
            }
        }
    }
// нажатие на разные кнопки
    public void onDayNightButtonClick(View view) {
        nighttheme = !nighttheme;
        setPropertiesTheme();
    }

    public void onBookClick(View view) {
        setContentView(layout.book);
        page = 0;
        initFindId();
        setPropertiesTheme();
    }

    public void onLibraryClick(View view) {
        setContentView(layout.library);
        page = 1;
        initFindId();
        setPropertiesTheme();
        Book[] pdfFiles = new Book[0];
        pdfFiles[0] = new Book();
        //String[] pdfFile = {"Книга 1", "fjfjhfk"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, pdfFiles){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView mText = (TextView) view.findViewById(android.R.id.text1);
                return view;
            }
        };
        mNamesBooks.setAdapter(adapter);
        mNamesBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = mNamesBooks.getItemAtPosition(i).toString();
                Intent start = new Intent(getApplicationContext(), PDFOpener.class);
                start.putExtra("pdfFileName",item);
                startActivity(start);
            }
        });

    }

    public void onProfileClick(View view) {
        setContentView(layout.profile);
        page = 2;
        initFindId();
        setPropertiesTheme();
    }
}