package com.example.irvinetaste;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private List<Bookmark> data;
    private Context context;

    public BookmarkAdapter(Context ctx) {
        this.data = new LinkedList<Bookmark>();
        this.context = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
        TextView restaurantName;

        ViewHolder(View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.);
            restaurantName = itemView.findViewById(R.id.restaurantName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int vt) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Bookmark bm = data.get(pos);
        holder.restaurantName.setText(bm.getName());
        holder.restaurantName.setOnClickListener(goToRestaurant);
    }

    private View.OnClickListener goToRestaurant = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            context.startActivity(intent);
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }
}
