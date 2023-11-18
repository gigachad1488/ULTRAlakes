package com.example.megalakes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.megalakes.R;

import database.entities.Origin;
import database.entities.Place;
import viewmodels.OriginViewModel;
import viewmodels.PlaceViewModel;

public class PlaceFragment extends Fragment {

    boolean redact;
    private long id;
    Button addButton;
    Button cancelButton;
    PlaceViewModel placeViewModel;
    Place changePlace;
    EditText placeText;

    public PlaceFragment()
    {
        redact = false;
    }

    public void CloseFragment()
    {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public PlaceFragment(long id)
    {
        redact = true;
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePlace = new Place();
        placeText = view.findViewById(R.id.placeFragmentEditText);
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
        addButton = view.findViewById(R.id.addPlaceFragmentButton);
        cancelButton = view.findViewById(R.id.cancelPlaceFragmentButton);

        cancelButton.setOnClickListener((click) -> CloseFragment());
        if (redact)
        {
            addButton.setText("изменить");
            placeViewModel.getPlaceAt(id).observe(getViewLifecycleOwner(), new Observer<Place>() {
                @Override
                public void onChanged(Place place) {
                    changePlace = place;
                    placeText.setText(place.name);
                }
            });
            addButton.setOnClickListener((click) -> {
                changePlace.name = placeText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        placeViewModel.update(changePlace);
                        CloseFragment();
                    }
                }).start();
            });
        }
        else {
            addButton.setOnClickListener((click) -> {
                changePlace.name = placeText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        placeViewModel.insert(changePlace);
                        CloseFragment();
                    }
                }).start();
            });
        }
    }
}