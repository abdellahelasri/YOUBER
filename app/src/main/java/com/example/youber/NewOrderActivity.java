package com.example.youber;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.adapter.CategoryAdapter;
import com.example.youber.domain.Categorie;
import com.example.youber.fragments.DetailsFragment;
import com.example.youber.fragments.SearchFragment;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class NewOrderActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategories;
    SearchView searchView;
    DatabaseHelper dbHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordering_activity);
        searchView = findViewById(R.id.searchProduct);
        recyclerViewCategorie();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                SearchFragment fragment = new SearchFragment();
                Bundle searchViewBundle = new Bundle();
                searchViewBundle.putString("SearchText", newText);
                fragment.setArguments(searchViewBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragment,fragment).addToBackStack(null).commit();
                return false;
            }
        });
    }


    private void recyclerViewCategorie() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategories=findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(linearLayoutManager);
        ArrayList<Categorie> ListCategories = new ArrayList<>();

        ListCategories =dbHelper.displayCategorie();

        adapter = new CategoryAdapter(ListCategories);
        recyclerViewCategories.setAdapter(adapter);
    }

}


