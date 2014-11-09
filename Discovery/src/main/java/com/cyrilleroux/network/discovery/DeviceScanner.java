package com.cyrilleroux.network.discovery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created 09/11/2014.
 */
public class DeviceScanner {

    /**
     * Scans network for devices.
     *
     * @return The list of fond devices.
     */
    public static List<String> discover(String subnet, String port) {

        List<String> foundDevices = new ArrayList<>();

        for (int i = 1; i < 256; i++) {
            String host = subnet + i;
            if (sendPing(host, port)) {
                foundDevices.add(host);
            }
        }

        return foundDevices;
    }

    private static boolean sendPing(String host, String port) {
        return false;
    }
}
