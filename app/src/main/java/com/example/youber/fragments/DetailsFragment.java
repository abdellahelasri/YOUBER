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

public class DetailsFragment extends Fragment {

    ArrayList<Produit> listeProduits = new ArrayList<>();
    DatabaseHelper DBhelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DBhelper = new DatabaseHelper(this.getContext());
        RecyclerView rv = new RecyclerView(this.getContext());

        /**
         * Display the products of selected category
         */
        Bundle catClickBundle = getArguments();

        if (catClickBundle == null)
        {
            Toast.makeText(getContext(),"Erreur id_categorie", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int id_cat= catClickBundle.getInt("id_categorie");
            listeProduits = DBhelper.displayCatProducts(id_cat);
        }


        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(new ProduitAdapter(listeProduits,getContext()));
        return rv;
    }
}
