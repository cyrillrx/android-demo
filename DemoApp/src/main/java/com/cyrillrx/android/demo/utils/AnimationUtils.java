package com.cyrillrx.android.demo.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * @author Cyril Leroux
 *         Created on 08/06/15
 */
public class AnimationUtils {

    public static final String ALPHA = "alpha";
    public static final String ROTATION = "rotation";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";

    private AnimationUtils() {
        //No instances.
    }

    public static PropertyValuesHolder alpha(float... values) {
        return PropertyValuesHolder.ofFloat(ALPHA, values);
    }

    public static PropertyValuesHolder rotation(float... values) {
        return PropertyValuesHolder.ofFloat(ROTATION, values);
    }

    public static PropertyValuesHolder translationX(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_X, values);
    }

    public static PropertyValuesHolder translationY(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_Y, values);
    }

    public static PropertyValuesHolder scaleX(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_X, values);
    }

    public static PropertyValuesHolder scaleY(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_Y, values);
    }

    public static void fadeIn(final View view) {

        view.setVisibility(View.VISIBLE);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, AnimationUtils.ALPHA, view.getAlpha(), 1f);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    public static void fadeOut(final View view) {

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, AnimationUtils.ALPHA, view.getAlpha(), 0f);
        objectAnimator.setDuration(400);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) { view.setVisibility(View.GONE); }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        objectAnimator.start();
    }

    public static ObjectAnimator fadeInAnimator(final View view) {
        view.setVisibility(View.VISIBLE);
        return ObjectAnimator.ofFloat(view, AnimationUtils.ALPHA, view.getAlpha(), 1f);
    }

    public static ObjectAnimator fadeOutAnimator(final View view) {
        return ObjectAnimator.ofFloat(view, AnimationUtils.ALPHA, view.getAlpha(), 0f);
    }
}
