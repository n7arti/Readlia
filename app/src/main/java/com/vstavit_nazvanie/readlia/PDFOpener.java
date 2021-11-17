/*
package com.vstavit_nazvanie.readlia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFOpener extends AppCompatActivity {
    PDFView myPDFViewer;
    boolean click = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopener);
        myPDFViewer = (PDFView) findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("pdfFileName");
        if (getItem.equals("Test")) {
            myPDFViewer.fromAsset("Кислев ПИН-34. Метрология .pdf").load();
        }
    }

    public void onPDFClick(View view) {
        ConstraintLayout constraintLayout= (ConstraintLayout) findViewById(R.id.menu);
        if (click){
            myPDFViewer.removeAllViews();
            constraintLayout.removeAllViews();
        }
        else {
            LayoutInflater ltInflater = getLayoutInflater();
            ltInflater.inflate(R.layout.book_menu_lib_top, myPDFViewer, true);
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
}
*/
