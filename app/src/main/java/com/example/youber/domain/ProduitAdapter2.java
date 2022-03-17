package com.example.youber.domain;

import static java.util.ResourceBundle.getBundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.R;

import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class ProduitAdapter2 extends RecyclerView.Adapter<ProduitAdapter2.ViewHolder> {
    ArrayList<Produit> foodDomains;
    DatabaseHelper dbHelper;
    private Activity context;

    public ProduitAdapter2(ArrayList<Produit> FoodDomains, Activity context) {
        this.foodDomains = FoodDomains;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.title.setText(foodDomains.get(holder.getAdapterPosition()).getTitre());
        holder.fee.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getPrix()));
        holder.quantite.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getQantite()));

        if (String.valueOf(foodDomains.get(holder.getAdapterPosition()).getStockable()).equals("false"))
        {
            holder.quantite.setVisibility(View.INVISIBLE);
            holder.quantite_.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, fee, quantite, quantite_;
        ImageView pic;
        Button update, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView7);
            fee = itemView.findViewById(R.id._price);
            quantite = itemView.findViewById(R.id.idquand);
            quantite_ = itemView.findViewById(R.id.textViewQuantity);
            pic = itemView.findViewById(R.id.pic);
            update = itemView.findViewById(R.id.editProduct);
            delete = itemView.findViewById(R.id.deleteProduct);
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
                bundle.putString("categorie", String.valueOf(foodDomains.get(getAdapterPosition()).getIdcat()));
                bundle.putString("stockable", String.valueOf(foodDomains.get(getAdapterPosition()).getStockable()));
                bundle.putString("quantite", String.valueOf(foodDomains.get(getAdapterPosition()).getQantite()));
                bundle.putString("prix", String.valueOf(foodDomains.get(getAdapterPosition()).getPrix()));
                i.putExtras(bundle);

                context.startActivity(i);
                context.finish();

            } else if(v.getId() == delete.getId()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.ProductDelete);
                alert.setMessage(R.string.ProductDelete_message);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dbHelper.deleteProduit(foodDomains.get(getAdapterPosition()).getId());
                        Intent i = new Intent(context, ManageProduit.class);
                        context.startActivity(i);
                        context.overridePendingTransition(0,0);
                        context.finish();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        }
    }
}


