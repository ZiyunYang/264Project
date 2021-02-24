package com.example.irvinetaste;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private List<Restaurant> restaurants;
    RestaurantListAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView rating;
        TextView price;
        ImageView restaurantImg;

        ViewHolder(View itemView) {
            super(itemView);
            restaurantImg = itemView.findViewById(R.id.rest_pic);
            price = itemView.findViewById(R.id.rest_pricing);
            rating = itemView.findViewById(R.id.rest_rating);
            name = itemView.findViewById(R.id.rest_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.name.setText(restaurant.getName());
        holder.rating.setText(restaurant.getRating()+"");
        holder.price.setText(restaurant.getPrice());
        Picasso.get().load(restaurant.getImgUrl()).into(holder.restaurantImg);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

}
