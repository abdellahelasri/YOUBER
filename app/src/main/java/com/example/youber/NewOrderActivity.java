package com.example.youber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.adapter.CategoryAdapter;
import com.example.youber.adapter.ProduitAdapter;
import com.example.youber.domain.Categorie;
import com.example.youber.domain.Produit;
import com.example.youber.fragments.SearchFragment;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class NewOrderActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategories;
    SearchView searchView;
    Button cartBtn, backBtn;
    ImageView erreurImage;
    TextView cartTxt, productsTxt, erreurTxt;
    int id_order;

    DatabaseHelper dbHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordering_activity);

        searchView = findViewById(R.id.search);
        cartBtn = findViewById(R.id.cartbtn);
        backBtn = findViewById(R.id.backBtn);
        erreurImage = findViewById(R.id.erreurImage);
        erreurTxt = findViewById(R.id.erreurTxt);
        cartTxt = findViewById(R.id.catTxt);
        productsTxt = findViewById(R.id.txtProducts);


        Bundle bundle = getIntent().getExtras();
        id_order = bundle.getInt("id_order",0);

        recyclerViewCategories();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchFragment fragment = new SearchFragment();
                Bundle searchViewBundle = new Bundle();
                searchViewBundle.putString("SearchText", newText);
                fragment.setArguments(searchViewBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragment,fragment).addToBackStack(null).commit();
                return false;
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(NewOrderActivity.this,CartListActivity.class);
                bundle.putInt("id_order", id_order);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewOrderActivity.this,SecondActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
    }

    private void recyclerViewCategories() {
        ArrayList<Categorie> ListCategories = new ArrayList<>();
        ListCategories =dbHelper.displayCategorie();

        if (ListCategories.isEmpty())
        {
            cartTxt.setVisibility(View.INVISIBLE);
            productsTxt.setVisibility(View.INVISIBLE);
            erreurTxt.setVisibility(View.VISIBLE);
            erreurImage.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategories=findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(linearLayoutManager);

        adapter = new CategoryAdapter(ListCategories,id_order);
        recyclerViewCategories.setAdapter(adapter);
    }
}


