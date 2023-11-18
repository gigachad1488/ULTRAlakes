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
import database.entities.WaterBalance;
import viewmodels.OriginViewModel;
import viewmodels.WaterBalanceViewModel;

public class WaterBalanceFragment extends Fragment {

    boolean redact;
    private long id;
    Button addButton;
    Button cancelButton;
    WaterBalanceViewModel wbViewModel;
    WaterBalance changeWb;
    EditText wbText;

    public WaterBalanceFragment()
    {
        redact = false;
    }

    public void CloseFragment()
    {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public WaterBalanceFragment(long id)
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

        return inflater.inflate(R.layout.fragment_water_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeWb = new WaterBalance();
        wbText = view.findViewById(R.id.wbFragmentEditText);
        wbViewModel = new ViewModelProvider(requireActivity()).get(WaterBalanceViewModel.class);
        addButton = view.findViewById(R.id.addWbFragmentButton);
        cancelButton = view.findViewById(R.id.cancelWbFragmentButton);

        cancelButton.setOnClickListener((click) -> CloseFragment());
        if (redact)
        {
            addButton.setText("изменить");
            wbViewModel.getWbAt(id).observe(getViewLifecycleOwner(), new Observer<WaterBalance>() {
                @Override
                public void onChanged(WaterBalance wb) {
                    changeWb = wb;
                    wbText.setText(wb.type);
                }
            });
            addButton.setOnClickListener((click) -> {
                changeWb.type = wbText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        wbViewModel.update(changeWb);
                        CloseFragment();
                    }
                }).start();
            });
        }
        else {
            addButton.setOnClickListener((click) -> {
                changeWb.type = wbText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        wbViewModel.insert(changeWb);
                        CloseFragment();
                    }
                }).start();
            });
        }
    }
}