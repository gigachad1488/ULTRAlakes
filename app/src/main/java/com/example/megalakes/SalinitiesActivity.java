package com.example.megalakes;

import android.content.Intent;
import android.os.Bundle;

import com.example.megalakes.adapters.PlaceAdapter;
import com.example.megalakes.adapters.SalinityAdapter;
import com.example.megalakes.fragments.PlaceFragment;
import com.example.megalakes.fragments.SalinityFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.megalakes.databinding.ActivitySalinitiesBinding;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.entities.Place;
import database.entities.Salinity;
import viewmodels.PlaceViewModel;
import viewmodels.SalinityViewModel;

public class SalinitiesActivity extends AppCompatActivity {

    SalinityViewModel salinityViewModel;
    public Button addButton;
    public Button changeButton;
    public Button deleteButton;
    public Button backButton;

    public Salinity selectedSalinity;

    public GridView grid;

    public ArrayList<Salinity> salinities = new ArrayList<Salinity>();
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salinities);
        db = Database.getDatabase(this);
        addButton = findViewById(R.id.addSalinityButton);
        changeButton = findViewById(R.id.changeSalinityButton);
        deleteButton = findViewById(R.id.deleteSalinityButton);
        backButton = findViewById(R.id.backSalinityButton);
        grid = findViewById(R.id.salinitiesgrid);
        salinityViewModel = new ViewModelProvider(this).get(SalinityViewModel.class);

        backButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, AllTablesActivity.class);
            startActivity(intent);
        });

        addButton.setOnClickListener((click) ->
        {
            getSupportFragmentManager().beginTransaction().add(R.id.salinitiesConsL, new SalinityFragment(), "fr").addToBackStack("frg").commit();
        });

        changeButton.setOnClickListener((click) ->
        {
            if (selectedSalinity != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.salinitiesConsL, new SalinityFragment(selectedSalinity.id), "fr").addToBackStack("frg").commit();
            }
        });

        deleteButton.setOnClickListener((click) ->
        {
            if (selectedSalinity != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Удаление");
                builder.setMessage("Удалить?");
                builder.setCancelable(true);
                builder.setPositiveButton("Да", (dialogInterface, i1) ->
                {
                    salinityViewModel.delete(selectedSalinity); selectedSalinity = null;});
                builder.setNegativeButton("Нет", (dialogInterface, i2) -> dialogInterface.dismiss());
                builder.show();
            }
        });

        salinityViewModel.getAllData().observe(this, new Observer<List<Salinity>>() {
            @Override
            public void onChanged(List<Salinity> salinities) {
                grid.setAdapter(new SalinityAdapter(getApplicationContext(), R.layout.salinitylayout, salinities));
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSalinity = ((SalinityAdapter)parent.getAdapter()).getSalinityAt(position);
            }
        });
    }
}