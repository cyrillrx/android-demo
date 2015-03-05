package com.cyrilleroux.android.demo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.cyrilleroux.android.demo.R;
import com.cyrilleroux.android.demo.unlock.UnlockActivity;

/**
 * @author Cyril Leroux
 *         Created 07/02/2015.
 */
public class NotificationActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button btn = (Button) findViewById(R.id.btn_notify);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, UnlockActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                13, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setContentTitle("Super titre")
                .setContentText("Super contenu")
                .setContentIntent(pendingIntent);
        mNotification.notify(1000, notifBuilder.build());
    }
}
