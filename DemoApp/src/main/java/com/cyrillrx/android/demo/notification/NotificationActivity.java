package com.cyrillrx.android.demo.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cyrillrx.android.demo.DemoActivity;
import com.cyrillrx.android.demo.R;

/**
 * @author Cyril Leroux
 *         Created 07/02/2015.
 */
public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 13;

    private static final int SIMPLE_NOTIFICATION_ID = 1;
    private static final int INBOX_NOTIFICATION_ID = 2;
    private static final int BLINKING_NOTIFICATION_ID = 3;

    private static final int TRIGGER_DELAY_MS = 5000;
    private static final int LIGHT_ON_MS = 1000;
    private static final int LIGHT_OFF_MS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.btn_simple_notification).setOnClickListener(this);
        findViewById(R.id.btn_inbox_notification).setOnClickListener(this);
        findViewById(R.id.btn_blinking_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_simple_notification:
                simpleNotification();
                break;

            case R.id.btn_inbox_notification:
                inboxNotification();
                break;

            case R.id.btn_blinking_notification:
                Toast.makeText(getApplicationContext(), "Notification triggers in 5 seconds", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blinkingNotification();
                    }
                }, TRIGGER_DELAY_MS);
                break;
        }
    }

    private void simpleNotification() {

        final Intent intent = new Intent(this, DemoActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setContentTitle(getString(R.string.simple_notification_title))
                .setContentText(getString(R.string.simple_notification_text))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        final NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(SIMPLE_NOTIFICATION_ID, builder.build());
    }

    private void inboxNotification() {

        final Intent intent = new Intent(this, DemoActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);

        final NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        inboxStyle.addLine("Dummy data line 1");
        inboxStyle.addLine("Dummy data line 2");
        inboxStyle.addLine("Dummy data line 3");
        inboxStyle.addLine("Dummy data line 4");

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.inbox_notification_title))
                .setContentText(getString(R.string.inbox_notification_text))
                .setStyle(inboxStyle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        final NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(INBOX_NOTIFICATION_ID, builder.build());
    }

    private void blinkingNotification() {

        final Intent intent = new Intent(this, DemoActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setContentTitle(getString(R.string.blinking_notification_title))
                .setContentText(getString(R.string.blinking_notification_text))
                .setLights(Color.YELLOW, LIGHT_ON_MS, LIGHT_OFF_MS)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        final NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(BLINKING_NOTIFICATION_ID, builder.build());
    }

}
