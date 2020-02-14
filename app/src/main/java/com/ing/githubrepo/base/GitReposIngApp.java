package com.ing.githubrepo.base;

import android.app.Application;

import com.ing.githubrepo.service.base.Network;


/**
 * Created by karamans on 13.02.2020.
 */

public class GitReposIngApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Network.init(this);
    }
}
