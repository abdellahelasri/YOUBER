package com.example.youber.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youber.Client.ManageClient;
import com.example.youber.R;
import com.example.youber.domain.ManageCategorie;
import com.example.youber.domain.ManageProduit;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button products = (Button) view.findViewById(R.id.products);
        Button clients = (Button) view.findViewById(R.id.clients);
        Button categories = (Button) view.findViewById(R.id.categories);

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageProduit.class);
                startActivity(intent);
            }
        });

        clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageClient.class);
                startActivity(intent);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageCategorie.class);
                startActivity(intent);
            }
        });

        return view;
    }



}
