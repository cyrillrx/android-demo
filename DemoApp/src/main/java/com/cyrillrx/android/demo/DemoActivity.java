package com.cyrillrx.android.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cyrillrx.android.demo.actionbar.FadingActionBarActivity;
import com.cyrillrx.android.demo.cards.CardGridActivity;
import com.cyrillrx.android.demo.cards.CardListActivity;
import com.cyrillrx.android.demo.cards.HorizontalCardGridActivity;
import com.cyrillrx.android.demo.cards.HorizontalCardListActivity;
import com.cyrillrx.android.demo.drawer.DoubleDrawerActivity;
import com.cyrillrx.android.demo.drawer.SimpleDrawerActivity;
import com.cyrillrx.android.demo.notification.NotificationActivity;
import com.cyrillrx.android.demo.unlock.UnlockActivity;
import com.cyrillrx.android.toolbox.Logger;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_home);

        Logger.initialize(getApplicationContext(), BuildConfig.DEBUG);

        final List<Sample> samples = new ArrayList<>();
        samples.add(new Sample("UnlockActivity", "Touch all the targets to complete the activity.", UnlockActivity.class));
        samples.add(new Sample("Simple Nav Drawer", "Simple Nav Drawer activity.", SimpleDrawerActivity.class));
        samples.add(new Sample("Double Nav Drawer", "Double Nav Drawer activity.", DoubleDrawerActivity.class));
        samples.add(new Sample("Fading Action Bar", "Fading action bar on scroll.", FadingActionBarActivity.class));
        samples.add(new Sample("Vertical List", "A vertical card list using RecycleView and CardView.", CardListActivity.class));
        samples.add(new Sample("Horizontal List", "A horizontal card list using RecycleView and CardView.", HorizontalCardListActivity.class));
        samples.add(new Sample("Vertical Grid", "A vertical card grid using RecycleView and CardView.", CardGridActivity.class));
        samples.add(new Sample("Horizontal Grid", "A horizontal card grid using RecycleView and CardView.", HorizontalCardGridActivity.class));
        samples.add(new Sample("Notification", "Display a simple notification.", NotificationActivity.class));

        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter(this, samples));
    }
}
