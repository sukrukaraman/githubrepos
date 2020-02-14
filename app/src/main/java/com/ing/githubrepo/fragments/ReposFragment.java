package com.ing.githubrepo.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.ing.githubrepo.R;
import com.ing.githubrepo.adapters.ReposRecyclerViewAdapter;
import com.ing.githubrepo.service.GithubReposRequest;
import com.ing.githubrepo.service.base.BaseRequest;
import com.ing.githubrepo.service.base.NetworkStack;
import com.ing.githubrepo.service.model.GithubReposResponseModel;
import com.ing.githubrepo.service.model.Repo;
import com.ing.githubrepo.utils.DialogUtil;

import java.util.List;

/**
 * Created by karamans on 13.02.2020.
 */

public class ReposFragment extends Fragment implements BaseRequest.ResponseListener<List<Repo>>,
        BaseRequest.ErrorListener, DialogInterface.OnClickListener {

    private static final String KEY_USERNAME = "username";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private ReposRecyclerViewAdapter.OnReposItemClickListener onReposItemClickListener;

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_repo, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewNews);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = view.findViewById(R.id.progressBar);

        username = getArguments().getString(KEY_USERNAME);

        requestNews(username);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onReposItemClickListener = (ReposRecyclerViewAdapter.OnReposItemClickListener) context;
        } catch (Exception e) {
            throw new RuntimeException("Activity must be implemented ReposRecyclerViewAdapter.OnReposItemClickListener");
        }
    }

    public static ReposFragment newInstance(String username) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USERNAME, username);
        ReposFragment reposFragment = new ReposFragment();
        reposFragment.setArguments(bundle);
        return reposFragment;
    }

    private void requestNews(String username) {
        progressBar.setVisibility(View.VISIBLE);
        GithubReposRequest newsRequest = new GithubReposRequest(username, this, this);
        NetworkStack.getInstance().addRequest(newsRequest);
    }

    @Override
    public void onResponse(List<Repo> githubReposResponseModel) {
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        if (githubReposResponseModel != null) {
            if(githubReposResponseModel.size() == 0){
                DialogUtil.showNoReposDialog(getActivity(), this, this);
            }else{
                ReposRecyclerViewAdapter adapter = new ReposRecyclerViewAdapter(githubReposResponseModel, onReposItemClickListener);
                mRecyclerView.setAdapter(adapter);
            }
        }else{
            DialogUtil.showResponseErrorDialog(getActivity(), this, this);
        }
    }

    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     *
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        DialogUtil.showResponseErrorDialog(getActivity(), this, this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_NEGATIVE) {
            requestNews(username);
        }else if(i == DialogInterface.BUTTON_POSITIVE){
            getActivity().finish();
        }
    }
}
