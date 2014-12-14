package com.cyrilleroux.android.cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyrilleroux.android.R;

/**
 * @author Cyril Leroux
 *         Created 14/12/2014.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private String[] mDataSet;
    private boolean mVertical;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(String[] dataSet, boolean vertical) {
        mDataSet = dataSet;
        mVertical = vertical;
    }

    public CardAdapter(String[] dataSet) {
        this(dataSet, true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int res = mVertical ? R.layout.card_item_v : R.layout.card_item_h;
        View v = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        //TODO Set the view's size, margins, paddings and layout parameters here
        return new ViewHolder((CardView) v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView) viewHolder.mCardView.findViewById(R.id.info_text)).setText(mDataSet[position]);

    }

    @Override
    public int getItemCount() { return mDataSet.length; }
}
