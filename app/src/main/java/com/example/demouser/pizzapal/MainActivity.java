package com.example.demouser.pizzapal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.demouser.pizzapal.adapter.ListRecycler;
import com.example.demouser.pizzapal.data.Pizza;
import com.example.demouser.pizzapal.touch.ListTouchHelper;

import java.util.List;


/**
 * Created by audreylemberger on 11/2/15.
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_NEW_ITEM = 101;
    public static final int REQUEST_EDIT_ITEM = 102;

    private ListRecycler adapter;
    public static final String KEY_EDIT = "KEY_EDIT";
    private Pizza itemToEditHolder;
    private int itemToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //problem with this
        List<Pizza> pizzaList = Pizza.listAll(Pizza.class);

        adapter = new ListRecycler(pizzaList);
        RecyclerView recyclerViewPlaces = (RecyclerView) findViewById(
                R.id.recycler_view);
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPlaces.setAdapter(adapter);

        ListTouchHelper touchHelperCallback = new ListTouchHelper(
                adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerViewPlaces);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            showCreateItemActivity();
        }
        else if(id == R.id.action_delete){
            Pizza.deleteAll(Pizza.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_ITEM) {
                    Pizza pizza = (Pizza) data.getSerializableExtra(
                            SecondActivity.KEY_ITEM);

                    adapter.addItem(pizza);

                } else if (requestCode == REQUEST_EDIT_ITEM) {
                    Pizza itemTemp = (Pizza) data.getSerializableExtra(
                            SecondActivity.KEY_ITEM);

                    itemToEditHolder.setBuilding(itemTemp.getBuilding());
                    itemToEditHolder.setRoom(itemTemp.getRoom());
                    itemToEditHolder.setIsGF(itemTemp.getIsGF());
                    itemToEditHolder.setIsKosher(itemTemp.getIsKosher());
                    itemToEditHolder.setIsVegetarian(itemTemp.getIsVegetarian());
                    itemToEditHolder.setIsVegan(itemTemp.getIsVegan());


                    if (itemToEditPosition != -1) {
                        adapter.updateItem(itemToEditPosition, itemToEditHolder);
                        itemToEditPosition = -1;
                    }else {
                        adapter.notifyDataSetChanged();
                    }

                }
                break;
            case RESULT_CANCELED:

                break;
        }
    }
    private void showCreateItemActivity() {
        Intent intentStart = new Intent(MainActivity.this,
                SecondActivity.class);
        startActivityForResult(intentStart, REQUEST_NEW_ITEM);
    }
    public void showEditPlaceActivity(Pizza placeToEdit, int position) {
        Intent intentStart = new Intent(MainActivity.this,
                SecondActivity.class);
        itemToEditHolder = placeToEdit;
        itemToEditPosition = position;

        intentStart.putExtra(KEY_EDIT, placeToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_ITEM);
    }
}
