package com.example.megalakes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.megalakes.R;

import java.util.List;

import database.entities.Salinity;

public class SalinityAdapter extends ArrayAdapter<Salinity> {
    private LayoutInflater inflater;
    private int layout;
    private List<Salinity> salinities;

    public SalinityAdapter(Context context, int resource, List<Salinity> salinities) {
        super(context, resource, salinities);
        this.salinities = salinities;
        layout = resource;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView idtext = view.findViewById(R.id.salinityIdAdapter);
        TextView salinitytext = view.findViewById(R.id.salinityTextAdapter);

        Salinity salinity = salinities.get(position);

        idtext.setText(Long.toString(salinity.id));
        salinitytext.setText(salinity.salinity);

        return view;
    }

    public Salinity getSalinityAt(int i)
    {
        return salinities.get(i);
    }
}
