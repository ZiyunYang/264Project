package com.example.irvinetaste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class nearbyAdapter extends RecyclerView.Adapter<nearbyAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie mv = mData.get(position);
        holder.tvTitle.setText(mv.getTitle());
        holder.tvReleaseDate.setText(mv.getReleaseDate());
        holder.tvVote.setText(String.valueOf(mv.getVoteAverage()));
        holder.tvOverview.setText(mv.getOverview());
        String path = "https://image.tmdb.org/t/p/w500/" + mv.getPosterPath();
        Picasso.get().load(path).into(holder.imageView);
    }

    @Override
    public int getItemCount() { return this.mData.size(); }

}
