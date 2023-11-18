package com.example.megalakes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.megalakes.R;

import database.entities.Place;
import database.entities.Salinity;
import viewmodels.PlaceViewModel;
import viewmodels.SalinityViewModel;

public class SalinityFragment extends Fragment {

    boolean redact;
    private long id;
    Button addButton;
    Button cancelButton;
    SalinityViewModel salinityViewModel;
    Salinity changeSalinity;
    EditText salinityText;

    public SalinityFragment()
    {
        redact = false;
    }

    public void CloseFragment()
    {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public SalinityFragment(long id)
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

        return inflater.inflate(R.layout.fragment_salinity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeSalinity = new Salinity();
        salinityText = view.findViewById(R.id.salinityFragmentEditText);
        salinityViewModel = new ViewModelProvider(requireActivity()).get(SalinityViewModel.class);
        addButton = view.findViewById(R.id.addSalinityFragmentButton);
        cancelButton = view.findViewById(R.id.cancelSalinityFragmentButton);

        cancelButton.setOnClickListener((click) -> CloseFragment());
        if (redact)
        {
            addButton.setText("изменить");
            salinityViewModel.getSalinityAt(id).observe(getViewLifecycleOwner(), new Observer<Salinity>() {
                @Override
                public void onChanged(Salinity salinity) {
                    changeSalinity = salinity;
                    salinityText.setText(salinity.salinity);
                }
            });
            addButton.setOnClickListener((click) -> {
                changeSalinity.salinity = salinityText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        salinityViewModel.update(changeSalinity);
                        CloseFragment();
                    }
                }).start();
            });
        }
        else {
            addButton.setOnClickListener((click) -> {
                changeSalinity.salinity = salinityText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        salinityViewModel.insert(changeSalinity);
                        CloseFragment();
                    }
                }).start();
            });
        }
    }
}