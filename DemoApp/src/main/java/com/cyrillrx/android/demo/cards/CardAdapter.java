package com.cyrillrx.android.demo.cards;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cyrillrx.android.demo.CustomPopup;
import com.cyrillrx.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 14/12/2014.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public enum ScrollType {HORIZONTAL, VERTICAL, GRID}

    private String[] mDataSet;
    private ScrollType mScrollType;
    private CustomPopup mCustomPopup;

    private float mTouchX;
    private float mTouchY;

    private View.OnTouchListener mListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mTouchX = event.getRawX();
            mTouchY = event.getRawY();
            return false;
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public CardAdapter(String[] dataSet, ScrollType scrollType) {
        mDataSet = dataSet;
        mScrollType = scrollType;
    }

    public CardAdapter(String[] dataSet) {
        this(dataSet, ScrollType.VERTICAL);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemRes(), parent, false);
        //TODO Set the view's size, margins, paddings and layout parameters here
        return new ViewHolder((CardView) v);
    }

    private int getItemRes() {
        switch (mScrollType) {

            case HORIZONTAL:
                return R.layout.card_item_h;

            case VERTICAL:
                return R.layout.card_item_v;

            // Grid
            default:
                return R.layout.card_item;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView) viewHolder.mCardView.findViewById(R.id.info_text)).setText(mDataSet[position]);
        viewHolder.itemView.setOnTouchListener(mListener);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                final int x = v.getLeft() + v.getWidth() / 2;
                final int y = v.getTop() + v.getHeight() / 2;
                mCustomPopup = showPopup(v, x, y);
                mCustomPopup.setDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                });
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            return false;
                        }
                        if (mCustomPopup.isShown()
                                && (event.getAction() == MotionEvent.ACTION_UP
                                || event.getAction() == MotionEvent.ACTION_MOVE)) {
                            mCustomPopup.dispatchTouchEvent(event);
                            return true;
                        }
                        return false;
                    }
                });

                return false;
            }
        });
    }

    @Override
    public int getItemCount() { return mDataSet.length; }

    // The method that displays the popup.
    public CustomPopup showPopup(View parent, float posX, float posY) {

        Activity activity = (Activity) parent.getContext();
        // Creating the PopupWindow
        if (mCustomPopup == null) {
            mCustomPopup = new CustomPopup(activity);
            activity.getWindowManager()
                    .addView(mCustomPopup,
                            new WindowManager.LayoutParams(
                                    WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                                    WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                                    PixelFormat.TRANSPARENT

                            ));
        }

        // Displaying the popup at the specified location, + offsets.
        mCustomPopup.show(posX, posY);
        return mCustomPopup;
    }

    public void hidePopup() {
        if (mCustomPopup != null) {
            mCustomPopup.dismiss();
        }
    }
}
