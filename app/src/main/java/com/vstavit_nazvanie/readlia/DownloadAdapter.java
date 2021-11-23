package com.vstavit_nazvanie.readlia;

import android.util.Log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DownloadAdapter {
    private int progress;
    private final PropertyChangeSupport support;

    public DownloadAdapter(){
        support = new PropertyChangeSupport(this);
    }

    public void setProgress(int progress_value) {
        support.firePropertyChange("progress", this.progress, progress_value);
        Log.i("Download adapter", progress_value + " " + progress);
        progress = progress_value;
    }

    public void addPropertyChangeListener(PropertyChangeListener downloadListener) {
        support.addPropertyChangeListener(downloadListener);
    }
    public void removePropertyChangeListener(PropertyChangeListener downloadListener) {
        support.removePropertyChangeListener(downloadListener);
    }

    public void finishEvent() {
        support.firePropertyChange("finish", false, true);
    }
}
