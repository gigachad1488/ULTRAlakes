package com.example.megalakes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import database.Database;
import database.entities.Origin;

public class MainActivity extends AppCompatActivity {

    public Button ob;
    public EditText oet;
    public TextView ot;
    public Button stt;
    Database db;
    LiveData<Origin> orig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Database.getDatabase(this);
        ob = findViewById(R.id.updateOriginButton);
        oet = findViewById(R.id.editTextOrigin);
        ot = findViewById(R.id.originsText);
        stt = findViewById(R.id.addOriginButton);

        /*


        UpdateOrigins();

        stt.setOnClickListener((click) ->
        {
            UpdateOrigins();
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                orig = db.originDao().getAllOrigins();
            }
        });
        t.start();
*/
        ob.setOnClickListener((click) ->
        {
            Thread tt = new Thread(new Runnable() {
                @Override
                public void run() {
                    Origin or = new Origin();
                    or.origin = oet.getText().toString();
                    db.originDao().insert(or);
                }
            });
            tt.start();
            //UpdateOrigins();
        });



        Button b = findViewById(R.id.button2);
        b.setOnClickListener((clicl) ->
        {
            Intent i = new Intent(this, AllTablesActivity.class);
            startActivity(i);
        });
    }

    /*
    public void UpdateOrigins()
    {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                orig = db.originDao().getAllOrigins();
                String s = "";
                if (orig != null) {
                    for (int i = 0; i < orig.size(); i++) {
                        s += orig.get(i).origin + "\r\n";
                    }
                    ot.setText(s);
                }
            }
        });
        t1.start();
    }


     */


}