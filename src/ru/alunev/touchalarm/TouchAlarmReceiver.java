package ru.alunev.touchalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TouchAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // start TouchAlarm activity
        // Intent newIntent = new Intent(context, TouchAlarmActivity.class);
        // newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(newIntent);
        TouchAlarmController.getInstance().startPlay(context);

        Toast.makeText(context, "TA-DA!!! Getup bullshit!", Toast.LENGTH_SHORT).show();
    }
}
