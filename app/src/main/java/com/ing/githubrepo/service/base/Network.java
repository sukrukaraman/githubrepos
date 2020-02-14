package com.ing.githubrepo.service.base;

import android.content.Context;

/**
 * Created by karamans on 13.02.2020.
 */

public final class Network {

    private static final int DEFAULT_TIMEOUT_SECONDS = 30; // seconds
    private static final String TAG = "Network";
    private static final String URL_FILE = "url.properties";
    private static final String ACTIONS = "actions.properties";
    private static final int MAX_RETRY_COUNT = 3;
    private static final int MILLISECONDS = 1000;
    private static final int DEFAULT_TIMEOUT_MS = DEFAULT_TIMEOUT_SECONDS * MILLISECONDS; // 30 Seconds

    private static Network instance = null;
    private static Object mutex = new Object();

    private Network() {
    }

    /**
     * Initialize Network Layer
     */
    public static void init(Context context) {
        NetworkStack.Builder builder = new NetworkStack.Builder(context);
        builder.setUrlKey(EndpointType.PROD.getType().toLowerCase());
        builder.setMaxRetryCount(MAX_RETRY_COUNT);
        builder.setTimeOutInMillis(DEFAULT_TIMEOUT_MS);
        builder.setActionsFrom(ACTIONS);
        builder.setUrlsFrom(URL_FILE);
        builder.setImageCache(new ImageCache());

        NetworkStack.init(builder);
    }

    /**
     * Get singleton Network class instance
     *
     * @return {@link NetworkStack}
     */

    public static Network getInstance(Context context) {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new Network();
                }
            }
        }

        return instance;
    }


}

