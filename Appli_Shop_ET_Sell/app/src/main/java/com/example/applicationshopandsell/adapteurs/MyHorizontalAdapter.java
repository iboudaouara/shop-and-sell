package com.example.applicationshopandsell.adapteurs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applicationshopandsell.OnItemClickListener;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.objets.Item;

import java.util.List;

public class MyHorizontalAdapter extends RecyclerView.Adapter<MyHorizontalAdapter.ViewHolder> {

    private List<Item> itemList;
    private OnItemClickListener listener;

    public MyHorizontalAdapter(List<Item> items, OnItemClickListener listener) {
        this.itemList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_layout, parent, false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textView.setText(String.valueOf(item.getPrix()) + " $");

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.imageView);

        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick((Item) v.getTag());
                    }
                }
            });
        }
    }
}
