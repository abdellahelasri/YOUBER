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
import android.widget.TextView;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.SecondActivity;
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

public class ManageProduit extends AppCompatActivity{

    Button addButton, backBtn;
    DatabaseHelper dbHelper;
    TextView errorText;
    ImageView errorImage;
    SearchView searchView;
    ArrayList<Produit> listProduits = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produits_table);
        backBtn = findViewById(R.id.backBtn);
        addButton = findViewById(R.id.addbutton);
        searchView = findViewById(R.id.searchItem);
        errorText = findViewById(R.id.textError);
        errorImage = findViewById(R.id.imageError);

        dbHelper = new DatabaseHelper(this);

        listProduits = dbHelper.displayAllProducts();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageProduit.this, AddProduit.class));
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listProduits = dbHelper.displayAllProducts();
                ArrayList <Produit> listPro =  new ArrayList<>();
                for (Produit item : listProduits) {
                    if (item.getTitre().toLowerCase().contains(newText.toLowerCase())) {
                        listPro.add(item);
                    }
                }
                listProduits = listPro;
                getProducts();
                return false;
            }
        });
        getProducts();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getProducts(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewProducts = findViewById(R.id.recyclerView);
        recyclerViewProducts.setLayoutManager(linearLayoutManager);

        if (listProduits.isEmpty())
        {
            errorImage.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.VISIBLE);
            recyclerViewProducts.setVisibility(View.INVISIBLE);
        }
        else {
            errorImage.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.INVISIBLE);
            recyclerViewProducts.setVisibility(View.VISIBLE);
            ProduitAdapter2 adapter = new ProduitAdapter2(listProduits, this);
            recyclerViewProducts.setAdapter(adapter);
        }
    }
}


