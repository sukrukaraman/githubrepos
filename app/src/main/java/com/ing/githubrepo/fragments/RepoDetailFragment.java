package com.ing.githubrepo.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.NetworkImageView;
import com.ing.githubrepo.R;
import com.ing.githubrepo.service.base.NetworkStack;
import com.ing.githubrepo.service.model.Repo;
import com.ing.githubrepo.utils.FavouriteUtil;

/**
 * Created by karamans on 13.02.2020.
 */

public class RepoDetailFragment extends Fragment {

    public static final String ARG_REPO = "repo";

    private TextView textViewOwner;
    private TextView textViewStars;
    private TextView textViewOpenIssues;
    private TextView textViewDescription;
    private NetworkImageView imageViewAvatar;
    private Repo repo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_repo_detail, container, false);
        initViews(view);

        repo = getArguments().getParcelable(ARG_REPO);
        bind(repo);

        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_star, menu);
        if (repo != null && FavouriteUtil.isInFav(getActivity(), repo.getId())) {
            MenuItem menuItem = menu.findItem(R.id.favourite_btn);
            Drawable newIcon = menuItem.getIcon();
            FavouriteUtil.setFavIconStatus(getActivity(), newIcon, true);
            menuItem.setIcon(newIcon);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.favourite_btn) {
            boolean remove = false;
            if (FavouriteUtil.isInFav(getActivity(), repo.getId())) {
                remove = true;
            }

            FavouriteUtil.setFavIconStatus(getActivity(), item.getIcon(), !remove);
            FavouriteUtil.setFav(getActivity(), repo.getId(), remove);

        }

        return super.onOptionsItemSelected(item);
    }


    public static RepoDetailFragment newInstance(Repo repo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_REPO, repo);
        RepoDetailFragment newsFragment = new RepoDetailFragment();
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    private void initViews(View view) {
        textViewOwner = view.findViewById(R.id.ownerTitle);
        textViewStars = view.findViewById(R.id.stars);
        textViewOpenIssues = view.findViewById(R.id.openIssues);
        textViewDescription = view.findViewById(R.id.description);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
    }


    public void bind(@NonNull Repo repo) {
        if (repo.getOwner() != null) {
            textViewOwner.setText(repo.getOwner().getLogin());
            imageViewAvatar.setImageUrl(repo.getOwner().getAvatar_url(), NetworkStack.getInstance().getImageLoader());
        }

        textViewStars.setText(getString(R.string.stars, repo.getStarCount()));
        textViewDescription.setText(repo.getDescription());
        textViewOpenIssues.setText(getString(R.string.open_issues_count, repo.getOpenIssuesCount()));

    }
}
