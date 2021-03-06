package com.bobantalevski.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

public class PlayerHandler extends Handler {
    private PlayerService playerService;

    public PlayerHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Subclasses must implement this to receive messages.
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        if (msg.replyTo != null) {
            playerService.activityMessenger = msg.replyTo;
        }
        switch (msg.arg1) {
            case 0: // Play
                playerService.play();
                break;
            case 1: // Pause
                playerService.pause();
                break;
            case 2: // isPlaying
                int isPlaying = playerService.isPlaying() ? 1 : 0;
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                if (msg.arg2 == 1) {
                    message.arg2 = 1;
                }
                message.replyTo = playerService.serviceMessenger;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
