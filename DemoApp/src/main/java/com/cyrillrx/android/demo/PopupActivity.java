package com.cyrillrx.android.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Cyril Leroux
 *         Created on 05/06/2015.
 */
public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup);

        View view = findViewById(R.id.popup);
        if (!view.requestFocus()) {
            Toast.makeText(getApplicationContext(), "requestFocus failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "requestFocus succeed", Toast.LENGTH_SHORT).show();
        }

        if (!view.requestFocusFromTouch()) {
            Toast.makeText(getApplicationContext(), "requestFocusFromTouch failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "requestFocusFromTouch succeed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }
}
