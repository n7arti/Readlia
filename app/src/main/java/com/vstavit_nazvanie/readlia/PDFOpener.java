package com.vstavit_nazvanie.readlia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFOpener extends AppCompatActivity {
    PDFView myPDFViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopener);
        myPDFViewer = (PDFView) findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("pdfFileName");
        if (getItem.equals("Книга 1")){
            myPDFViewer.fromAsset("Кислев ПИН-34. Метрология .pdf").load();
        }
    }
}