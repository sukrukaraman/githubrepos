package com.ing.githubrepo.service.base;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by karamans on 13.02.2020.
 */

public final class UrlUtil {
    /**
     * Construction
     */
    private UrlUtil() {

    }

    /**
     * @param context          {@link Context}
     * @param propertyFileName
     * @return {@link Properties} from assets folder
     */
    private static Properties fetch(Context context, String propertyFileName) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(propertyFileName);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param context  {@link Context}
     * @param urlsPath from assets folder
     * @return {@link HashMap} containing urls
     */

    public static Map<String, String> readUrlProperties(Context context, String urlsPath) {
        if (urlsPath == null) {
            throw new IllegalArgumentException("-->Url properties not found! Please include a property file under assets folder");
        } else {
            Properties properties = fetch(context, urlsPath);
            HashMap urls = new HashMap();
            Iterator i = properties.stringPropertyNames().iterator();
            while (i.hasNext()) {
                String urlKey = (String) i.next();
                urls.put(urlKey, properties.getProperty(urlKey));
            }

            return urls;
        }


    }

    /**
     * Reading endpoint actions from assets
     *
     * @param context      {@link Context}
     * @param actionsPaths
     * @return {@link Map < String , String >}
     */
    public static Map<String, String> readActionsProperties(Context context, String[] actionsPaths) {
        if (null == actionsPaths) {
            return null;
        } else {
            HashMap actionEndpoints = new HashMap();
            String[] actionsPaths1 = actionsPaths;
            int len = actionsPaths.length;

            for (int i = 0; i < len; ++i) {
                String actionPath = actionsPaths1[i];
                Properties propertiesFile = fetch(context, actionPath);
                Iterator iterator = propertiesFile.stringPropertyNames().iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    actionEndpoints.put(key, propertiesFile.getProperty(key));
                }
            }

            return actionEndpoints;
        }
    }

}
