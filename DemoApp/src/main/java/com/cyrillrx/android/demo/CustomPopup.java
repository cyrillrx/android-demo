package com.cyrillrx.android.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.cyrillrx.android.demo.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 08/06/15
 */
public class CustomPopup extends FrameLayout {

    private static final int EDGE_THRESHOLD = 300;
    private static final double RADIUS = 250d;
    private static final double DEFAULT_ANGLE = Math.PI / 2d;

    private OnDismissListener mDismissListener;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
                dismiss(NO_ID);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mBtn1.setHovered(isInView(mBtn1, event));
            mBtn2.setHovered(isInView(mBtn2, event));
            mBtn3.setHovered(isInView(mBtn3, event));
            return true;

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isInView(mBtn1, event)) {
                mBtn1.setHovered(false);
                dismiss(mBtn1.getId());
                return true;
            }
            if (isInView(mBtn2, event)) {
                mBtn2.setHovered(false);
                dismiss(mBtn2.getId());
                return true;
            }
            if (isInView(mBtn3, event)) {
                mBtn3.setHovered(false);
                dismiss(mBtn3.getId());
                return true;
            }
            dismiss(NO_ID);
            return true;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * @return true if the event occurred inside the view's bounding rect.
     */
    public boolean isInView(View view, MotionEvent event) {
        return event.getRawX() > view.getX() &&
                event.getRawX() < view.getX() + view.getRight() &&
                event.getRawY() > view.getY() &&
                event.getRawY() < view.getY() + view.getHeight();
    }

    /**
     * Displays the popup at the specified position.
     *
     * @return true if show succeeded.
     */
    public boolean show(float x, float y) {

        if (mShowing) { return false; }

        mShowing = true;
        setVisibility(View.VISIBLE);

        mOrigin.setX(x - mOrigin.getWidth() / 2f);
        mOrigin.setY(y - mOrigin.getHeight() / 2f);

        AnimUtils.fadeIn(mOverlay);
        AnimUtils.fadeIn(mOrigin);

        setItemPositions(getAngle(x, y));

        showAnimation();
        return true;
    }

    private double getAngle(float x, float y) {

        double divider = 0d;
        double leftProximity = 0d;
        double rightProximity = 0d;
        double topProximity = 0d;
        double bottomProximity = 0d;

        double proximity1 = 0d;

        if (y < EDGE_THRESHOLD) {
            topProximity = 3d * Math.PI / 2d; // 270°
            divider++;
        }

        final Point size = getSreenSize(getContext());
        if (x > size.x - EDGE_THRESHOLD) {
            rightProximity = Math.PI; // 180°
            divider++;
        }

        if (y > size.y - EDGE_THRESHOLD) {
            bottomProximity = Math.PI / 2d; // 90°
            divider++;
        }

        if (x < EDGE_THRESHOLD) {
            leftProximity = (topProximity > 0) ? 2d * Math.PI : 0; // 0° or 360°
            divider++;
        }

        if (divider == 0d) {
            return DEFAULT_ANGLE;
        }

        return (leftProximity + topProximity + rightProximity + bottomProximity) / divider;
    }

    private void setItemPositions(double angle) {

        PointF bookmarkPos = getPolarToCartesian(angle);
        mBtn2.setX(mOrigin.getX() + bookmarkPos.x);
        mBtn2.setY(mOrigin.getY() - bookmarkPos.y);

        double diff = Math.PI / 4d;
        PointF likePos = getPolarToCartesian(angle + diff);
        mBtn1.setX(mOrigin.getX() + likePos.x);
        mBtn1.setY(mOrigin.getY() - likePos.y);

        PointF playPos = getPolarToCartesian(angle - diff);
        mBtn3.setX(mOrigin.getX() + playPos.x);
        mBtn3.setY(mOrigin.getY() - playPos.y);
    }

    protected void dismiss(@IdRes int viewId) {

        if (mDismissListener != null) {
            mDismissListener.onDismiss(viewId);
        }

        hideAnimation();
        mShowing = false;
    }

    private void showAnimation() {

        List<Animator> animList = new ArrayList<>();

        animList.add(AnimUtils.fadeInAnimator(mOverlay));
        animList.add(AnimUtils.fadeInAnimator(mOrigin));

        animList.add(getShowAnimator(mOrigin, mBtn1));
        animList.add(getShowAnimator(mOrigin, mBtn2));
        animList.add(getShowAnimator(mOrigin, mBtn3));

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideAnimation() {

        List<Animator> animList = new ArrayList<>();
        animList.add(AnimUtils.fadeOutAnimator(mOverlay));
        animList.add(AnimUtils.fadeOutAnimator(mOrigin));

        animList.add(getHideAnimator(mOrigin, mBtn1));
        animList.add(getHideAnimator(mOrigin, mBtn2));
        animList.add(getHideAnimator(mOrigin, mBtn3));

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(200);
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
        return ObjectAnimator.ofFloat(view, AnimUtils.TRANSLATION_X, view.getTranslationX(), 0f);
    }

    private Animator getShowAnimator(View origin, View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimUtils.alpha(item.getAlpha(), 1f),
                AnimUtils.rotation(item.getRotation(), 720f),
                AnimUtils.translationX(origin.getX(), item.getX()),
                AnimUtils.translationY(origin.getY(), item.getY())
        );

        return anim;
    }

    private Animator getHideAnimator(View origin, final View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimUtils.alpha(item.getAlpha(), 0f),
                AnimUtils.rotation(item.getRotation(), -720f),
                AnimUtils.translationX(item.getX(), origin.getX()),
                AnimUtils.translationY(item.getY(), origin.getY())
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(View.GONE);
            }
        });

        return anim;
    }

    public boolean isShowing() { return mShowing; }

    public void setResultListener(OnDismissListener listener) {
        mDismissListener = listener;
    }

    /**
     * Listener that is called when this popup window returns a result.
     */
    public interface OnDismissListener {

        /** Called when this popup window is dismissed. */
        void onDismiss(@IdRes int viewId);
    }

    // TODO move in a util class
    private static PointF getPolarToCartesian(double angleInRadians) {
        return new PointF(
                (float) (Math.cos(angleInRadians) * RADIUS),
                (float) (Math.sin(angleInRadians) * RADIUS)
        );
    }

    // TODO move in a util class
    private static Point getSreenSize(Context context) {
        final Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size;
    }
}