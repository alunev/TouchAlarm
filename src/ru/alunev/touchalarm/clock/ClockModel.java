package ru.alunev.touchalarm.clock;

public class ClockModel {
    private ClockTime clockTime;

    private int areaWidth;
    private int areaHeight;
    private int intrinsicWdth;
    private int intrinsicHeight;

    private boolean timeChanged;

    public ClockModel() {
        clockTime = new ClockTime();
    }

    public int getAreaWidth() {
        return areaWidth;
    }

    public void setAreaWidth(int areaWidth) {
        this.areaWidth = areaWidth;
    }

    public int getAreaHeight() {
        return areaHeight;
    }

    public void setAreaHeight(int areaHeight) {
        this.areaHeight = areaHeight;
    }

    public int getIntrinsicWdth() {
        return intrinsicWdth;
    }

    public void setIntrinsicWdth(int intrinsicWdth) {
        this.intrinsicWdth = intrinsicWdth;
    }

    public int getIntrinsicHeight() {
        return intrinsicHeight;
    }

    public void setIntrinsicHeight(int intrinsicHeight) {
        this.intrinsicHeight = intrinsicHeight;
    }

    public int getCenterX() {
        return areaWidth / 2;
    }

    public int getCenterY() {
        return areaHeight / 2;
    }

    public ClockTime getClockTime() {
        return clockTime;
    }

    public float getHoursAngle() {
        return (clockTime.getHour() + clockTime.getMinute() / 60.0f) / 12.0f * 360.0f;
    }

    public void setHoursAngle(double angle) {
        clockTime.setHour((int) Math.ceil(angle / 360.0f * 12.0f));
    }

    public float getMinutesAngle() {
        return clockTime.getMinute() / 60.0f * 360.0f;
    }

    public void setMinutesAngle(double angle) {
        int oldMinute = clockTime.getMinute();
        int oldHour = clockTime.getHour();
        
        int newMinute = (int) Math.ceil(angle / 360.0f * 60.0f);
        int newHour = 0;
        
        // convert 60 minutes to 00 minutes
        if (newMinute == 60) {
            newMinute = 0;
        }
        
        // determine whether we need to increase/decrease hours
        if ((oldMinute > 45 && oldMinute < 60) && (newMinute >= 0 && newMinute < 15)) {
            newHour = (oldHour + 1) % 24;
        } else if ((oldMinute >= 0 && oldMinute < 15) && (newMinute > 45 && newMinute < 60)) {
            newHour = ((oldHour - 1) + 24) % 24;
        } else {
            newHour = oldHour;
        }
        
        clockTime.setMinute(newMinute);
        clockTime.setHour(newHour);
    }

    public void timeChanged() {
        setTimeChanged(true);
        clockTime.timeChanged();
    }

    public int getLeftBound() {
        return getCenterX() - (intrinsicWdth / 2);
    }

    public int getTopBound() {
        return getCenterY() - (intrinsicHeight / 2);
    }

    public int getRightBound() {
        return getCenterX() + (intrinsicWdth / 2);
    }

    public int getBottomBound() {
        return getCenterY() + (intrinsicHeight / 2);
    }

    public float getScale() {
        return Math.min((float) getAreaWidth()/ (float) getIntrinsicWdth(),
                (float) getAreaHeight()/ (float) getIntrinsicHeight());
    }

    public boolean areaSizeEnough() {
        return getAreaWidth() >= getIntrinsicWdth() && getAreaHeight() >= getIntrinsicHeight();
    }

    public boolean isTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(boolean timeChanged) {
        this.timeChanged = timeChanged;
    }
}
