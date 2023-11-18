package com.example.megalakes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.megalakes.adapters.OriginAdapter;
import com.example.megalakes.adapters.WaterBalanceAdapter;
import com.example.megalakes.fragments.OriginFragment;
import com.example.megalakes.fragments.WaterBalanceFragment;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.entities.Origin;
import database.entities.WaterBalance;
import viewmodels.OriginViewModel;
import viewmodels.WaterBalanceViewModel;

public class WaterBalancesActivity extends AppCompatActivity {

    WaterBalanceViewModel wbViewModel;
    public Button addButton;
    public Button changeButton;
    public Button deleteButton;
    public Button backButton;

    public WaterBalance selectedWb;

    public GridView grid;

    public ArrayList<WaterBalance> wb = new ArrayList<WaterBalance>();
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_balances);
        db = Database.getDatabase(this);
        addButton = findViewById(R.id.addWbButton);
        changeButton = findViewById(R.id.changeWbButton);
        deleteButton = findViewById(R.id.deleteWbButton);
        backButton = findViewById(R.id.backWbButton);
        grid = findViewById(R.id.wbsgrid);
        wbViewModel = new ViewModelProvider(this).get(WaterBalanceViewModel.class);

        backButton.setOnClickListener((click) ->
        {
            Intent intent = new Intent(this, AllTablesActivity.class);
            startActivity(intent);
        });

        addButton.setOnClickListener((click) ->
        {
            getSupportFragmentManager().beginTransaction().add(R.id.wbsConsL, new WaterBalanceFragment(), "fr").addToBackStack("frg").commit();
        });

        changeButton.setOnClickListener((click) ->
        {
            if (selectedWb != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.wbsConsL, new WaterBalanceFragment(selectedWb.id), "fr").addToBackStack("frg").commit();
            }
        });

        deleteButton.setOnClickListener((click) ->
        {
            if (selectedWb != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Удаление");
                builder.setMessage("Удалить?");
                builder.setCancelable(true);
                builder.setPositiveButton("Да", (dialogInterface, i1) -> {
                    wbViewModel.delete(selectedWb);
                    selectedWb = null;
                });
                builder.setNegativeButton("Нет", (dialogInterface, i2) -> dialogInterface.dismiss());
                builder.show();
            }
        });

        wbViewModel.getAllData().observe(this, new Observer<List<WaterBalance>>() {
            @Override
            public void onChanged(List<WaterBalance> wbs) {
                grid.setAdapter(new WaterBalanceAdapter(getApplicationContext(), R.layout.waterbalancelayout, wbs));
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedWb = ((WaterBalanceAdapter)parent.getAdapter()).getWbAt(position);
            }
        });
    }
}