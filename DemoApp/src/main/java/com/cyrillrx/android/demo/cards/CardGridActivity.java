package com.cyrillrx.android.demo.cards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.cyrillrx.android.demo.PopLayout;
import com.cyrillrx.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 11/12/2014.
 */
public class CardGridActivity extends AppCompatActivity {

    private ViewStub mPopupStub;
    private View mPopup;
    private PopLayout mPopupLayout;
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
        setContentView(R.layout.activity_card_list_popup);

        mPopupStub = (ViewStub) findViewById(R.id.stub_popup);

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

    public PopLayout showPopup(float x, float y) {
        if (mPopup == null) {
            mPopup = mPopupStub.inflate();
            mPopupLayout = (PopLayout) mPopup.findViewById(R.id.popup);
        }

        mPopup.getParent().requestDisallowInterceptTouchEvent(false);
        mPopup.setVisibility(View.VISIBLE);

        String message = (mPopup.requestFocus()) ? "focus granted to popup" : "Fail...";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        return mPopupLayout;
    }

    public void hidePopup() {
        if (mPopup == null) {
            mPopup = mPopupStub.inflate();
            mPopupLayout = (PopLayout) mPopup.findViewById(R.id.popup);
        }
        mPopup.setVisibility(View.GONE);
    }
}
