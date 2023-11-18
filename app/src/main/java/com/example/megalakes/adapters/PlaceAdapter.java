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
import database.entities.Place;

public class PlaceAdapter extends ArrayAdapter<Place> {
    private LayoutInflater inflater;
    private int layout;
    private List<Place> places;

    public PlaceAdapter(Context context, int resource, List<Place> places) {
        super(context, resource, places);
        this.places = places;
        layout = resource;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);


        TextView idtext = view.findViewById(R.id.placeIdAdapter);
        TextView placetext = view.findViewById(R.id.placeTextAdapter);

        Place place = places.get(position);

        idtext.setText(Long.toString(place.id));
        placetext.setText(place.name);

        return view;
    }

    public Place getPlaceAt(int i)
    {
        return places.get(i);
    }
}
