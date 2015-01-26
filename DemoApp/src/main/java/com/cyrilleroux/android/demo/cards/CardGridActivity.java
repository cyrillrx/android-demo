package com.cyrilleroux.android.demo.cards;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cyrilleroux.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 11/12/2014.
 */
public class CardGridActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private String[] mDataSet = new String[]{
            "TOTO", "TATA", "TUTU",
            "TOTO", "TATA", "TUTU",
            "TOTO", "TATA", "TUTU",
            "TOTO", "TATA", "TUTU",
            "TOTO", "TATA", "TUTU",
            "TOTO", "TATA", "TUTU"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_v);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Use a grid manager
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new CardAdapter(mDataSet, CardAdapter.ScrollType.GRID);
        mRecyclerView.setAdapter(mAdapter);
    }
}
