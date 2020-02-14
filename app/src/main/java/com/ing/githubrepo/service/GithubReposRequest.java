package com.ing.githubrepo.service;

import com.google.gson.reflect.TypeToken;
import com.ing.githubrepo.service.base.BaseRequest;
import com.ing.githubrepo.service.base.BaseRequestModel;
import com.ing.githubrepo.service.base.NetworkStack;
import com.ing.githubrepo.service.model.Repo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by karamans on 13.02.2020.
 */

public class GithubReposRequest extends BaseRequest<BaseRequestModel, List<Repo>> {

    private static final String ACTION_KEY = "repos";
    private String username;

    public GithubReposRequest(String username, ResponseListener<List<Repo>> responseListener, BaseRequest.ErrorListener errorListener) {
        super(responseListener, errorListener);
        this.username = username;
    }


    @Override
    public Type getResponseModelClassType() {
        return new TypeToken<List<Repo>>() {
        }.getType();
    }

    @Override
    public String getActionUrl() {
        return String.format(NetworkStack.getInstance().getAction(ACTION_KEY), username);
    }
}
