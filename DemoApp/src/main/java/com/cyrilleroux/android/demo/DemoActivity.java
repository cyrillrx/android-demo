package com.cyrilleroux.android.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cyrilleroux.android.R;
import com.cyrilleroux.android.actionbar.FadingActionBarActivity;
import com.cyrilleroux.android.cards.CardListActivity;
import com.cyrilleroux.android.cards.HorizontalCardListActivity;
import com.cyrilleroux.android.drawer.DoubleDrawerActivity;
import com.cyrilleroux.android.drawer.SimpleDrawerActivity;
import com.cyrilleroux.android.unlock.UnlockActivity;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends ListActivity implements AdapterView.OnItemClickListener {

    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_home);

        final List<Sample> samples = new ArrayList<>();
        samples.add(new Sample("UnlockActivity", "Touch all the targets to complete the activity.", UnlockActivity.class));
        samples.add(new Sample("Simple Nav Drawer", "Simple Nav Drawer activity.", SimpleDrawerActivity.class));
        samples.add(new Sample("Double Nav Drawer", "Double Nav Drawer activity.", DoubleDrawerActivity.class));
        samples.add(new Sample("Fading Action Bar", "Fading action bar on scroll.", FadingActionBarActivity.class));
        samples.add(new Sample("Vertical Card list", "A vertical card list using RecycleView and CardView.", CardListActivity.class));
        samples.add(new Sample("Horizontal Card list", "A horizontal card list using RecycleView and CardView.", HorizontalCardListActivity.class));

        mAdapter = new SampleAdapter(getApplicationContext(), samples);

        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Sample sample = mAdapter.getItem(position);
        startActivity(new Intent(getApplicationContext(), sample.getClazz()));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(com.cyrilleroux.sampleapp.R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == com.cyrilleroux.sampleapp.R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
