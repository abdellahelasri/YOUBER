package com.example.youber.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.adapter.ProduitAdapter;
import com.example.youber.domain.Produit;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ArrayList<Produit> listeProduits = new ArrayList<>();
    DatabaseHelper DBhelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DBhelper = new DatabaseHelper(this.getContext());
        listeProduits = DBhelper.displayAllProducts();

        /**
         * Display the products searched in the search bar
         */
        Bundle searchViewBundle = getArguments();

        if (searchViewBundle != null)
        {
            String searchText= searchViewBundle.getString("SearchText");
            System.out.println(searchText);
            ArrayList <Produit> listPro =  new ArrayList<>();
            for (Produit item : listeProduits) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getTitre().toLowerCase().contains(searchText.toLowerCase())) {
                    listPro.add(item);
                }
            }
            listeProduits = listPro;
        }
        /*else {
            String searchText= searchViewBundle.getString("SearchText");
            ArrayList <Produit> listPro =  new ArrayList<>();
            for (Produit item : listeProduits) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getTitre().toLowerCase().contains(searchText.toLowerCase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    listPro.add(item);
                }
            }
            /*if (listeProduits.isEmpty()) {
                // if no item is added in filtered list we are
                // displaying a toast message as no data found.
                Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
                return listPro;
            } else {
                // at last we are passing that filtered
                // list to our adapter class.
                return  listPro;
            }*
            listeProduits = listPro;
        }*/

        RecyclerView rv = new RecyclerView(this.getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(new ProduitAdapter(listeProduits,getContext()));
        return rv;
    }

}
