package com.example.demouser.pizzapal;

/**
 * Created by demouser on 1/11/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demouser.pizzapal.data.Pizza;


/**
 * Created by audreylemberger on 11/2/15.
 */
public class SecondActivity extends AppCompatActivity {

    private EditText itemName;
    private EditText price;
    private EditText description;
    private CheckBox checkBox;
    private Spinner dropdown;
    public static final String KEY_ITEM = "KEY_ITEM";
    private Pizza itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Pizza) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }
        itemName = (EditText) findViewById(R.id.itemname);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        dropdown = (Spinner)findViewById(R.id.dropdown);
        String[] items = new String[]{"Food", "Electronic", "Book"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveItem();

            }
        });

        if (itemToEdit != null) {
            itemName.setText(itemToEdit.getItem());
            description.setText(itemToEdit.getDescription());
            dropdown.setSelection(itemToEdit.getPlaceType().getValue());
            checkBox.setChecked(itemToEdit.isPurchased());
            price.setText(itemToEdit.getItem());
        }

    }



    private void saveItem() {
        Intent intentResult = new Intent();
        Pizza itemResult = null;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        } else {
            itemResult = new Pizza();

        }

        itemResult.setItem(itemName.getText().toString());
        itemResult.setDescription(description.getText().toString());
        itemResult.setPlaceType(
                ShoppingItem.PlaceType.fromInt(dropdown.getSelectedItemPosition()));
        itemResult.setPurchased(checkBox.isChecked());
        itemResult.setPrice(price.getText().toString());

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }



}


