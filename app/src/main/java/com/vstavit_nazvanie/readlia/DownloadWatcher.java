package com.vstavit_nazvanie.readlia;

import android.util.Log;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DownloadWatcher implements PropertyChangeListener {
    private boolean gate = false;

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("finish")) {
            Log.i("Download watcher", propertyChangeEvent.getPropertyName());
            this.setGate(true);
        }
        else
            this.checkGate ((int)propertyChangeEvent.getNewValue());
    }

    public void checkGate(int progress_value) {
        Log.i("Download watcher", String.valueOf(progress_value));
    }

    public boolean getGate(){
        return gate;
    }
    public void setGate(boolean gate) {
        this.gate = gate;
    }
}
