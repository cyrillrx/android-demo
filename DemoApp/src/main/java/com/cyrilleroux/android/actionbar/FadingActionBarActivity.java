package com.cyrilleroux.android.actionbar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ScrollView;

import com.cyrilleroux.android.component.scroll.ObservableScrollView;
import com.cyrilleroux.android.component.scroll.ScrollViewListener;
import com.cyrilleroux.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 22/11/2014.
 */
public class FadingActionBarActivity extends ActionBarActivity implements ScrollViewListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fading_action_bar);
        setAlpha(0);

        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        scrollView.setScrollViewListener(this);
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldX, int oldY) {
        setAlpha(Math.min(255, y));
    }

    private void setAlpha(int alpha) {
        ColorDrawable color = new ColorDrawable();
        color.setColor(getResources().getColor(R.color.nav_primary));
        color.setAlpha(alpha);
        getSupportActionBar().setBackgroundDrawable(color);
    }
}
