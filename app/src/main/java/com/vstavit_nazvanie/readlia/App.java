package com.vstavit_nazvanie.readlia;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class App extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this.getApplicationContext();
    }

    public static Bitmap DrawableToBitmap(int drawable) {
        return BitmapFactory.decodeResource(sInstance.getResources(), drawable);
    }

    public static Context getInstance() {
        return sInstance;
    }
}

