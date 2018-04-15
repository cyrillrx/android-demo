package com.cyrillrx.raspwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cyrillrx.raspwatcher.utils.IntentKey;

/**
 * Created on 03/11/14.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String VIDEO_SAMPLE_URL = "http://techslides.com/demos/sample-videos/small.mp4";

    private EditText mEtVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mEtVideoUri = findViewById(R.id.et_video_uri);
        mEtVideoUri.setText(VIDEO_SAMPLE_URL);

        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:

                Intent intent = new Intent(getApplicationContext(), WatchActivity.class);
                intent.putExtra(IntentKey.URI, getVideoUri());
                startActivity(intent);
                break;
        }
    }

    private String getVideoUri() {
        String videoUri = mEtVideoUri.getText().toString();
        if (videoUri.isEmpty()) {
            return VIDEO_SAMPLE_URL;
        }
        return videoUri;
    }
}
