package com.ing.githubrepo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ing.githubrepo.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by karamans on 13.02.2020.
 */

/**
 * Utility class that handles favourite repos
 */
import static android.content.Context.MODE_PRIVATE;

public final class FavouriteUtil {

    private static final String PREF_SHARED = "ing_shared";
    private static final String KEY_FAV_LIST = "key_fav_list";


    private FavouriteUtil(){
        //
    }


    private static String getFavListJson(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_SHARED, MODE_PRIVATE);
        return prefs.getString(KEY_FAV_LIST, null);
    }

    /**
     * Add or remove fav repo to sharedPrefs
     *
     * @param context {@link Context} getSharedPrefs
     * @param id {@link String} repo id
     * @param remove repo will be added fav or not
     */
    public static void setFav(Context context, String id, boolean remove) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_SHARED, MODE_PRIVATE).edit();


        String jsonExistingFavList = getFavListJson(context);
        List<String> favList;
        Gson gson = new Gson();

        if (jsonExistingFavList != null) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            favList = gson.fromJson(jsonExistingFavList, type);
        } else {
            favList = new ArrayList<>();
        }

        if (remove && isInFav(context, id)) {
            favList.remove(id);
        } else if (!remove && !isInFav(context, id)) {
            favList.add(id);
        }

        jsonExistingFavList = gson.toJson(favList);

        editor.putString(KEY_FAV_LIST, jsonExistingFavList);
        editor.commit();
    }

    /**
     * Checks if repo is in fav or not
     * @param context {@link Context} getSharedPrefs
     * @param id id {@link String} repo id
     * @return {@link Boolean} if is in fav or not
     */
    public static boolean isInFav(Context context, String id) {
        String jsonExistingFavList = getFavListJson(context);
        List<String> favList;
        Gson gson = new Gson();

        if (jsonExistingFavList != null) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            favList = gson.fromJson(jsonExistingFavList, type);
            return favList.contains(id);
        }

        return false;
    }


    /**
     * Sets fav icon status
     * @param context id {@link Context} to get resources
     * @param icon  {@link Drawable} fav icon drawable
     * @param active id {@link Boolean} active or passive
     */
    public static void setFavIconStatus(Context context, Drawable icon, boolean active) {
        if (active) {
            icon.mutate().setColorFilter(context.getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
        } else {
            icon.mutate().setColorFilter(context.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }
}
