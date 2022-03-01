package com.example.youber.domain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.adapter.CategoryAdapter;
import com.example.youber.adapter.ClientAdapter;
import com.example.youber.adapter.ProduitAdapter;
import com.example.youber.domain.ProduitAdapter2;
import com.example.youber.Client.Client;
import com.example.youber.Client.ManageClient;
import com.example.youber.Client.Register_Client;
import com.example.youber.fragments.SearchFragment;
import com.example.youber.helper.DatabaseHelper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManageCategorie extends AppCompatActivity {

    FloatingActionButton addButton, displaybutton, updatebutton, deletebutton;
    RecyclerView recyclerViewCategories;
    ProduitAdapter2 adapter;

    DatabaseHelper dbHelper;
    SearchView searchView;
    ArrayList<Categorie> listCategories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produits_table);
        addButton = findViewById(R.id.addbutton);
        searchView = findViewById(R.id.searchView);

        dbHelper = new DatabaseHelper(this);

        listCategories = dbHelper.displayCategorie();



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageCategorie.this, AddCategorie.class));

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listCategories = dbHelper.displayCategorie();
                ArrayList <Categorie> listPro =  new ArrayList<>();
                for (Categorie item : listCategories) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getTitre().toLowerCase().contains(newText.toLowerCase())) {
                        listPro.add(item);
                    }
                }
                listCategories = listPro;
                getCategories();
                return false;
            }
        });

        getCategories();

    }

    void getCategories(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerViewCategories = findViewById(R.id.recyclerView);
        recyclerViewCategories.setLayoutManager(linearLayoutManager);

        CategoryAdapter adapter = new CategoryAdapter( listCategories);
        recyclerViewCategories.setAdapter(adapter);


    }


}


