package com.example.megalakes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.megalakes.R;

import java.util.List;

import database.entities.Origin;
import database.entities.WaterBalance;

public class WaterBalanceAdapter extends ArrayAdapter<WaterBalance> {
    private LayoutInflater inflater;
    private int layout;
    private List<WaterBalance> wbs;

    public WaterBalanceAdapter(Context context, int resource, List<WaterBalance> wbs) {
        super(context, resource, wbs);
        this.wbs = wbs;
        layout = resource;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);


        TextView idtext = view.findViewById(R.id.wbIdAdapter);
        TextView wbtext = view.findViewById(R.id.wbTextAdapter);

        WaterBalance wb = wbs.get(position);

        idtext.setText(Long.toString(wb.id));
        wbtext.setText(wb.type);

        return view;
    }

    public WaterBalance getWbAt(int i)
    {
        return wbs.get(i);
    }

}
