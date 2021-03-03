package com.example.irvinetaste;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.function.Consumer;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private NearbyRestaurants nearbyRestaurants;
    private Context context;

    NearbyAdapter(NearbyRestaurants nearbyRestaurantsList, Context ctx) {
        nearbyRestaurants = nearbyRestaurantsList;
        context = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView rating;
        TextView price;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById((R.id.rest_pic));
            name = itemView.findViewById(R.id.rest_name);
            price = itemView.findViewById(R.id.rest_price);
            rating = itemView.findViewById(R.id.rest_rating);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant restaurant = nearbyRestaurants.get(position);
        holder.name.setText(restaurant.getName());
        holder.name.setOnClickListener(goToRestaurant);
        holder.imageView.setOnClickListener(goToRestaurant);
        holder.price.setText(restaurant.getPrice());
        holder.rating.setText(restaurant.getRating().toString());
        Picasso.get().load(restaurant.getImgUrl()).into(holder.imageView);
    }

    private View.OnClickListener goToRestaurant = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            context.startActivity(intent);
        }
    };

    @Override
    public int getItemCount() { return this.nearbyRestaurants.size(); }
}
