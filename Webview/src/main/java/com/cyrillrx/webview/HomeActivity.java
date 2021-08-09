package com.cyrillrx.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cyrillrx.webview.utils.IntentKey;

/**
 * Created on 03/11/14.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mEtVideoUri = findViewById(R.id.et_video_uri);
        mEtVideoUri.setText("http://www.google.com");

        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra(IntentKey.URL, getVideoUri());
                startActivity(intent);
                break;
        }
    }

    private String getVideoUri() {
        String videoUri = mEtVideoUri.getText().toString();
        if (videoUri.isEmpty()) {
            return "http://www.google.com";
        }
        return videoUri;
    }
}
