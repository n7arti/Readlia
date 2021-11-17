package com.vstavit_nazvanie.readlia;

import static com.vstavit_nazvanie.readlia.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private ListView mNamesBooks; //список с книгами
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
    private int page; // индикатор страницы

    private static class Properties {
        private static boolean nighttheme = true;
        private static String pathSave = "Classic path";

        public static void setNighttheme(boolean value) {
            nighttheme = value;
        }
        public static void setPathSave(String line) {
            //тут надо как-то проверить что есть доступ для записи
            pathSave = line;
        }

        public static String saveProperties() {
            return nighttheme + "\n" + pathSave;
        }

        @NonNull
        @Override
        public String toString() {
            return "Nighttheme = " + nighttheme + "\n" + "Save path = " + pathSave + "\n";
        }
    }
    //end Properties class

    public void savePropertiesAdapter() throws IOException {
        String string = Properties.saveProperties();
        FileOutputStream fos = openFileOutput("Properties", Context.MODE_PRIVATE);
        fos.write(string.getBytes());
        fos.close();
    }
    public void loadProperties() throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Properties")));
        String line = br.readLine();
        if (line.equals("true"))
            Properties.setNighttheme(true);
        else
            Properties.setNighttheme(false);
        line = br.readLine(); // путь сохранения
        if (line != null)
            Properties.setPathSave(line);
    }
    //end Properties costIbl method's

    public void createBookInstance(Book book) throws IOException {
        String save = book.saveInfo();
        FileOutputStream fos = openFileOutput(String.valueOf(book.getId()), Context.MODE_PRIVATE);
        fos.write(save.getBytes());
        fos.close();
    }

    @Override // При запуске приложения (единожды)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(layout.book); // установка главного экрана
        page = 0;
        initFindId();
        setTheme();
    }

    public void setTheme() { // отрисовка цветов темы
        if (Properties.nighttheme) { // темная тема
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
        mNamesBooks = findViewById(id.namesBooks);
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
    public void onDayNightButtonClick(View view) throws IOException {
        Properties.setNighttheme(!Properties.nighttheme);
        savePropertiesAdapter();
        setTheme();
    }

    public void onBookClick(View view) {
        setContentView(layout.book);
        page = 0;
        initFindId();
        setTheme();
    }

    public void onLibraryClick(View view) throws IOException, InterruptedException {
        CreateTestModul tool = new CreateTestModul();
        String[] cities = {"Test", "Самара", "Вологда", "Волгоград", "Саратов", "Воронеж"};
        Book book = new Book();
        ArrayList<Book> booksExample = new ArrayList<>();

        page = 1;
        setContentView(layout.library);
        initFindId();
        setTheme();


        AutoCompleteTextView autoCompleteTextView = findViewById(id.find);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<> (this, layout.find_view , cities);
        autoCompleteTextView.setAdapter(adapter1);
        //функция-запрос на сервер которая вернет Начальный bookExample. Например "Популярное"
        //начало содержимого функции
        booksExample.add(Toolbar.downloadBookInfo(tool.createBook(1), this)); // добавили книгу в массив книг
        booksExample.add(Toolbar.downloadBookInfo(tool.createBook(2), this)); // добавили книгу в массив книг
        booksExample.add(Toolbar.downloadBookInfo(tool.createBook(3), this)); // добавили книгу в массив книг
        booksExample.add(Toolbar.downloadBookInfo(tool.createBook(4), this)); // добавили книгу в массив книг
        booksExample.add(Toolbar.downloadBookInfo(tool.createBook(5), this)); // добавили книгу в массив книг
        booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
        booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
        booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
        booksExample.add(tool.createBook(1)); // добавили книгу в массив книг
        //конец содержимого функции
        //createBookInstance(tool.createModul()); // создание файла примера сохранения

        NetBookAdapter adapter = new NetBookAdapter(this, layout.list_of_book, booksExample);
        // инциализировали адаптер который принимает context, шаблон отображения элемента, список элементов
        mNamesBooks.setAdapter(adapter); //адаптер показывает
        mNamesBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) mNamesBooks.getItemAtPosition(i);
                File fileBook = null;
                try {
                    fileBook = Toolbar.downloadBookForWatch(book, context);
                    TimeUnit.SECONDS.sleep(4);
                } catch (IOException | InterruptedException e) {
                    Log.i("fileBook", String.valueOf(e));
                }
                Intent start = new Intent(context, TXTOpener.class);
                start.putExtra("pathBook", Objects.requireNonNull(fileBook).getAbsolutePath());
                startActivity(start);
            }
        });
    }

    public void onProfileClick(View view) {
        setContentView(layout.profile);
        page = 2;
        initFindId();
        setTheme();
    }
}