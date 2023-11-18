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

import database.Database;
import database.entities.Origin;
import viewmodels.OriginViewModel;

public class OriginFragment extends Fragment {

    boolean redact;
    private long id;
    Button addButton;
    Button cancelButton;
    OriginViewModel originViewModel;
    Origin changeOrigin;
    EditText originText;

    public OriginFragment()
    {
        redact = false;
    }

    public void CloseFragment()
    {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public OriginFragment(long id)
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

        return inflater.inflate(R.layout.fragment_origin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeOrigin = new Origin();
        originText = view.findViewById(R.id.originFragmentEditText);
        originViewModel = new ViewModelProvider(requireActivity()).get(OriginViewModel.class);
        addButton = view.findViewById(R.id.addOriginFragmentButton);
        cancelButton = view.findViewById(R.id.cancelOriginFragmentButton);

        cancelButton.setOnClickListener((click) -> CloseFragment());
        if (redact)
        {
            addButton.setText("изменить");
            originViewModel.getOriginAt(id).observe(getViewLifecycleOwner(), new Observer<Origin>() {
                @Override
                public void onChanged(Origin origin) {
                    changeOrigin = origin;
                    originText.setText(origin.origin);
                }
            });
            addButton.setOnClickListener((click) -> {
                changeOrigin.origin = originText.getText().toString();
                originViewModel.update(changeOrigin);
                CloseFragment();
            });
        }
        else {
            addButton.setOnClickListener((click) -> {
                changeOrigin.origin = originText.getText().toString();
                originViewModel.insert(changeOrigin);
                CloseFragment();
            });
        }
    }
}