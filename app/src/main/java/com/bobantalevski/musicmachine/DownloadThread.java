package com.bobantalevski.musicmachine;

import android.os.Looper;
import android.util.Log;

public class DownloadThread extends Thread {

    private static final String TAG = DownloadThread.class.getSimpleName();
    public DownloadHandler handler;

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        Looper.prepare();
        handler = new DownloadHandler();
        Looper.loop();
    }
}
