package com.bobantalevski.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler{
    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService service;

    /**
     * Subclasses must implement this to receive messages.
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        downloadSong(msg.obj.toString());
        service.stopSelf(msg.arg1);
    }

    private void downloadSong(String song) {
        long endTime =  System.currentTimeMillis() + 10 * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, song + " downloaded");
    }

    public void setService(DownloadService service) {
        this.service = service;
    }
}
