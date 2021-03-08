package com.example.irvinetaste;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRestaurantListAdapter extends RecyclerView.Adapter<SearchRestaurantListAdapter.ViewHolder>{

    private List<Restaurant> mData;
    private Context context;
    SearchRestaurantListAdapter(List<Restaurant> data, Context context) {
        this.mData = data;
        this.context = context;
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

        holder.name.setText(restaurant.getName());
        holder.rating.setText(restaurant.getRating().toString());
        holder.price.setText(restaurant.getPrice());
        String address = restaurant.getLocation();

        List<String>transactions = restaurant.getTransactions();
        String delivery = "X Delivery", takeout = "X Takeout";
        if(transactions.indexOf("delivery")!= -1){
            delivery = "√ Delivery";
        }
        if(transactions.indexOf("takeout")!= -1){
            takeout = "√ Takeout";
        }

        holder.address.setText(address);
        holder.delivery.setText(delivery);
        holder.takeout.setText(takeout);
        String distance = String.format("%s m", Double.valueOf(restaurant.getDistance()).intValue());
        holder.distance.setText(distance);
        List<HashMap<String, String>>categories = restaurant.getCategories();
        String categoriesStr = categories.stream().map(node -> node.get("title")).collect(Collectors.toList()).toString();
        holder.categories.setText(categoriesStr.substring(1, categoriesStr.length()-1));

        Picasso.get().load(restaurant.getImgUrl()).into(holder.restaurantImg);

        myClickListener listener = new myClickListener(restaurant.getId());
        holder.restaurantImg.setOnClickListener(listener);
        holder.address.setOnClickListener(listener);
        holder.categories.setOnClickListener(listener);
        holder.delivery.setOnClickListener(listener);
        holder.displayAddress.setOnClickListener(listener);
        holder.rating.setOnClickListener(listener);
        holder.name.setOnClickListener(listener);
        holder.price.setOnClickListener(listener);
        holder.takeout.setOnClickListener(listener);
        holder.distance.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myClickListener implements View.OnClickListener{
        String id;
        public myClickListener(String id){
            this.id = id;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView rating;
        TextView price;
        TextView displayAddress;
        TextView address;
        TextView delivery;
        TextView takeout;
        TextView distance;
        TextView categories;
        ImageView restaurantImg;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.searchName);
            rating = itemView.findViewById(R.id.searchRating);
            price = itemView.findViewById(R.id.searchPrice);
            address = itemView.findViewById(R.id.searchAddress);
            displayAddress = itemView.findViewById(R.id.searchAddress);
            distance = itemView.findViewById(R.id.searchDistance);
            delivery = itemView.findViewById(R.id.searchDelivery);
            takeout = itemView.findViewById(R.id.searchTakeout);
            categories = itemView.findViewById(R.id.searchCategories);

            restaurantImg = itemView.findViewById(R.id.searchRestaurantImg);
        }
    }

    // https://stackoverflow.com/questions/26635841/recyclerview-change-data-set
    public void updateList(List<Restaurant> data){
        this.mData = data;
        // rebind and relayout views
        notifyDataSetChanged();
    }

}
