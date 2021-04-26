package ir.awlrhm.areminder.view.acalenar.listener;

import android.animation.Animator;
import android.animation.ValueAnimator;



public abstract class SmallAnimationListener implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        animationUpdate(valueAnimator.getAnimatedValue());
    }

    @Override
    public void onAnimationStart(Animator animator) {
        animationStart(animator);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        animationEnd(animator);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        animationEnd(animator);
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
        // IGNORE
    }

    public abstract void animationStart(Animator animation);

    public abstract void animationEnd(Animator animation);

    public abstract void animationUpdate(Object value);
}
