package com.example.youber.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.youber.R;
import com.example.youber.adapter.ClientAdapter;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class ManageClient extends AppCompatActivity {

    Button addButton, displaybutton, updatebutton, deletebutton;
    RecyclerView recyclerViewCategories;
    ClientAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_client);
        addButton = findViewById(R.id.idregidter);

        dbHelper = new DatabaseHelper(this);

        viewClients();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageClient.this, Register_Client.class));
            }
        });
        //La vie c'est molo molo
    }

    private void viewClients() {
        ArrayList<Client> listClients = dbHelper.displayAllClient();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        RecyclerView recyclerViewCategories = findViewById(R.id.recyclerViewClient2);
        recyclerViewCategories.setLayoutManager(linearLayoutManager);

        ClientAdapter adapter = new ClientAdapter(listClients, this);
        recyclerViewCategories.setAdapter(adapter);
    }


}