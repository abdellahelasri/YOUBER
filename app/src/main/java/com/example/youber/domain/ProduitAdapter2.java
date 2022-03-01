package com.example.youber.domain;

import static android.content.Intent.getIntent;
import static java.util.ResourceBundle.getBundle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.LoginActivity;
import com.example.youber.R;
import com.example.youber.Client.Client_full_display;
import com.example.youber.Client.ManageClient;
import com.example.youber.domain.ManageProduit;
import com.example.youber.domain.Produit;
import com.example.youber.domain.UpdateProduitActivity;

import com.example.youber.helper.DatabaseHelper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProduitAdapter2 extends RecyclerView.Adapter<ProduitAdapter2.ViewHolder> {
    ArrayList<Produit> foodDomains;
    ArrayList<Produit> listPanier;
    DatabaseHelper dbHelper;
    private Context context;

    public ProduitAdapter2(ArrayList<Produit> FoodDomains, Context context) {
        this.foodDomains = FoodDomains;
        // this.listPanier = new ArrayList<>();
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produit2, parent, false);

        return new ViewHolder(inflate);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodDomains.get(holder.getAdapterPosition()).getTitre());
        holder.fee.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getPrix()));
        holder.quantite.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getQantite()));

/*
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, UpdateProduitActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("idproduit", String.valueOf(foodDomains.get(holder.getAdapterPosition()).getId()));
                bundle.putString("nom", foodDomains.get(holder.getAdapterPosition()).getNom());
                bundle.putString("categorie", String.valueOf(foodDomains.get(holder.getAdapterPosition()).getIdcat()));
                bundle.putString("quantite", String.valueOf(foodDomains.get(holder.getAdapterPosition()).getQantite()));
                bundle.putString("prix", String.valueOf(foodDomains.get(holder.getAdapterPosition()).getPrix()));
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
           /* holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(Client_full_display.this);
                    alert.setTitle("Suppression de client");
                    alert.setMessage("Etes vous sur de vouloir supprimer ce client ?");
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dbHelper.deleteClient(idClient);
                            Intent i = new Intent(Client_full_display.this, ManageClient.class);
                            startActivity(i);
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close dialog
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
                }
            });  */
    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, fee, quantite;
        ImageView pic;
        Button update, delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView7);
            fee = itemView.findViewById(R.id._price);
            quantite = itemView.findViewById(R.id.idquand);
            pic = itemView.findViewById(R.id.pic);
            update = itemView.findViewById(R.id.button33);
            delete = itemView.findViewById(R.id.button22);
            update.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == update.getId()){
                Intent i = new Intent(context, UpdateProduitActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("idproduit", String.valueOf(foodDomains.get(getAdapterPosition()).getId()));
                bundle.putString("nom", foodDomains.get(getAdapterPosition()).getTitre());
                bundle.putString("categorie", String.valueOf(foodDomains.get(getAdapterPosition()).getId()));
                bundle.putString("quantite", String.valueOf(foodDomains.get(getAdapterPosition()).getQantite()));
                bundle.putString("prix", String.valueOf(foodDomains.get(getAdapterPosition()).getPrix()));
                i.putExtras(bundle);

                context.startActivity(i);

            } else if(v.getId() == delete.getId()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Suppression de produit");
                alert.setMessage("Etes vous sur de vouloir supprimer ce produit ?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dbHelper.deleteProduit(foodDomains.get(getAdapterPosition()).getId());
                        Intent i = new Intent(context, ManageProduit.class);
                        context.startActivity(i);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        }
    }
}


