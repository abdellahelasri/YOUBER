package com.example.youber.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.R;
import com.example.youber.domain.LigneCommande;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<LigneCommande> listProducts = new ArrayList<>();

    public CartAdapter(ArrayList<LigneCommande> listProducts )
    {
        this.listProducts = listProducts;
    }

    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new CartAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.nom.setText(listProducts.get(position).getNomProduit());
        holder.quantite.setText(String.valueOf(listProducts.get(position).getQuantite()));
        holder.prix.setText(String.valueOf(listProducts.get(position).getPrix()));
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom,prix,quantite;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nom = itemView.findViewById(R.id.productName);
            prix = itemView.findViewById(R.id.prixProduit);
            quantite = itemView.findViewById(R.id.productQuantity);
        }
    }


}
