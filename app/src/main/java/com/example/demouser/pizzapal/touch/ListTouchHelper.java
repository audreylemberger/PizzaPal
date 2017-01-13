package com.example.demouser.pizzapal.touch;

/**
 * Created by demouser on 1/13/17.
 */

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.demouser.pizzapal.adapter.ListRecycler;


/**
 * Created by audreylemberger on 11/4/15.
 */
public class ListTouchHelper extends ItemTouchHelper.Callback{

    private ListRecycler adapter;

    public ListTouchHelper(ListRecycler adapter) {
        this.adapter = adapter;
    }



    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.swapItems(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeTodo(viewHolder.getAdapterPosition());
    }
}
