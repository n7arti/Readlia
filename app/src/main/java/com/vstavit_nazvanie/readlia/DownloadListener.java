package com.vstavit_nazvanie.readlia;

public interface DownloadListener {
    int progress = 0;

    void setProgress(int progress_value);
    void register(DownloadListener downloadListener);
    void remove(DownloadListener downloadListener);
    void notifyListener();
}
