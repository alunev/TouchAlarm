package ru.alunev.touchalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AlarmPlayer extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.train);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }
}
