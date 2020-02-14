package com.ing.githubrepo.service.model;

import com.google.gson.annotations.SerializedName;
import com.ing.githubrepo.service.base.BaseResponseModel;

import java.util.List;


/**
 * Created by karamans on 13.02.2020.
 */

public class GithubReposResponseModel extends BaseResponseModel {

    public List<Repo> repos;

    public List<Repo> getRepos() {
        return repos;
    }

    @Override
    public String toString() {
        return repos.toString();
    }
}
