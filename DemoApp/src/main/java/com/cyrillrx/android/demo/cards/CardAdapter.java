package com.cyrillrx.android.demo.cards;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.IdRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cyrillrx.android.demo.CustomPopup;
import com.cyrillrx.android.demo.R;
import com.cyrillrx.android.toolbox.Logger;

/**
 * @author Cyril Leroux
 *         Created 14/12/2014.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public enum ScrollType {HORIZONTAL, VERTICAL, GRID}

    private String[] dataSet;
    private ScrollType scrollType;

    private CustomPopup customPopup;
    private float touchX;
    private float touchY;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Logger.debug("ContentViewHolder", "onTouch: " + event);
                touchX = event.getRawX();
                touchY = event.getRawY();
            }
            return false;
        }
    };

    private View.OnTouchListener dispatchTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            final int action = event.getAction();
            switch (action) {

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_MOVE:
                    return customPopup.isShowing() && customPopup.dispatchTouchEvent(event);

                default:
                    return false;
            }
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public CardAdapter(String[] dataSet, ScrollType scrollType) {
        this.dataSet = dataSet;
        this.scrollType = scrollType;
    }

    public CardAdapter(String[] dataSet) { this(dataSet, ScrollType.VERTICAL); }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getItemRes(), parent, false);
        //TODO Set the view's size, margins, paddings and layout parameters here
        return new ViewHolder((CardView) v);
    }

    private int getItemRes() {
        switch (scrollType) {

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
        ((TextView) viewHolder.cardView.findViewById(R.id.info_text)).setText(dataSet[position]);
        viewHolder.itemView.setOnTouchListener(touchListener);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return CardAdapter.this.onLongClick(v);
            }
        });
    }

    @Override
    public int getItemCount() { return dataSet.length; }

    protected boolean onLongClick(final View v) {

        final Context applicationContext = v.getContext().getApplicationContext();

        customPopup = getPopup(v.getContext());
        // Displaying the popup at the specified location, + offsets.
        if (!customPopup.show(touchX, touchY)) {
            return false;
        }

        // After a long click, dispatch down and move actions to the popup
        v.setOnTouchListener(dispatchTouchListener);

        // Prevent parent and its ancestors to intercept touch events
        v.getParent().requestDisallowInterceptTouchEvent(true);

        customPopup.setResultListener(new CustomPopup.OnDismissListener() {

            @Override
            public void onDismiss(@IdRes int viewId) {
                resetTouch();

                switch (viewId) {

                    case R.id.btn_1:
                        Toast.makeText(applicationContext, "Btn1", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.btn_2:
                        Toast.makeText(applicationContext, "Btn2", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.btn_3:
                        Toast.makeText(applicationContext, "Btn3", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        // Dismissed
                }
            }

            private void resetTouch() {
                // Reset touch Listener
                v.setOnTouchListener(touchListener);
                // Allow parent and its ancestors to intercept touch events
                v.getParent().requestDisallowInterceptTouchEvent(false);

            }
        });

        return true;
    }

    /**
     * Create the popup and add it to the WindowManager.
     * If the popup already exists, it's not recreated.
     *
     * @return The Contextual popup.
     */
    private CustomPopup getPopup(Context context) {

        if (customPopup == null) {
            // Creating and add the view to the Windows manager
            customPopup = new CustomPopup(context);
            ((Activity) context).getWindowManager()
                    .addView(customPopup,
                            new WindowManager.LayoutParams(
                                    WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                                    WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                                    PixelFormat.TRANSPARENT
                            ));
        }
        return customPopup;
    }
}
