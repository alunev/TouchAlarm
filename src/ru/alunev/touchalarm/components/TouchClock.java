package ru.alunev.touchalarm.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RemoteViews.RemoteView;

import ru.alunev.touchalarm.TouchAlarmController;
import ru.alunev.touchalarm.clock.ClockModel;
import ru.alunev.touchalarm.utils.AnalytFuncs;

@RemoteView
public class TouchClock extends View {
    private static TouchAlarmController LOG = TouchAlarmController.getInstance();

    private Drawable dial;
    private Drawable hourHand;
    private Drawable minuteHand;
    private Drawable secondHand;

    private ClockModel clockModel;

    public TouchClock(Context context) {
        this(context, null);
    }

    public TouchClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchClock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Resources r = context.getResources();

        setDial(r.getDrawable(ru.alunev.touchalarm.R.drawable.dial));
        setHourHand(r.getDrawable(ru.alunev.touchalarm.R.drawable.hour_hand));
        setMinuteHand(r.getDrawable(ru.alunev.touchalarm.R.drawable.minute_hand));

        setClockModel(new ClockModel());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // read time changed flag and reset it
        boolean needReDraw = clockModel.isTimeChanged();
        if (needReDraw) {
            clockModel.setTimeChanged(false);
        }

        // set values to our clock coordinates model
        clockModel.setAreaWidth(getWidth());
        clockModel.setAreaHeight(getHeight());
        clockModel.setIntrinsicWdth(dial.getIntrinsicWidth());
        clockModel.setIntrinsicHeight(dial.getIntrinsicHeight());

        // do we need to scale everything or we have enough space to draw dial in native size?
        boolean scaled = false;
        if (!clockModel.areaSizeEnough()) {
            scaled = true;
            float scale = clockModel.getScale();

            // push canvas state and scale it(enlarge) to hold our dial
            canvas.save();
            canvas.scale(scale, scale, clockModel.getCenterX(), clockModel.getCenterY());
        }

        final Drawable dial = getDial();
        if (needReDraw) {
            // something changed - re-set bounds and then draw
            dial.setBounds(clockModel.getLeftBound(), clockModel.getTopBound(),
                    clockModel.getRightBound(), clockModel.getBottomBound());
        }
        dial.draw(canvas);

        // push canvas again before drawing hour hand
        // and rotate underlying dial by the hours angle counterclockwise and draw hand vertically on it
        canvas.save();
        canvas.rotate(clockModel.getHoursAngle(), clockModel.getCenterX(), clockModel.getCenterY());
        final Drawable hourHand = getHourHand();
        if (needReDraw) {
            // something changed so update hour hand too
            hourHand.setBounds(
                    clockModel.getCenterX() - hourHand.getIntrinsicWidth() / 2,
                    clockModel.getCenterY() - hourHand.getIntrinsicHeight(),
                    clockModel.getCenterX() + hourHand.getIntrinsicWidth() / 2,
                    clockModel.getCenterY());
        }
        hourHand.draw(canvas);
        canvas.restore();

        // all the same for minute hand
        canvas.save();
        canvas.rotate(clockModel.getMinutesAngle(), clockModel.getCenterX(), clockModel.getCenterY());
        final Drawable minuteHand = getMinuteHand();
        if (needReDraw) {
            minuteHand.setBounds(
                    clockModel.getCenterX() - minuteHand.getIntrinsicWidth() + minuteHand.getIntrinsicWidth() / 2,
                    clockModel.getCenterY() - minuteHand.getIntrinsicHeight(),
                    clockModel.getCenterX() + minuteHand.getIntrinsicWidth() / 2,
                    clockModel.getCenterY());
        }
        minuteHand.draw(canvas);
        canvas.restore();

        // reduce our canvas to natural size
        if (scaled) {
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onTimeChanged();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // LOG.appendToLog(event.getAction() + " " + event.getX() + ", " + event.getY());

        // coordinates of minute hand vector
        float minuteX = event.getX() - clockModel.getCenterX();
        float minuteY = - (event.getY() - clockModel.getCenterY());

        // update model with UI change and rise flag to redraw handles
        clockModel.setMinutesAngle(AnalytFuncs.getVectorAngle(minuteX, minuteY));
        clockModel.setTimeChanged(true);
        this.invalidate();

        /**
         * notify other components
         */
        TouchAlarmController.getInstance().onTimeChanged(clockModel.getClockTime());

        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return super.onTrackballEvent(event);
    }

    private void onTimeChanged() {
        clockModel.timeChanged();
    }

    public ClockModel getClockModel() {
        return clockModel;
    }

    public void setClockModel(ClockModel clockCoords) {
        this.clockModel = clockCoords;
    }

    public Drawable getHourHand() {
        return hourHand;
    }

    public void setHourHand(Drawable hourHand) {
        this.hourHand = hourHand;
    }

    public Drawable getMinuteHand() {
        return minuteHand;
    }

    public void setMinuteHand(Drawable minuteHand) {
        this.minuteHand = minuteHand;
    }

    public Drawable getSecondHand() {
        return secondHand;
    }

    public void setSecondHand(Drawable secondHand) {
        this.secondHand = secondHand;
    }

    public Drawable getDial() {
        return dial;
    }

    public void setDial(Drawable dial) {
        this.dial = dial;
    }
}
