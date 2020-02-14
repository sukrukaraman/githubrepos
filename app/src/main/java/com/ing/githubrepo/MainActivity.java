package com.ing.githubrepo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ing.githubrepo.adapters.ReposRecyclerViewAdapter;
import com.ing.githubrepo.base.BaseActivity;
import com.ing.githubrepo.fragments.RepoDetailFragment;
import com.ing.githubrepo.fragments.ReposFragment;
import com.ing.githubrepo.service.model.Repo;


public class MainActivity extends BaseActivity implements ReposRecyclerViewAdapter.OnReposItemClickListener {

    private static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceReposFragment(getIntent().getStringExtra(USERNAME));
    }

    private void replaceReposFragment(String username) {
        ReposFragment reposFragment = ReposFragment.newInstance(username);
        replaceFragment(reposFragment);
    }

    private void replaceRepoDetailFragment(Repo repo) {
        RepoDetailFragment repoDetailFragment = RepoDetailFragment.newInstance(repo);
        replaceFragment(repoDetailFragment);

    }

    @Override
    public void onReposItemClick(Repo repo) {
        replaceRepoDetailFragment(repo);
    }

    public static Intent newIntent(String userName, Context context){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(USERNAME,userName);
        return  intent;
    }
}
