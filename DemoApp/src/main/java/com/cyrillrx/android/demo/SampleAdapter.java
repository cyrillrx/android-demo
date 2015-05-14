package com.cyrillrx.android.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created on 02/10/2014.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private Context mContext;
    private List<Sample> mSamples;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View root;
        public TextView tvTitle;
        public TextView tvSubtitle;

        public ViewHolder(View view) {
            super(view);
            root = view;
            tvTitle = (TextView) view.findViewById(android.R.id.text1);
            tvSubtitle = (TextView) view.findViewById(android.R.id.text2);
        }
    }

    public SampleAdapter(Context context, List<Sample> samples) {
        mContext = context;
        mSamples = samples;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Sample sample = mSamples.get(position);
        holder.tvTitle.setText(sample.getTitle());
        holder.tvSubtitle.setText(sample.getSubtitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext.getApplicationContext(), sample.getClazz()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSamples.size();
    }
}
