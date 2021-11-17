package com.vstavit_nazvanie.readlia;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TXTOpener extends AppCompatActivity {
    private final int sizeOfList = 1900;
    private String pathBook;
    private int page = 1;
    private boolean click = false;
    private TextView txtOpen;
    private int pageView = 1;
    private String[] text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pathBook = getIntent().getStringExtra("pathBook");
        setContentView(R.layout.activity_txtopener);
        txtOpen = findViewById(R.id.txtOpen);
        text = new String[pageCount()];
        loadTXT();
        txtOpen.setText(text[1]);
    }

    public int pageCount() {
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

    public void loadTXT() {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathBook));

            while ((line = reader.readLine()) != null) {
                line += " ";
                cutString(line);
            }
        }
        catch (IOException e) {
            Log.i("TXTOpener", "File don't open" + String.valueOf(e));
        }
    }

    public void cutString(String line) {
        int countElement = line.length();

        if (text[page] != null) {
            if (text[page].length() + countElement < sizeOfList)
                text[page] += line;
            else {
                int freeSpace = sizeOfList - text[page].length();
                text[page] += line.substring(0, freeSpace);
                this.page++;
                cutString(line.substring(freeSpace));
            }
        }
        else {
            if (countElement < sizeOfList)
                text[page] = line;
            else {
                text[page] = line.substring(0, sizeOfList);
                this.page++;
                cutString(line.substring(sizeOfList));
            }
        }
    }

    public void onMiddleClick(View view) {
        ConstraintLayout constraintLayout= (ConstraintLayout) findViewById(R.id.menu);
        ConstraintLayout constraintLayout1= (ConstraintLayout) findViewById(R.id.menuUp);
        if (click){
            constraintLayout1.removeAllViews();
            constraintLayout.removeAllViews();
        }
        else {
            LayoutInflater ltInflater = getLayoutInflater();
            ltInflater.inflate(R.layout.book_menu_lib_top, constraintLayout1, true);
            ltInflater.inflate(R.layout.book_menu_lib_bottom, constraintLayout, true);

        }
        click = !click;
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        TextView mNowPage =  findViewById(R.id.nowPage);
        TextView mAllPages =  findViewById(R.id.allPages);
        mNowPage.setText(String.valueOf(seekBar.getProgress()));
        mAllPages.setText("/ " + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mNowPage.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onBackClick(View view) {
        if(pageView > 1)
            pageView--;
        txtOpen.setText(text[pageView]);
    }

    public void onForwardClick(View view) {
        if(pageView+1 < pageCount())
            pageView++;
        txtOpen.setText(text[pageView]);
    }
}

