package ir.awlrhm.areminder.view.acalenar.animator;

import android.animation.ValueAnimator;

import ir.awlrhm.areminder.view.acalenar.listener.SmallAnimationListener;

public class CalendarAnimation extends ValueAnimator {
    public void setListener(SmallAnimationListener smallAnimationListener) {
        addUpdateListener(smallAnimationListener);
        addListener(smallAnimationListener);
        start();
    }
}

