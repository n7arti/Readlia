package com.vstavit_nazvanie.readlia;

import static com.vstavit_nazvanie.readlia.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.FileInfo;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity  {
    private final Context context = this;
    private static int page; // индикатор страницы
    private static int countSaveMyBook = 0;
    private static MyBook myBook; // Текущая рабочая книга
    private static Book ThatBook; // Текущая рабочая книга
    private AutoCompleteTextView autoCompleteTextView;
    private ListView mNamesBooks; //список с книгами
    private ConstraintLayout mBackground; // цвет фона
    private ConstraintLayout mLine; // линия около заголовка
    private ImageButton mDayNightButton;// кнопка день\ночь
    private ImageButton mMyBookButton; // кнопка мои книги
    private ImageButton mLibraryButton; // кнопка библиотека
    private ImageButton mProfileButton; // кнопка профиль
    private TextView mlittleMyBookText; // названия кнопоки
    private TextView mlittleLibraryText; // названия кнопоки
    private TextView mlittleProfileText; // названия кнопоки
    private TextView mTextMain; // цвет текста заголовка окна
    private ImageView mBookIcon; // иконка
    private ImageView mLibraryIcon; // иконка
    private ImageView mProfileIcon; // иконка
    private static ArrayList<String> authorMass = new ArrayList<>(); // Список авторов
    private static ArrayList<String> ganreMass = new ArrayList<>(); // Список жанров
    private static ArrayList<String> bookMass = new ArrayList<>(); // Список книг
    private static ArrayList<String> yearMass = new ArrayList<>(); // Список книг
    private static ArrayList<Book> booksExample; // Массив книг для сетевой библиотеки "Популярное"
    private static ArrayList<MyBook> localBookMass = new ArrayList<>(); // Список сохраненных книг
    private static NetBookAdapter netBookAdapter; // Адаптер для "Сетева библиотека"
    private static LocalBookAdapter localBookAdapter; // Адаптер для "Мои книги"
    protected static DownloadAdapter downloadAdapter = new DownloadAdapter(); // Ловец событий


    public static MyBook getMyBook(){
        return myBook;
    }

    private class DownloadLocalBook implements PropertyChangeListener {
        public DownloadLocalBook() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            switch (propertyChangeEvent.getPropertyName()) {
                case "startDownload": {
                    this.startDownload((String) propertyChangeEvent.getNewValue())  ;
                    break;
                }
            }
            Log.i("ToLocalLibrary", "activity");
        }

        public void startDownload(String pathToBook) {
            Log.i("ToLocalLibrary", "in method");
            OutputStream fos = null;
            InputStream fis = null;
            File fileOut = new File(pathToBook);
            File fileIn = new File(countSaveMyBook + "Book");
            try {
                fos = openFileOutput(fileIn.getName(), Context.MODE_PRIVATE);
                fis = new FileInputStream(fileOut);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    Log.i("ToLocalLibrary", "finish");
                    Log.i("ToLocalLibrary", "fis " + pathToBook);
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myBook = new MyBook(ThatBook);
                myBook.setPathToBook(fileIn.getAbsolutePath());
                myBook.setPageCount(TXTOpener.getPageCount());
                myBook.setPageNumber(TXTOpener.getPageView());
                Log.i("wtf", myBook.toString());
                localBookMass.add(myBook);
                Log.i("AddMyBook", "Add book with id" + myBook.getId());
                saveLocalLibrary();
                countSaveMyBook++;
                savePropertiesAdapter();
            }
        }
    }
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
            return nighttheme + "\n" + countSaveMyBook + "\n" + pathSave;
        }

        @NonNull
        @Override
        public String toString() {
            return "Nighttheme = " + nighttheme + "\n" + "Save path = " + pathSave + "\n" +
                    "Count save MyBook = " + countSaveMyBook + "\n";
        }
    }
    //end Properties class

    public void savePropertiesAdapter() {
        String saveString = Properties.saveProperties();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("Properties", Context.MODE_PRIVATE);
            fos.write(saveString.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadProperties() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Properties")));
        String line = br.readLine(); // тема
        if (line.equals("true"))
            Properties.setNighttheme(true);
        else
            Properties.setNighttheme(false);
        line = br.readLine(); // Количество книг
        countSaveMyBook = Integer.parseInt(line);
        line = br.readLine(); // путь сохранения
        if (line != null)
            Properties.setPathSave(line);

        br.close();
    }
    //end Properties costIbl method's

    public void saveLocalLibrary() {
        StringBuilder saveString = new StringBuilder();


        saveString.append(localBookMass.get(countSaveMyBook).toString());
        try {
            FileOutputStream fos = openFileOutput( (countSaveMyBook) + "MyBookInfo", Context.MODE_PRIVATE);
            fos.write(saveString.toString().getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLocalLibrary() throws IOException {
        for (int i = 0; i < countSaveMyBook; i++) {
            MyBook myBook = new MyBook();
            File file = new File(i + "MyBookInfo"); // инпут стрим надо
            myBook.loadInfo(file);
            localBookMass.add(myBook);
        }
    }

    public void printLocalLibrary() {
        for (int i = 0; i < localBookMass.size(); i++) {
            Log.i("PRINT", localBookMass.get(i).toString());
        }
    }
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

    public void startLocalBook(MyBook mybook_value, File finalFileBook) {
        myBook = mybook_value;
        Intent start = new Intent(context, TXTOpener.class);
        start.putExtra("Book", true);
        startActivity(start);
    }

    public void startNetBook(Book book, File finalFileBook) {
        ThatBook = book;
        Intent start = new Intent(context, TXTOpener.class);
        start.putExtra("pathBook", Objects.requireNonNull(finalFileBook).getAbsolutePath());
        start.putExtra("nameBook", book.title);
        start.putExtra("id", book.getId());
        startActivity(start);
    }

    public void setFindList(int id) {
        ArrayAdapter<String> adapterFind = null;
        switch (id) {
            case 0: {
                adapterFind = new ArrayAdapter<>(this, layout.find_view, bookMass);
                break;
            }
            case 1: {
                adapterFind = new ArrayAdapter<>(this, layout.find_view, authorMass);
                break;
            }
            case 2: {
                adapterFind = new ArrayAdapter<>(this, layout.find_view, ganreMass);
                break;
            }
            case 3: {
                adapterFind = new ArrayAdapter<>(this, layout.find_view, yearMass);
                break;
            }
        }
        autoCompleteTextView.setAdapter(adapterFind);
    }

    @Override // При запуске приложения (единожды)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadProperties();
            loadLocalLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(layout.book); // установка главного экрана
        page = 0;
        initFindId();
        setTheme();
        //функция-запрос на сервер которая вернет Начальный bookExample. Например "Популярное"
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                booksExample = CreateTestModul.testCreateBookExample(context);
            }
        });
        run.start();

        // Подписка на события
        DownloadLocalBook downloadLocalBook = new DownloadLocalBook();
        downloadAdapter.addPropertyChangeListener(downloadLocalBook);
        // end
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
        autoCompleteTextView = findViewById(id.find);
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
        if (page == 1) {
            //loadNetBookAdapter();
            mNamesBooks.setAdapter(netBookAdapter);
        }
    }

    public void onBookClick(View view) {
        setContentView(layout.book);
        page = 0;
        initFindId();
        setTheme();
    }

    public void onLibraryClick(View view) {
        CreateTestModul tool = new CreateTestModul();

        page = 1;
        setContentView(layout.library);
        initFindId();
        setTheme();
        printLocalLibrary();
        // добавить обработку и уведомлять об полученной инфе пользователя
        Toolbar.fillFindList(booksExample, authorMass, ganreMass, bookMass, yearMass);
        setFindList(0); // установка поиска по названию
        mNamesBooks.setAdapter(netBookAdapter); //адаптер показывает
        mNamesBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DownloadWatcher downloadWatcher = new DownloadWatcher();
                downloadAdapter.addPropertyChangeListener(downloadWatcher);
                DateFormat timeFormat = new SimpleDateFormat("ss", Locale.getDefault());
                File fileBook = null;


                Book book = (Book) mNamesBooks.getItemAtPosition(i);
                try {
                    fileBook = Toolbar.downloadBookForWatch(book, downloadAdapter, context);
                } catch (IOException e) {
                    Log.i("fileBook", String.valueOf(e));
                }
                File finalFileBook = fileBook;

                Thread run = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //this.setDeamon(true);
                        while (true) {
                            try {
                                Log.i("Watcher", String.valueOf(downloadWatcher.getGate()));
                                if (downloadWatcher.getGate()) {
                                    startNetBook(book, finalFileBook);
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
        setFindList(0); // установка поиска по названию

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
        setFindList(1);  // установка поиска по названию

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
        setFindList(2);  // установка поиска по названию

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
        setFindList(3);  // установка поиска по названию

    }
}