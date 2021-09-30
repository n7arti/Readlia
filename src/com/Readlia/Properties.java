package com.Readlia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.*;
import java.io.IOException;

public class Properties {
    int brightness;
    String nameFont;
    int sizeFont;
    Color colorText; // будет реальзовано в андроид
    Color colorBG; // будет реализовано в андроид

    Properties() {
        try {
            File file = new File("C:\\Users\\Nastena\\Desktop\\Readlia\\Properties.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            brightness = Integer.parseInt(line);
            line = reader.readLine();
            nameFont = line;
            line = reader.readLine();
            sizeFont = Integer.parseInt(line);
            line = reader.readLine();
            colorText = Color.getColor(line);
            line = reader.readLine();
            colorBG = Color.getColor(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBrightness() {
        return brightness;
    }
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
    public String getNameFont() {
        return nameFont;
    }
    public void setNameFont(String nameFont) {
        this.nameFont = nameFont;
    }
    public int getSizeFont() {
        return sizeFont;
    }
    public void setSizeFont(int sizeFont) {
        this.sizeFont = sizeFont;
    }
    public Color getColorText() {
        return colorText;
    }
    public void setColorText(String colorText) {
        this.colorText = Color.getColor(colorText);
    }
    public Color getColorBG() {
        return colorBG;
    }
    public void setColorBG(String colorBG) {
        this.colorBG = Color.getColor(colorBG);
    }

}
