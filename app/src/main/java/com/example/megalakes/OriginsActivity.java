package com.example.megalakes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.megalakes.adapters.OriginAdapter;
import com.example.megalakes.fragments.OriginFragment;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.entities.Origin;
import viewmodels.OriginViewModel;

public class OriginsActivity extends AppCompatActivity {

    OriginViewModel originViewModel;

    public ProgressBar progressBar;
    public Button addButton;
    public Button changeButton;
    public Button deleteButton;
    public Button backButton;

    public Origin selectedOrigin;

    public GridView grid;

    public ArrayList<Origin> orig = new ArrayList<Origin>();
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origins);
        db = Database.getDatabase(this);
        progressBar = findViewById(R.id.progressBar);
        addButton = findViewById(R.id.addOriginButton);
        changeButton = findViewById(R.id.changeOriginButton);
        deleteButton = findViewById(R.id.deleteOriginButton);
        backButton = findViewById(R.id.backOriginButton);
        grid = findViewById(R.id.originsgrid);
        originViewModel = new ViewModelProvider(this).get(OriginViewModel.class);

        backButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, AllTablesActivity.class);
            startActivity(intent);
        });

        addButton.setOnClickListener((click) ->
        {
            getSupportFragmentManager().beginTransaction().add(R.id.originsConsL, new OriginFragment(), "fr").addToBackStack("frg").commit();
        });

        changeButton.setOnClickListener((click) ->
        {
            if (selectedOrigin != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.originsConsL, new OriginFragment(selectedOrigin.id), "fr").addToBackStack("frg").commit();
            }
        });

        deleteButton.setOnClickListener((click) ->
        {
            if (selectedOrigin != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Удаление");
                builder.setMessage("Удалить?");
                builder.setCancelable(true);
                builder.setPositiveButton("Да", (dialogInterface, i1) -> {
                    originViewModel.delete(selectedOrigin);
                    selectedOrigin = null;
                });
                builder.setNegativeButton("Нет", (dialogInterface, i2) -> dialogInterface.dismiss());
                builder.show();
            }
        });

        originViewModel.getAllData().observe(this, new Observer<List<Origin>>() {
            @Override
            public void onChanged(List<Origin> origins) {
                grid.setAdapter(new OriginAdapter(getApplicationContext(), R.layout.originlayout, origins));
            }
        });

        originViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrigin = ((OriginAdapter)parent.getAdapter()).getOriginAt(position);
            }
        });
    }
}