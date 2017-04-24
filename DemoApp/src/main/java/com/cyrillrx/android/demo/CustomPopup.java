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

    private OnDismissListener dismissListener;

    private View overlay;
    private View origin;
    private View btn1;
    private View btn2;
    private View btn3;

    private boolean showing;

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
        overlay = findViewById(R.id.overlay);
        origin = findViewById(R.id.origin);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);

        overlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(NO_ID);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            btn1.setHovered(isInView(btn1, event));
            btn2.setHovered(isInView(btn2, event));
            btn3.setHovered(isInView(btn3, event));
            return true;

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isInView(btn1, event)) {
                btn1.setHovered(false);
                dismiss(btn1.getId());
                return true;
            }
            if (isInView(btn2, event)) {
                btn2.setHovered(false);
                dismiss(btn2.getId());
                return true;
            }
            if (isInView(btn3, event)) {
                btn3.setHovered(false);
                dismiss(btn3.getId());
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

        if (showing) { return false; }

        showing = true;
        setVisibility(View.VISIBLE);

        origin.setX(x - origin.getWidth() / 2f);
        origin.setY(y - origin.getHeight() / 2f);

        AnimUtils.fadeIn(overlay);
        AnimUtils.fadeIn(origin);

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
        btn2.setX(origin.getX() + bookmarkPos.x);
        btn2.setY(origin.getY() - bookmarkPos.y);

        double diff = Math.PI / 4d;
        PointF likePos = getPolarToCartesian(angle + diff);
        btn1.setX(origin.getX() + likePos.x);
        btn1.setY(origin.getY() - likePos.y);

        PointF playPos = getPolarToCartesian(angle - diff);
        btn3.setX(origin.getX() + playPos.x);
        btn3.setY(origin.getY() - playPos.y);
    }

    protected void dismiss(@IdRes int viewId) {

        if (dismissListener != null) {
            dismissListener.onDismiss(viewId);
        }

        hideAnimation();
        showing = false;
    }

    private void showAnimation() {

        List<Animator> animList = new ArrayList<>();

        animList.add(AnimUtils.fadeInAnimator(overlay));
        animList.add(AnimUtils.fadeInAnimator(origin));

        animList.add(getShowAnimator(origin, btn1));
        animList.add(getShowAnimator(origin, btn2));
        animList.add(getShowAnimator(origin, btn3));

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideAnimation() {

        List<Animator> animList = new ArrayList<>();
        animList.add(AnimUtils.fadeOutAnimator(overlay));
        animList.add(AnimUtils.fadeOutAnimator(origin));

        animList.add(getHideAnimator(origin, btn1));
        animList.add(getHideAnimator(origin, btn2));
        animList.add(getHideAnimator(origin, btn3));

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

    public boolean isShowing() { return showing; }

    public void setResultListener(OnDismissListener listener) {
        dismissListener = listener;
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