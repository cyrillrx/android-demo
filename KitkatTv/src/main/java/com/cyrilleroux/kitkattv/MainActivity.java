package com.cyrilleroux.kitkattv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created on 19/11/14.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    Button mButton1;
    Button mButton2;
    Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (Button) findViewById(android.R.id.button1);
        mButton2 = (Button) findViewById(android.R.id.button2);
        mButton3 = (Button) findViewById(android.R.id.button3);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case android.R.id.button1:
                break;

            case android.R.id.button2:
                break;

            case android.R.id.button3:
                break;
        }
    }
}
