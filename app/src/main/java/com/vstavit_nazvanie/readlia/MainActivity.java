package com.vstavit_nazvanie.readlia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.book); // установка главного экрана
        nighttheme = true;
        page = 0;
        initFindId();

    }

    public void setPropertiesTheme() { // отрисовка цветов темы
        if (nighttheme) { // темная тема
            mBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
            mTextMain.setTextColor(ContextCompat.getColor(this, R.color.light));
            mDayNightButton.setImageResource(R.drawable.ic_day_and_night);
            mMyBookButton.setImageResource(R.drawable.ic_button);
            mLibraryButton.setImageResource(R.drawable.ic_button);
            mProfileButton.setImageResource(R.drawable.ic_button);
            mLine.setBackgroundColor(ContextCompat.getColor(this, R.color.light)); // исправить косяк нейминга
            switch (page) {
                case (0): { // нажата кнопка книги
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mBookIcon.setImageResource(R.drawable.ic_book_true);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_false);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_false);
                    break;
                }
                case (1): { // нажата кнопка библиотека
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mBookIcon.setImageResource(R.drawable.ic_book_false);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_true);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_false);
                    break;
                }
                case (2): { // нажата кнопка профиль
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.gray));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.light));
                    mBookIcon.setImageResource(R.drawable.ic_book_false);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_false);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_true);
                    break;
                }
            }
        }
        else { // light theme
            mBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mTextMain.setTextColor(ContextCompat.getColor(this, R.color.text_light));
            mDayNightButton.setImageResource(R.drawable.ic_day_and_night_light);
            mMyBookButton.setImageResource(R.drawable.ic_button_day);
            mLibraryButton.setImageResource(R.drawable.ic_button_day);
            mProfileButton.setImageResource(R.drawable.ic_button_day);
            mLine.setBackgroundColor(ContextCompat.getColor(this, R.color.text_light));
            switch (page) {
                case (0): { // нажата кнопка книги
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.yellow));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mBookIcon.setImageResource(R.drawable.ic_book_day_true);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_day_false);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_day_false);
                    break;
                }
                case (1): { // нажата кнопка библиотека
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.yellow));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mBookIcon.setImageResource(R.drawable.ic_book_day_false);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_day_true);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_day_false);
                    break;
                }
                case (2): { // нажата кнопка профиль
                    mlittleMyBookText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mlittleLibraryText.setTextColor(ContextCompat.getColor(this, R.color.little_text_light));
                    mlittleProfileText.setTextColor(ContextCompat.getColor(this, R.color.yellow));
                    mBookIcon.setImageResource(R.drawable.ic_book_day_false);
                    mLibraryIcon.setImageResource(R.drawable.ic_library_day_false);
                    mProfileIcon.setImageResource(R.drawable.ic_profile_day_true);
                    break;

                }
            }
        }

    }

    public void initFindId() { // загрузка всех объектов
        mBackground = findViewById(R.id.constraintLayout);
        mDayNightButton = findViewById(R.id.dayNightButton);
        mMyBookButton = findViewById(R.id.mybookButton);
        mLibraryButton = findViewById(R.id.libraryButton);
        mProfileButton = findViewById(R.id.profileButton);
        mLine = findViewById(R.id.line);
        mlittleMyBookText = findViewById(R.id.littleMyBookText);
        mlittleLibraryText = findViewById(R.id.littleLibraryText);
        mlittleProfileText = findViewById(R.id.littleProfileText);
        mBookIcon = findViewById(R.id.bookIcon);
        mLibraryIcon = findViewById(R.id.libraryIcon);
        mProfileIcon = findViewById(R.id.profileIcon);
        switch (page) {
            case (0): {
                mTextMain = findViewById(R.id.textMyBook);
                break;
            }
            case (1): {
                mTextMain = findViewById(R.id.textLibrary);
                break;
            }
            case (2): {
                mTextMain = findViewById(R.id.textProfile);
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
        setContentView(R.layout.book);
        page = 0;
        initFindId();
        setPropertiesTheme();
    }

    public void onLibraryClick(View view) {
        setContentView(R.layout.library);
        page = 1;
        initFindId();
        setPropertiesTheme();
    }

    public void onProfileClick(View view) {
        setContentView(R.layout.profile);
        page = 2;
        initFindId();
        setPropertiesTheme();
    }
}