package com.vstavit_nazvanie.readlia;

import android.util.Log;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DownloadWatcher implements PropertyChangeListener {
    private boolean gate = false;

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case "finish": {
                Log.i("Download watcher", propertyChangeEvent.getPropertyName());
                this.setGate(true);
                break;
            }
            case "progress": {
                this.checkGate ((int)propertyChangeEvent.getNewValue());
                break;
            }
            case "startDownload": {
                // in MainActivity.DownloadLocalBook
                break;
            }
        }
    }

    public void checkGate(int progress_value) {
        //Log.i("Download watcher", String.valueOf(progress_value));
    }

    public boolean getGate(){
        return gate;
    }
    public void setGate(boolean gate) {
        this.gate = gate;
    }
}
