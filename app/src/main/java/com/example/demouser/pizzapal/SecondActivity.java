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
import android.widget.Spinner;

import com.example.demouser.pizzapal.data.Pizza;


/**
 * Created by audreylemberger on 11/2/15.
 */
public class SecondActivity extends AppCompatActivity {


    public static final String KEY_ITEM = "KEY_ITEM";
    private Pizza itemToEdit = null;
    private Button addButton;
    private Button cancelButton;
    private CheckBox gfBox;
    private CheckBox vegBox;
    private CheckBox kosherBox;
    private CheckBox veganBox;
    private Spinner buildings;
    private Spinner toppings;
    private EditText roomNumber;
    private EditText venderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Pizza) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }
        addButton = ((Button)findViewById(R.id.addButton));
        cancelButton = ((Button)findViewById(R.id.cancelButton));
        gfBox = ((CheckBox) findViewById(R.id.gfBox));
        vegBox = ((CheckBox) findViewById(R.id.vegBox));
        kosherBox = ((CheckBox) findViewById(R.id.kosherBox));
        veganBox = ((CheckBox) findViewById(R.id.veganBox));
        roomNumber = ((EditText)findViewById(R.id.edRoomNumText));
        venderInfo = ((EditText) findViewById(R.id.edVendorText));


        buildings = (Spinner)findViewById(R.id.spinner1);
        toppings = (Spinner)findViewById(R.id.spinner2);
        String[] buildingItems = new String[]{"1837", "Abbey", "Brigham", "Buckland", "Creighton", "Dickinson"
                , "Ham", "MacGregor", "North Mandelle", "South Mandelle", "Mead", "Pearsons", "Pearsons Annex"
                , "Porter", "Prospect", "North Rockefeller", "South Rockefeller", "Safford", "Torrey", "Wilder"
                , "Art Building", "Carr Laboratory", "Ciruti", "Clapp", "Cleveland", "Dwight", "Equestrian Center"
                , "StonyBrook", "Kendade", "Kendall Athletic Complex", "Pratt", "Reese", "Rooke Theatre"
                , "Shattuck", "Skinner", "Talcott Greenhouse", "Williston Observatory", "Smith Library"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, buildingItems);

        buildings.setAdapter(adapter);


        //rowspan/columnspan
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveItem();

            }
        });

        if (itemToEdit != null) {
            roomNumber.setText(itemToEdit.getLoc()[1]);
            venderInfo.setText(itemToEdit.getVendor());
            buildings.setSelection(itemToEdit.getBuildingNumber());
            vegBox.setChecked(itemToEdit.getRestrictions()[0]);
            veganBox.setChecked(itemToEdit.getRestrictions()[1]);
            kosherBox.setChecked(itemToEdit.getRestrictions()[2]);
            gfBox.setChecked(itemToEdit.getRestrictions()[3]);

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


        itemResult.setRoom(roomNumber.getText().toString());
        itemResult.setBuilding(buildings.getItemAtPosition(buildings.getSelectedItemPosition()).toString());
        itemResult.setBuildingNumber((buildings.getSelectedItemPosition()));
        itemResult.setIsVegan(veganBox.isChecked());
        itemResult.setIsVegetarian(vegBox.isChecked());
        itemResult.setIsKosher(kosherBox.isChecked());
        itemResult.setIsGF(gfBox.isChecked());

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }



}


