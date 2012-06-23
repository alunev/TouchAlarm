package ru.alunev.touchalarm;

import ru.alunev.touchalarm.clock.ClockTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.widget.TextView;

public class TouchAlarmController {
    private static TouchAlarmController instance;
    private Window window;
    private Context activityContext;

    private TouchAlarmController() {
    }

    public static TouchAlarmController getInstance() {
        if (instance == null) {
            instance = new TouchAlarmController();
        }

        return instance;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Context getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(Context context) {
        this.activityContext = context;
    }

    public void scheduleAlarm(ClockTime clockTime) {
        Intent intent = new Intent(activityContext, TouchAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(activityContext, 0, intent, 0);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) activityContext.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, clockTime.getPickedTime().toMillis(false), sender);
    }

    public void startPlay(Context context) {
        Intent playAlarm = new Intent("com.android.alarmclock.PLAY_ALARM");
        context.startService(playAlarm);
    }

    public void stopPlay(Context context) {
        Intent playAlarm = new Intent("com.android.alarmclock.PLAY_ALARM");
        context.stopService(playAlarm);
    }

    /**
     * Event method that is called then user changes time selection.
     * As the number of subscribers grows we may implement true Event-Listener pattern.
     * @param clockTime
     */
    public void onTimeChanged(ClockTime clockTime) {
        TextView digitalTime = (TextView) window.findViewById(R.id.DigitalTime);
        digitalTime.setText(clockTime.getHour() + ":" + clockTime.getMinute());
    }
}
