package com.example.irvinetaste;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListAdapter1 extends RecyclerView.Adapter<RestaurantListAdapter1.ViewHolder>{

    private List<Restaurant> mData;
    RestaurantListAdapter1(List<Restaurant> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Restaurant restaurant = mData.get(position);

        // todo change text
        holder.name.setText(restaurant.getTitle());
        holder.rating.setText(restaurant.getReleaseDate());
        holder.price.setText(String.valueOf(restaurant.getVoteAverage()));
        holder.tvOverview.setText(restaurant.getOverview());

        // todo load view
        Picasso.get().load(restaurant.getPosterPath()).into(holder.restaurantImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // todo 存什么
        TextView name;
        TextView rating;
        TextView price;
        TextView displayAddress;
        TextView delivery;
        TextView takeout;
        TextView distance;
        ImageView restaurantImg;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvTitle);
            rating = itemView.findViewById(R.id.tvReleaseDate);
            price = itemView.findViewById(R.id.tvVote);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            restaurantImg = itemView.findViewById(R.id.ivMovie);
        }
    }

    // https://stackoverflow.com/questions/26635841/recyclerview-change-data-set
    public void updateList(List<Restaurant> data){
        this.mData = data;
        // rebind and relayout views
        notifyDataSetChanged();
    }

}
