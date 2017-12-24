package com.bobantalevski.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private boolean bound = false;
    private PlayerService playerService;
    private Button downloadButton;
    private Button playButton;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            bound = true;
            PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) binder;
            playerService = localBinder.getService();
            if (playerService.isPlaying()) {
                playButton.setText("Pause");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = findViewById(R.id.downloadButton);
        playButton = findViewById(R.id.playButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();

                // Send Messages to Handler for processing
                for (String song : Playlist.songs) {
                    Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                    intent.putExtra(KEY_SONG, song);
                    startService(intent);
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bound) {
                    if (playerService.isPlaying()) {
                        playerService.pause();
                        playButton.setText("Play");
                    } else {
                        playerService.play();
                        playButton.setText("Pause");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConnection);
            // unbindService doesn't result in a call to onServiceDisconnected
            // that's called only if something unexpected happens
            // which is why we need to change the value of bound to false ourselves
            bound = false;
        }
    }
}
