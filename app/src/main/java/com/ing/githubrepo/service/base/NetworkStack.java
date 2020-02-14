package com.ing.githubrepo.service.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by karamans on 13.02.2020.
 */

public final class NetworkStack {

    private static final String DEFAULT_CACHE_DIR = "volley";

    private static NetworkStack instance;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private float backOffMultiplier;
    private int maxRetryCount;
    private int timeOutInMillis;
    private String urlKey;
    private Map<String, String> actionEndpoints;
    private Map<String, String> urls;

    public static NetworkStack getInstance() {
        if (instance == null) {
            throw new IllegalStateException("-->NetworkStack not initialized!");
        } else {
            return instance;
        }
    }

    /**
     * Construction
     *
     * @param builder {@link Builder} initialize needed fields
     */
    private NetworkStack(NetworkStack.Builder builder) {
        this.urls = UrlUtil.readUrlProperties(builder.context, builder.urlsPath);
        this.actionEndpoints = UrlUtil.readActionsProperties(builder.context, builder.actionsPaths);
        this.urlKey = builder.urlKey;
        this.timeOutInMillis = builder.timeOutInMillis;
        this.maxRetryCount = builder.maxRetryCount;
        this.backOffMultiplier = builder.backOffMultiplier;
        this.requestQueue = newRequestQueue(builder);
        this.imageLoader = new ImageLoader(this.requestQueue, builder.imageCache);
    }

    /**
     * Create {@link NetworkStack} instance
     *
     * @param builder {@link Builder}
     */
    public static void init(NetworkStack.Builder builder) {
        instance = new NetworkStack(builder);
    }

    /**
     * Getting url field provided by asset file
     *
     * @return i.e url="http" return "http"
     */
    public String getUrl() {
        return this.urls.get(this.urlKey);
    }

    public RequestQueue getRequestQueue() {
        return this.requestQueue;
    }

    /**
     * @return {@link ImageLoader}
     */
    public ImageLoader getImageLoader() {
        if (null == this.imageLoader) {
            throw new IllegalStateException("=>Image Loader Not Initialized. Make sure that you set an image loader while initializing NetworkStack");
        } else {
            return this.imageLoader;
        }
    }

    /**
     * Add new request
     *
     * @param request {@link BaseRequest}
     */
    public void addRequest(BaseRequest request) {
        if (this.requestQueue == null) {
            throw new RuntimeException("=>Make sure VolleyStack is initialized");
        } else {
            this.requestQueue.add(request);
        }
    }

    /**
     * Cancel request by tag
     *
     * @param tag request tag when add new request by {@link com.android.volley.Request#setTag(Object)}
     */
    public void cancelRequestsWithTag(Object tag) {
        if (this.requestQueue == null) {
            throw new RuntimeException("=>Make sure VolleyStack is initialized");
        } else {
            this.requestQueue.cancelAll(tag);
        }
    }

    /**
     * Create new request queue
     *
     * @param builder {@link Builder}
     * @return {@link RequestQueue}
     */
    private static RequestQueue newRequestQueue(NetworkStack.Builder builder) {
        File cacheDir = new File(builder.context.getCacheDir(), DEFAULT_CACHE_DIR);
        OkHttpClient client = builder.okHttpClient == null ? new OkHttpClient() : builder.okHttpClient;
        OkHttpStack stack = new OkHttpStack(client);
        BasicNetwork network = new BasicNetwork(stack);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, builder.diskCacheSize), network);
        queue.start();
        return queue;
    }

    public Map<String, String> getActions() {
        if (null == this.actionEndpoints) {
            throw new IllegalStateException("=>Action Endpoints Not initialized. Make sure that you specified an action endpoints url path while initializing NetworkStack");
        } else {
            return this.actionEndpoints;
        }
    }

    public String getAction(String actionName) {
        if (null == this.actionEndpoints) {
            throw new IllegalStateException("=>Action Endpoints Not initialized. Make sure that you specified an action endpoints url path while initializing SNetworkStack");
        } else {
            return this.actionEndpoints.get(actionName);
        }
    }

    public String getUrlKey() {
        return this.urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public int getTimeOutInMillis() {
        return this.timeOutInMillis;
    }

    public int getMaxRetryCount() {
        return this.maxRetryCount;
    }

    public float getBackOffMultiplier() {
        return this.backOffMultiplier;
    }

    /**
     * Builder class for initial {@link NetworkStack}
     */
    public static class Builder {
        private Context context;
        private String urlKey = "url";
        private int diskCacheSize = 5242880;
        private int timeOutInMillis = 30000;
        private int maxRetryCount = 1;
        private float backOffMultiplier = 1.0F;
        private String urlsPath;
        private String[] actionsPaths;
        private ImageLoader.ImageCache imageCache;
        private OkHttpClient okHttpClient;
        private NetworkConverter converter;

        public Builder(Context context) {
            this.context = context;
        }

        public NetworkStack.Builder setImageCache(ImageLoader.ImageCache imageCache) {
            this.imageCache = imageCache;
            return this;
        }

        public NetworkStack.Builder setUrlKey(String urlKey) {
            this.urlKey = urlKey;
            return this;
        }

        public NetworkStack.Builder setDiskCacheSize(int diskCacheSize) {
            this.diskCacheSize = diskCacheSize;
            return this;
        }

        public NetworkStack.Builder setUrlsFrom(String propertyFileName) {
            this.urlsPath = propertyFileName;
            return this;
        }

        public NetworkStack.Builder setActionsFrom(String... actionEndpointsReadPaths) {
            this.actionsPaths = actionEndpointsReadPaths;
            return this;
        }


        public NetworkStack.Builder setTimeOutInMillis(int timeOutInMillis) {
            this.timeOutInMillis = timeOutInMillis;
            return this;
        }

        public NetworkStack.Builder setClient(OkHttpClient client) {
            this.okHttpClient = client;
            return this;
        }

        public NetworkStack.Builder setMaxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
            return this;
        }

        public NetworkStack.Builder setConverter(NetworkConverter converter) {
            this.converter = converter;
            return this;
        }

        public NetworkStack build() {
            return new NetworkStack(this);
        }
    }

}
