package com.cyrillrx.android.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.cyrillrx.android.demo.utils.AnimationUtils;
import com.cyrillrx.android.toolbox.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 08/06/15
 */
public class CustomPopup extends FrameLayout {

    private static final String TAG = CustomPopup.class.getSimpleName();

    private PopupWindow.OnDismissListener mDismissListener;

    private View mOverlay;
    private View mOrigin;
    private View mBtn1;
    private View mBtn2;
    private View mBtn3;

    private boolean mShowing;

    public CustomPopup(Context context) {
        super(context);
        init(context);
    }

    public CustomPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomPopup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomPopup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context) {
        inflate(context, R.layout.layout_popup, this);
        mOverlay = findViewById(R.id.overlay);
        mOrigin = findViewById(R.id.origin);
        mBtn1 = findViewById(R.id.btn_1);
        mBtn2 = findViewById(R.id.btn_2);
        mBtn3 = findViewById(R.id.btn_3);

        mOverlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtn1.setOnHoverListener(new OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Logger.debug(TAG, "onHover btn1: " + event);
                return false;
            }
        });

        mBtn2.setOnHoverListener(new OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Logger.debug(TAG, "onHover btn2: " + event);
                return false;
            }
        });

        mBtn3.setOnHoverListener(new OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Logger.debug(TAG, "onHover btn3: " + event);
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Logger.debug(TAG, "dispatchTouchEvent: " + event);

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mBtn1.setHovered(isInView(mBtn1, event));
            mBtn2.setHovered(isInView(mBtn2, event));
            mBtn3.setHovered(isInView(mBtn3, event));
            return true;

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isInView(mBtn1, event)) {
                Logger.debug(TAG, "Up on btn1: " + event);
                mBtn1.setHovered(false);
                dismiss();
                return true;
            }
            if (isInView(mBtn2, event)) {
                Logger.debug(TAG, "Up on btn2: " + event);
                mBtn2.setHovered(false);
                dismiss();
                return true;
            }
            if (isInView(mBtn3, event)) {
                Logger.debug(TAG, "Up on btn3: " + event);
                mBtn3.setHovered(false);
                dismiss();
                return true;
            }
            dismiss();
            return false;
        }

        return super.dispatchTouchEvent(event);
    }

    public boolean isInView(View view, MotionEvent event) {
        return event.getRawX() > view.getX() &&
                event.getRawX() < view.getX() + view.getRight() &&
                event.getRawY() > view.getY() &&
                event.getRawY() < view.getY() + view.getHeight();
    }

    public void show(float x, float y) {
        if (mShowing) { return; }

        mShowing = true;
        setVisibility(View.VISIBLE);

        AnimationUtils.fadeIn(mOverlay);
        AnimationUtils.fadeIn(mOrigin);

        mOrigin.setX(x - mOrigin.getWidth() / 2f);
        mOrigin.setY(y + mOrigin.getHeight() / 2f);

        mBtn1.setX(mOrigin.getX() - 150);
        mBtn1.setY(mOrigin.getY() - 200);

        mBtn2.setX(mOrigin.getX());
        mBtn2.setY(mOrigin.getY() - 250);

        mBtn3.setX(mOrigin.getX() + 150);
        mBtn3.setY(mOrigin.getY() - 200);

        showAnimation();
    }

    public void dismiss() {
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
        hideAnimation();
        mShowing = false;
    }

    private void showAnimation() {

//        List<Animator> animList = new ArrayList<>();
//        for (int i = 0, len = mArcLayout.getChildCount(); i < len; i++) {
//            animList.add(createShowItemAnimator(mOrigin, mArcLayout.getChildAt(i)));
//        }
        List<Animator> animList = new ArrayList<>();

        animList.add(AnimationUtils.fadeInAnimator(mOverlay));
        animList.add(AnimationUtils.fadeInAnimator(mOrigin));
        animList.add(AnimationUtils.fadeInAnimator(mBtn1));
        animList.add(AnimationUtils.fadeInAnimator(mBtn2));
        animList.add(AnimationUtils.fadeInAnimator(mBtn3));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.TRANSLATION_X, mOrigin.getX(), mBtn1.getX()));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.TRANSLATION_X, mOrigin.getX(), mBtn2.getX()));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.TRANSLATION_X, mOrigin.getX(), mBtn3.getX()));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.TRANSLATION_Y, mOrigin.getY(), mBtn1.getY()));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.TRANSLATION_Y, mOrigin.getY(), mBtn2.getY()));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.TRANSLATION_Y, mOrigin.getY(), mBtn3.getY()));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.ROTATION, 360f, 0f));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.ROTATION, 360f, 0f));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.ROTATION, 360f, 0f));

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideAnimation() {

        List<Animator> animList = new ArrayList<>();
//        for (int i = mArcLayout.getChildCount() - 1; i >= 0; i--) {
//            animList.add(createHideItemAnimator(mOrigin, mArcLayout.getChildAt(i)));
//        }
        animList.add(AnimationUtils.fadeOutAnimator(mOverlay));
        animList.add(AnimationUtils.fadeOutAnimator(mOrigin));
        animList.add(AnimationUtils.fadeOutAnimator(mBtn1));
        animList.add(AnimationUtils.fadeOutAnimator(mBtn2));
        animList.add(AnimationUtils.fadeOutAnimator(mBtn3));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.TRANSLATION_X, mBtn1.getX(), mOrigin.getX()));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.TRANSLATION_X, mBtn2.getX(), mOrigin.getX()));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.TRANSLATION_X, mBtn3.getX(), mOrigin.getX()));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.TRANSLATION_Y, mBtn1.getY(), mOrigin.getY()));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.TRANSLATION_Y, mBtn2.getY(), mOrigin.getY()));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.TRANSLATION_Y, mBtn3.getY(), mOrigin.getY()));

        animList.add(ObjectAnimator.ofFloat(mBtn1, AnimationUtils.ROTATION, 360f, 0f));
        animList.add(ObjectAnimator.ofFloat(mBtn2, AnimationUtils.ROTATION, 360f, 0f));
        animList.add(ObjectAnimator.ofFloat(mBtn3, AnimationUtils.ROTATION, 360f, 0f));

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(200);
//        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.setInterpolator(new AccelerateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(View.GONE);
            }
        });
        animSet.start();

    }

    public static ObjectAnimator transXFromOrigin(final View view) {
        return ObjectAnimator.ofFloat(view, AnimationUtils.TRANSLATION_X, view.getTranslationX(), 0f);
    }

    private Animator createShowItemAnimator(View origin, View item) {

        float dx = origin.getX() - item.getX();
        float dy = origin.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimationUtils.rotation(item.getRotation(), 360f),
                AnimationUtils.translationX(item.getTranslationX(), 0f),
                AnimationUtils.translationY(item.getTranslationY(), 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(View origin, final View item) {

        float dx = origin.getX() - item.getX();
        float dy = origin.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimationUtils.rotation(360f, 0f),
                AnimationUtils.translationX(0f, dx),
                AnimationUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    public void setDismissListener(PopupWindow.OnDismissListener listener) {
        mDismissListener = listener;
    }

    public boolean isShowing() {
        return mShowing;
    }
}
