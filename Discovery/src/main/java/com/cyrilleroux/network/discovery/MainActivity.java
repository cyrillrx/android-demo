package com.cyrilleroux.network.discovery;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author Cyril Leroux
 *         Created 09/11/2014.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static String PORT = "9090";
    private TextView mTvIpAddress;
    private TextView mTvSubnet;

    private String mSubnet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTvIpAddress = (TextView) findViewById(android.R.id.text1);
        mTvSubnet = (TextView) findViewById(android.R.id.text2);
        findViewById(android.R.id.button1).setOnClickListener(this);

        populateNetworkInfo();
    }

    private void populateNetworkInfo() {

        final WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        final int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        final String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));

        mTvIpAddress.setText(ip);

        mSubnet = String.format("%d.%d.%d.",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff));

        mTvSubnet.setText(mSubnet);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case android.R.id.button1:
                DeviceScanner.discover(mSubnet, PORT);
                break;
        }
    }
}
