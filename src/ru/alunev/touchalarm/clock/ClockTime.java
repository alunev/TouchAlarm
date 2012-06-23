package ru.alunev.touchalarm.clock;

import android.text.format.Time;

public class ClockTime {
    /**
     * Currently picked time
     */
    private int hour;
    private int minute;
    private int second;

    /**
     * Current system time. That is displayed on the clock initially after start-up.
     */
    private Time calendar;

    public ClockTime() {
        setCalendar(new Time());
        timeChanged();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public Time getCalendar() {
        return calendar;
    }

    public void setCalendar(Time calendar) {
        this.calendar = calendar;
    }

    public void timeChanged() {
        calendar.setToNow();

        hour = calendar.hour;
        minute = calendar.minute;
    }

    /**
     * Return Time instance representing time currently picked by the user.
     * @return
     */
    public Time getPickedTime() {
        Time pickedTime = new Time(calendar);
        pickedTime.set(second, minute, hour, pickedTime.monthDay, pickedTime.month, pickedTime.year);

        return pickedTime;
    }
}
