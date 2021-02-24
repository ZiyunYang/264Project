package com.example.irvinetaste;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

//public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
//    private List<Restaurant> restaurants;
//    RestaurantListAdapter(List<Restaurant> restaurants) {
//        this.restaurants = restaurants;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView tvTitle;
//        TextView tvReleaseDate;
//        TextView tvVote;
//        TextView tvOverview;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.ivMovie);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
//            tvVote = itemView.findViewById(R.id.tvVote);
//            tvOverview = itemView.findViewById(R.id.tvOverview);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Movie movie = movies.get(position);
//        holder.tvTitle.setText(movie.getTitle());
//        holder.tvReleaseDate.setText(movie.getReleaseDate());
//        holder.tvVote.setText(movie.getVoteAverage()+"");
//        holder.tvOverview.setText(movie.getOverview());
//        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(holder.imageView);
//    }
//
//    @Override
//    public int getItemCount() {
//        return restaurants.size();
//    }
//
//}
