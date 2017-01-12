package com.example.demouser.pizzapal.adapter;

/**
 * Created by demouser on 1/11/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.example.demouser.pizzapal.R;
import com.example.demouser.pizzapal.data.Pizza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by audreylemberger on 11/4/15.
 */
public class ListRecycler extends RecyclerView.Adapter<ListRecycler.ViewHolder>{
    private List<Pizza> items;
    private Context context;

    public ListRecycler (List<Pizza> placesList) {
        this.items = placesList;
    }


    public void addItem(Pizza item) {
        item.save();
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pizza item = items.get(position);

        holder.itemNameView.setText(item.getItem());
        holder.priceView.setText(item.getPrice());
        holder.checkBox.setChecked(item.isPurchased());




        // why no button??
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.delete();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemNameView;
        private TextView priceView;
        private CheckBox checkBox;
        private Button deleteButton;
        private ImageView ivIcon;


        public ViewHolder(View itemView){
            super(itemView);
            itemNameView = (TextView) itemView.findViewById(R.id.itemNameView);
            priceView = (TextView) itemView.findViewById(R.id.priceView);
            checkBox = (CheckBox) itemView.findViewById(R.id.purchasedBox);
            deleteButton = (Button) itemView.findViewById(R.id.deleteItem);
            ivIcon = (ImageView) itemView.findViewById(R.id.imageNote);
        }
    }

    public void removeTodo(int index){
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public void updateItem(int index, Pizza item) {
        items.set(index, item);
        notifyDataSetChanged();
    }
}


