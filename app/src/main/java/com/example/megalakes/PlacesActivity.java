package com.example.megalakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.megalakes.adapters.PlaceAdapter;
import com.example.megalakes.fragments.PlaceFragment;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.entities.Place;
import viewmodels.PlaceViewModel;

public class PlacesActivity extends AppCompatActivity {

    PlaceViewModel placeViewModel;
    public Button addButton;
    public Button changeButton;
    public Button deleteButton;
    public Button backButton;

    public Place selectedPlace;

    public GridView grid;

    public ArrayList<Place> place = new ArrayList<Place>();
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        db = Database.getDatabase(this);
        addButton = findViewById(R.id.addPlaceButton);
        changeButton = findViewById(R.id.changePlaceButton);
        deleteButton = findViewById(R.id.deletePlaceButton);
        backButton = findViewById(R.id.backPlacesButton);
        grid = findViewById(R.id.placesGrid);
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);

        backButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, AllTablesActivity.class);
            startActivity(intent);
        });

        addButton.setOnClickListener((click) ->
        {
            getSupportFragmentManager().beginTransaction().add(R.id.placesConsL, new PlaceFragment(), "fr").addToBackStack("frg").commit();
        });

        changeButton.setOnClickListener((click) ->
        {
            if (selectedPlace != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.placesConsL, new PlaceFragment(selectedPlace.id), "fr").addToBackStack("frg").commit();
            }
        });

        deleteButton.setOnClickListener((click) ->
        {
            if (selectedPlace != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Удаление");
                builder.setMessage("Удалить?");
                builder.setCancelable(true);
                builder.setPositiveButton("Да", (dialogInterface, i1) ->
                {
                    placeViewModel.delete(selectedPlace); selectedPlace = null;});
                builder.setNegativeButton("Нет", (dialogInterface, i2) -> dialogInterface.dismiss());
                builder.show();
            }
        });

        placeViewModel.getAllData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                grid.setAdapter(new PlaceAdapter(getApplicationContext(), R.layout.placelayout, places));
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlace = ((PlaceAdapter)parent.getAdapter()).getPlaceAt(position);
            }
        });
    }
}