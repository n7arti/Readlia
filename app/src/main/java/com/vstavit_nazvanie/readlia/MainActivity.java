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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
    private ArrayList<String> authorMass; // Список авторов
    private ArrayList<String> ganreMass; // Список жанров
    private ArrayList<String> bookMass; // Список книг
    private ArrayList<Book> booksExample; // Массив книг для сетевой библиотеки "Популярное"
    private NetBookAdapter netBookAdapter; // Сетева библиотека

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

    public void loadProperties() throws IOException {
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

    public void createTempFileForUnitTest() throws IOException {
        String string = "12345678910";
        FileOutputStream fos = openFileOutput("TempUnitTest", Context.MODE_PRIVATE);
        fos.write(string.getBytes());
        fos.close();
    }

    public File createBookInstance(Book book) throws IOException {
        String save = book.saveInfo();
        File file = new File(String.valueOf(book.getId()));
        FileOutputStream fos = openFileOutput(file.getAbsolutePath(), Context.MODE_PRIVATE);
        fos.write(save.getBytes());
        fos.close();
        return file;
    }

    public void loadNetBookAdapter() {
        if (Properties.nighttheme)
            netBookAdapter = new NetBookAdapter(this, layout.list_of_book, booksExample); // Установка в адаптер листа с книгами
        else
            netBookAdapter = new NetBookAdapter(this, layout.list_of_book_day, booksExample); // Установка в адаптер листа с книгами
    }

    public void startBook(Book book, File finalFileBook) {
        Intent start = new Intent(context, TXTOpener.class);
        start.putExtra("pathBook", Objects.requireNonNull(finalFileBook).getAbsolutePath());
        start.putExtra("nameBook", book.title);
        startActivity(start);
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
        //функция-запрос на сервер которая вернет Начальный bookExample. Например "Популярное"
        booksExample = CreateTestModul.testCreateBookExample(this);
    }

    public void setTheme() { // отрисовка цветов темы
        if (Properties.nighttheme) { // темная тема
            mBackground.setBackgroundColor(ContextCompat.getColor(this, color.dark));
            mTextMain.setTextColor(ContextCompat.getColor(this, color.light));
            mDayNightButton.setImageResource(drawable.ic_day_and_night);
            mMyBookButton.setImageResource(drawable.ic_button);
            mLibraryButton.setImageResource(drawable.ic_button);
            mProfileButton.setImageResource(drawable.ic_button);
            mLine.setBackgroundColor(ContextCompat.getColor(this, color.light));
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
                    netBookAdapter = new NetBookAdapter(this, layout.list_of_book, booksExample); // обновление адаптера
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
        } else { // light theme
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
                    netBookAdapter = new NetBookAdapter(this, layout.list_of_book_day, booksExample); // обновление адаптера
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

    public void onLibraryClick(View view) {
        CreateTestModul tool = new CreateTestModul();
        authorMass = new ArrayList<>();
        ganreMass = new ArrayList<>();
        bookMass = new ArrayList<>();

        page = 1;
        setContentView(layout.library);
        initFindId();
        setTheme();

        // добавить обработку и уведомлять об полученной инфе пользователя
        Toolbar.fillFindList(booksExample, authorMass, ganreMass, bookMass);

        AutoCompleteTextView autoCompleteTextView = findViewById(id.find);
        ArrayAdapter<String> adapterFind = new ArrayAdapter<>(this, layout.find_view, authorMass);
        autoCompleteTextView.setAdapter(adapterFind);

        mNamesBooks.setAdapter(netBookAdapter); //адаптер показывает
        mNamesBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DownloadAdapter downloadAdapter = new DownloadAdapter();
                DownloadWatcher downloadWatcher = new DownloadWatcher();
                downloadAdapter.addPropertyChangeListener(downloadWatcher);
                DateFormat timeFormat = new SimpleDateFormat("ss", Locale.getDefault());
                Date currentDate = new Date();
                String timeText = timeFormat.format(currentDate);


                Book book = (Book) mNamesBooks.getItemAtPosition(i);
                File fileBook = null;
                try {
                    fileBook = Toolbar.downloadBookForWatch(book, downloadAdapter, context);
                } catch (IOException e) {
                    Log.i("fileBook", String.valueOf(e));
                }

                File finalFileBook = fileBook;

                Thread run = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                Log.i("Watcher", String.valueOf(downloadWatcher.getGate()));
                                if (downloadWatcher.getGate()) {
                                    startBook(book, finalFileBook);
                                    break;
                                }
                                Thread.sleep(1000); //1000 - 1 сек
                            } catch (InterruptedException ex) {

                            }
                        }
                    }
                });
                run.start();
            }
        });
    }

    public void onProfileClick(View view) throws Exception {
        setContentView(layout.profile);
        page = 2;
        initFindId();
        setTheme();
    }

    public void onTitleFindClick(View view) {
        ImageButton title = findViewById(id.titleFind);
        ImageButton author = findViewById(id.authorFind);
        ImageButton ganre = findViewById(id.ganreFind);
        ImageButton year = findViewById(id.yearFind);
        title.setImageResource(drawable.ic_find_click);
        author.setImageResource(drawable.ic_find_not_click);
        ganre.setImageResource(drawable.ic_find_not_click);
        year.setImageResource(drawable.ic_find_not_click);
    }

    public void onAuthorFindClick(View view) {
        ImageButton title = findViewById(id.titleFind);
        ImageButton author = findViewById(id.authorFind);
        ImageButton ganre = findViewById(id.ganreFind);
        ImageButton year = findViewById(id.yearFind);
        title.setImageResource(drawable.ic_find_not_click);
        author.setImageResource(drawable.ic_find_click);
        ganre.setImageResource(drawable.ic_find_not_click);
        year.setImageResource(drawable.ic_find_not_click);
    }

    public void onGanreFindClick(View view) {
        ImageButton title = findViewById(id.titleFind);
        ImageButton author = findViewById(id.authorFind);
        ImageButton ganre = findViewById(id.ganreFind);
        ImageButton year = findViewById(id.yearFind);
        title.setImageResource(drawable.ic_find_not_click);
        author.setImageResource(drawable.ic_find_not_click);
        ganre.setImageResource(drawable.ic_find_click);
        year.setImageResource(drawable.ic_find_not_click);
    }

    public void onYearFindClick(View view) {
        ImageButton title = findViewById(id.titleFind);
        ImageButton author = findViewById(id.authorFind);
        ImageButton ganre = findViewById(id.ganreFind);
        ImageButton year = findViewById(id.yearFind);
        title.setImageResource(drawable.ic_find_not_click);
        author.setImageResource(drawable.ic_find_not_click);
        ganre.setImageResource(drawable.ic_find_not_click);
        year.setImageResource(drawable.ic_find_click);
    }
}