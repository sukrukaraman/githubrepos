package com.ing.githubrepo.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ing.githubrepo.R;
import com.ing.githubrepo.service.model.Repo;
import com.ing.githubrepo.utils.FavouriteUtil;

import java.util.List;


/**
 * Created by karamans on 13.02.2020.
 */
public class ReposRecyclerViewAdapter extends RecyclerView.Adapter<ReposRecyclerViewAdapter.ViewHolder> {

    private final OnReposItemClickListener onNewsItemClickListener;
    private List<Repo> list;

    public ReposRecyclerViewAdapter(List<Repo> repos, OnReposItemClickListener onNewsItemClickListener) {
        this.list = repos;
        this.onNewsItemClickListener = onNewsItemClickListener;
    }

    public interface OnReposItemClickListener {
        void onReposItemClick(Repo repo);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Repo repo = list.get(position);
        holder.bind(repo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNewsItemClickListener != null) {
                    onNewsItemClickListener.onReposItemClick(repo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private ImageView imageViewStar;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            textViewTitle = itemView.findViewById(R.id.repoName);
            imageViewStar = itemView.findViewById(R.id.imageViewStar);

        }

        public void bind(@NonNull Repo repo) {
            textViewTitle.setText(repo.getName());

            if (FavouriteUtil.isInFav(imageViewStar.getContext(), repo.getId())) {
                imageViewStar.setVisibility(View.VISIBLE);
                FavouriteUtil.setFavIconStatus(imageViewStar.getContext(),imageViewStar.getDrawable(),true);
            }else{
                imageViewStar.setVisibility(View.GONE);
            }

        }


    }
}
