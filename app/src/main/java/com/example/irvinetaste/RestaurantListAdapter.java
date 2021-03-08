package com.example.irvinetaste;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private List<Restaurant> restaurants;
    private Context context;

    RestaurantListAdapter(List<Restaurant> restaurants, Context context) {
        this.restaurants = restaurants;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView rating;
        TextView price;
        ImageView restaurantImg;

        ViewHolder(View itemView) {
            super(itemView);
            restaurantImg = itemView.findViewById(R.id.rest_pic);
            price = itemView.findViewById(R.id.rest_price);
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
        holder.rating.setText(restaurant.getRating() + "");
        holder.price.setText(restaurant.getPrice());
        Picasso.get().load(restaurant.getImgUrl()).into(holder.restaurantImg);
        myClickListener listener = new myClickListener(restaurant.getId());
        holder.restaurantImg.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class myClickListener implements View.OnClickListener{
        String id;
        public myClickListener(String id){
            this.id = id;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            intent.putExtra("id",id);
            context.startActivity(intent);
        }
    }

}
