package com.example.megalakes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AllTablesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tables);

        Button originButton = findViewById(R.id.originButton);
        Button placeButton = findViewById(R.id.placeButton);
        Button salinityButton = findViewById(R.id.salinityButton);
        Button wbButton = findViewById(R.id.waterbalanceButton);

        originButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, OriginsActivity.class);
            startActivity(intent);
        });

        placeButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, PlacesActivity.class);
            startActivity(intent);
        });

        salinityButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, SalinitiesActivity.class);
            startActivity(intent);
        });

        wbButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, WaterBalancesActivity.class);
            startActivity(intent);
        });
    }
}