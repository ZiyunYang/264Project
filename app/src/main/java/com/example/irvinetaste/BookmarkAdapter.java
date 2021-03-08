package com.example.irvinetaste;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.irvinetaste.utils.DBThread;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private List<Bookmark> bookmarks;
    private Context context;

    public BookmarkAdapter(Context ctx) {
        this.bookmarks = User.getBookmarks();
        this.context = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView restaurantName;
        Button btn;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bmpic);
            restaurantName = itemView.findViewById(R.id.bmname);
            btn = itemView.findViewById(R.id.bmbtn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int vt) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Bookmark bm = bookmarks.get(pos);
        MyClickListener myClickListener = new MyClickListener(bm.getRestaurantId());
        holder.imageView.setOnClickListener(myClickListener);
        Picasso.get().load(bm.getImageUrl()).into(holder.imageView);
        holder.restaurantName.setText(bm.getName());
        holder.restaurantName.setOnClickListener(myClickListener);
        onClick(holder.btn, (c) -> removeBookmark((String) c), bm.getRestaurantId());
    }

    public class MyClickListener implements View.OnClickListener {
        String id;
        public MyClickListener(String restId) { this.id = restId; }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }
    }

    public void removeBookmark(String id) {
        DBThread dbThread = new DBThread(2, id);
        Thread thread = new Thread(dbThread);
        thread.start();
        notifyDataSetChanged();
    }

    public void onClick(Button btn, Consumer c, String id) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bookmark rm = null;
                for (Bookmark bm : bookmarks) { if (bm.getRestaurantId() == id) { rm = bm; break; } }
                User.removeBookmark(rm);
                bookmarks = User.getBookmarks();
                c.accept(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }
}
