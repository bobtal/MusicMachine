package com.bobantalevski.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

public class ActivityHandler extends Handler {
    private MainActivity mainActivity;

    /**
     * Default constructor associates this handler with the {@link Looper} for the
     * current thread.
     * <p>
     * If this thread does not have a looper, this handler won't be able to receive messages
     * so an exception is thrown.
     */
    public ActivityHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Subclasses must implement this to receive messages.
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        if (msg.arg1 == 0) {
            // Music is NOT playing
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Play");
            } else {
                // Play the music
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                // Change the play Button to say "Pause"
                mainActivity.changePlayButtonText("Pause");
            }
        } else if (msg.arg1 == 1) {
            // Music is playing
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Pause");
            } else {
                // Pause the music
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                // Change the play Button to say "Play"
                mainActivity.changePlayButtonText("Play");
            }
        } else if (msg.arg1 == 3) {
            mainActivity.changePlayButtonText("Play");
        }
    }
}
