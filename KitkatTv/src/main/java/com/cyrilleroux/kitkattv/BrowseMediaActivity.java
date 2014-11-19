package com.cyrilleroux.kitkattv;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;

/**
 * Created on 19/11/14.
 */
public class BrowseMediaActivity extends Activity {

    protected BrowseFragment mBrowseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_fragment);
    }
}
