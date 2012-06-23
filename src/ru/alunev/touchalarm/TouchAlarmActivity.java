package ru.alunev.touchalarm;

import ru.alunev.touchalarm.components.TouchClock;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TouchAlarmActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // init our controller
        TouchAlarmController.getInstance().setWindow(getWindow());
        TouchAlarmController.getInstance().setActivityContext(this);

        TouchClock touchClock = (TouchClock) findViewById(R.id.TouchClock);
        TouchAlarmController.getInstance().onTimeChanged(touchClock.getClockModel().getClockTime());

        // set alarm time button
        Button button = (Button) findViewById(R.id.SetButton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // why I'm getting it through view ?!?!?!?
                TouchClock touchClock = (TouchClock) findViewById(R.id.TouchClock);
                TouchAlarmController.getInstance().scheduleAlarm(touchClock.getClockModel().getClockTime());
            }
        });
        
        // shutup button
        button = (Button) findViewById(R.id.ShutUp);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TouchAlarmController.getInstance().stopPlay(v.getContext());
            }
        });
    }
}