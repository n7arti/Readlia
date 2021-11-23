package com.vstavit_nazvanie.readlia;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.TOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.text.font.FontFamily;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TXTOpener extends AppCompatActivity implements ColorPickerDialogListener {
    private static int sizeOfList = 2000; //2000
    private static String pathBook;
    private static String nameBook;
    private static int page;
    private static boolean click = false;
    private TextView txtOpen;
    private static int pageView;
    private static String[] text;
    private static int countPage;
    private static boolean nightReadMode = false;
    private static int textDay;
    private static int textNight;
    private static int backgroundDay;
    private static int backgroundNight;


    // for Unittest
    public static void setText(int i) {
        TXTOpener.text = new String[i];
    }

    public static String[] getText() {
        return text;
    }

    public static void setPage(int i) {
        TXTOpener.page = i;
    }

    public static void setSizeOfList(int sizeOfList) {
        TXTOpener.sizeOfList = sizeOfList;
    }

    public static void setPathBook(String pathBook) {
        TXTOpener.pathBook = pathBook;
    }

    //end
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pathBook = getIntent().getStringExtra("pathBook");
        nameBook = getIntent().getStringExtra("nameBook");
        setContentView(R.layout.activity_txtopener);
        txtOpen = findViewById(R.id.txtOpen);
        countPage = pageCount();
        text = new String[countPage];
        page = 1;
        pageView = 1;
        click = false;
        loadTXT();
        txtOpen.setText(text[1]);
        View view = new View(this);
        textDay = ContextCompat.getColor(this, R.color.black);
        textNight = ContextCompat.getColor(this, R.color.light);
        backgroundDay = ContextCompat.getColor(this, R.color.white);
        backgroundNight = ContextCompat.getColor(this, R.color.dark);
        onMiddleClick(view);
    }

    public static int pageCount() {
        int count = 0;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathBook));
            while ((line = reader.readLine()) != null) {
                count += line.length() + 1;
            }
        } catch (IOException e) {
            Log.i("TXTOpener", "File don't open" + String.valueOf(e));
        }
        count = count / sizeOfList + 2;
        return count;
    }


    public static void loadTXT() {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathBook));

            while ((line = reader.readLine()) != null) {
                line += " ";
                cutString(line);
            }
        } catch (IOException e) {
            Log.i("TXTOpener", "File don't open" + String.valueOf(e));
        }
    }

    public static void cutString(String line) {
        int countElement = line.length();

        if (text[page] != null) {
            if (text[page].length() + countElement < sizeOfList)
                text[page] += line;
            else {
                int freeSpace = sizeOfList - text[page].length();
                text[page] += line.substring(0, freeSpace);
                page++;
                cutString(line.substring(freeSpace));
            }
        } else {
            if (countElement < sizeOfList)
                text[page] = line;
            else {
                text[page] = line.substring(0, sizeOfList);
                page++;
                cutString(line.substring(sizeOfList));
            }
        }
    }


    public void onMiddleClick(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.menu);
        ConstraintLayout constraintLayout1 = (ConstraintLayout) findViewById(R.id.menuUp);
        if (click) {
            constraintLayout1.removeAllViews();
            constraintLayout.removeAllViews();
            //txtOpen.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        } else {
            //final ConstraintLayout.LayoutParams lparams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,550); // Width , height
            //txtOpen.setLayoutParams(lparams);
            LayoutInflater ltInflater = getLayoutInflater();
            ltInflater.inflate(R.layout.book_menu_lib_top, constraintLayout1, true);
            ltInflater.inflate(R.layout.book_menu_lib_bottom, constraintLayout, true);
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarText);
            TextView mNowPage = findViewById(R.id.nowPage);
            TextView mAllPages = findViewById(R.id.allPages);
            TextView mnameBook = findViewById(R.id.mNameBook);
            mnameBook.setText(nameBook);
            mNowPage.setText(String.valueOf(seekBar.getProgress()));
            seekBar.setMax(countPage - 1);
            seekBar.setProgress(pageView);
            mAllPages.setText("/ " + seekBar.getMax());
            mNowPage.setText(String.valueOf(seekBar.getProgress()));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (seekBar.getProgress() == 0)
                        pageView = 1;
                    else
                        pageView = seekBar.getProgress();
                    mNowPage.setText(String.valueOf(pageView));
                    txtOpen.setText(text[pageView]);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }
        click = !click;
        setViewReadMode();
    }

    public void onBackClick(View view) {
        if (pageView > 1)
            pageView--;
        txtOpen.setText(text[pageView]);
    }

    public void onForwardClick(View view) {
        if (pageView + 1 < countPage)
            pageView++;
        txtOpen.setText(text[pageView]);
    }

    public void onBackToLibraryClick(View view) {
        finish();
    }


    @SuppressLint("ResourceType")
    public void onSettingsClick(View view) {
        //boolean canWrite  = Settings.System.canWrite(this);

        // If app has no permission to write system settings
        //if (!canWrite) {
        // allowWritePermission();
        //}
        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.tapMiddle);
        //parentLayout.setLayoutParams(new LayoutParams(TOP));
        LayoutInflater ltInflater = getLayoutInflater();
        ltInflater.inflate(R.layout.settings_read, parentLayout, true);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView bright = findViewById(R.id.bright);

        try {
            int curBrightnessValue = Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
            seekBar.setProgress(curBrightnessValue);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        bright.setText("Яркость " + seekBar.getProgress() + "%");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0)
                    i = 1;
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = (float) i / 100;
                getWindow().setAttributes(layoutParams);
                bright.setText("Яркость " + i + "%");
                Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,
                        i / 100 * 255);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Spinner spinner = findViewById(R.id.spinner);
        /*Typeface type1 = txtOpen.getTypeface();
        Log.i("type", String.valueOf(type1.hashCode())); // не могу из typeface вытащить шрифт
        String selected = spinner.getSelectedItem().toString();
        if (selected.equals("Constacia Modern"))
            selected = "constacia_modern.ttf";
        else if (selected.equals("Constacia Modern Deco"))
            selected = "constacia_modern_deco.ttf";
        else selected = selected.toLowerCase() + ".xml";
        Log.i("font", selected);
        Typeface type = Typeface.createFromFile(
                "G:/Readlia_pdf/app/src/main/res/font/" + selected); // не находит путь
        txtOpen.setTypeface(type);*/
        ConstraintLayout colorText = findViewById(R.id.colorText); //считывание с файла и установка textDay(Night) backgroundDay(Night)
        ConstraintLayout colorFon = findViewById(R.id.colorFon);
        if (nightReadMode){
        colorText.setBackgroundColor(textNight);
        colorFon.setBackgroundColor(backgroundNight);
        }
        else{
            colorText.setBackgroundColor(textDay);
            colorFon.setBackgroundColor(backgroundDay);
        }


    }

    private void allowWritePermission() {
        Intent intent = new Intent(
                Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:$packageName")
        );
        startActivity(intent);
    }

    public void onSpaceClick(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.tapMiddle);
        constraintLayout.removeAllViews();
    }

    public void onPlusClick(View view) {
        TextView sizeText = findViewById(R.id.sizeText);
        float size = Float.parseFloat((String) sizeText.getText());
        int size1 = Integer.parseInt((String) sizeText.getText());
        size++;
        size1++;
        sizeText.setText(String.valueOf(size1));
        txtOpen.setTextSize(size);

    }

    public void onMinusClick(View view) {
        TextView sizeText = findViewById(R.id.sizeText);
        float size = Float.parseFloat((String) sizeText.getText());
        int size1 = Integer.parseInt((String) sizeText.getText());
        size--;
        size1--;
        sizeText.setText(String.valueOf(size1));
        txtOpen.setTextSize(size);

    }

    public void onSClick(View view) {
    }

    public void onColorTextClick(View view) {
        final int firstId = 1;
        createColorPickerDialog(firstId);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case 1: {
                ConstraintLayout colorText = findViewById(R.id.colorText);
               if (nightReadMode)
                    textNight = color;

                else
                    textDay = color;
                colorText.setBackgroundColor(color);
                break;
            }
            case 2:{
                ConstraintLayout colorFon = findViewById(R.id.colorFon);
                if (nightReadMode)
                    backgroundNight = color;
                else
                    backgroundDay = color;
                colorFon.setBackgroundColor(color);
                break;
            }
        }
        setViewReadMode();
    }

        @Override
        public void onDialogDismissed ( int dialogId){
            //Toast.makeText(this, "Dialog dismissed", Toast.LENGTH_SHORT).show();

        }
        private void createColorPickerDialog ( int id){
            ColorPickerDialog.newBuilder()
                    .setColor(Color.RED)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowCustom(true)
                    .setAllowPresets(true)
                    .setColorShape(ColorShape.CIRCLE)
                    .setDialogId(id)
                    .show(this);
        }

        public void onColorFonClick (View view){
            final int firstId = 2;
            createColorPickerDialog(firstId);
        }

    public void onReadModeClick(View view) {
        nightReadMode = !nightReadMode;
        setViewReadMode();
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    public void setViewReadMode(){
        ConstraintLayout background = findViewById(R.id.background);
        if (nightReadMode){
            txtOpen.setTextColor(textNight);
            background.setBackgroundColor(backgroundNight);
            if (click){
                ConstraintLayout top = findViewById(R.id.top);
                ConstraintLayout bottom = findViewById(R.id.bottom);
                ConstraintLayout lineTop = findViewById(R.id.line_top);
                ConstraintLayout lineBottom = findViewById(R.id.line_bottom);
                ImageButton back = findViewById(R.id.back);
                ImageButton download = findViewById(R.id.download);
                ImageButton settings = findViewById(R.id.settings_icon);
                TextView nameBook = findViewById(R.id.mNameBook);
                TextView nowPage = findViewById(R.id.nowPage);
                TextView allPages = findViewById(R.id.allPages);
                SeekBar seekBar = findViewById(R.id.seekBarText);
                top.setBackgroundColor(backgroundNight);
                bottom.setBackgroundColor(backgroundNight);
                lineTop.setBackgroundColor(textNight);
                lineBottom.setBackgroundColor(textNight);
                back.setImageResource(R.drawable.ic_back_icon_com_72866);
                nameBook.setTextColor(textNight);
                download.setImageResource(R.drawable.ic_download_icon);
                settings.setImageResource(R.drawable.ic_settings_icon);
                nowPage.setTextColor(textNight);
                allPages.setTextColor(textNight);
            }

        }
        else {
            txtOpen.setTextColor(textDay);
            background.setBackgroundColor(backgroundDay);
            if (click){
                int text = ContextCompat.getColor(this,R.color.text_light);
                ConstraintLayout top = findViewById(R.id.top);
                ConstraintLayout bottom = findViewById(R.id.bottom);
                ConstraintLayout lineTop = findViewById(R.id.line_top);
                ConstraintLayout lineBottom = findViewById(R.id.line_bottom);
                ImageButton back = findViewById(R.id.back);
                ImageButton download = findViewById(R.id.download);
                ImageButton settings = findViewById(R.id.settings_icon);
                TextView nameBook = findViewById(R.id.mNameBook);
                TextView nowPage = findViewById(R.id.nowPage);
                TextView allPages = findViewById(R.id.allPages);
                SeekBar seekBar = findViewById(R.id.seekBarText);
                top.setBackgroundColor(backgroundDay);
                bottom.setBackgroundColor(backgroundDay);
                lineTop.setBackgroundColor(textDay);
                lineBottom.setBackgroundColor(textDay);
                back.setImageResource(R.drawable.ic_back_icon_day);
                nameBook.setTextColor(text);
                download.setImageResource(R.drawable.ic_download_icon_day);
                settings.setImageResource(R.drawable.ic_settings_icon_day);
                nowPage.setTextColor(text);
                allPages.setTextColor(text);
            }
        }

    }
}

