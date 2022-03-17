package com.example.youber.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.R;

import com.example.youber.domain.Produit;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.ViewHolder> {
    List<Produit> foodDomains;
    Context context ;
    DatabaseHelper db;
    int id_order;


    public ProduitAdapter(ArrayList<Produit> FoodDomains, Context context, int id) {
        this.foodDomains = FoodDomains;
        this.context=context;
        this.db=new DatabaseHelper(context);
        this.id_order = id;
    }

    public ProduitAdapter(ArrayList<Produit> FoodDomains, Context context) {
        this.foodDomains = FoodDomains;
        this.context=context;
        this.db=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produit, parent, false);
        return new ViewHolder(inflate);
    }

    public void filterListProduit(ArrayList<Produit> listProduits)
    {
        foodDomains= listProduits;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodDomains.get(position).getTitre());
        holder.fee.setText(String.valueOf(foodDomains.get(position).getPrix()));

        /*int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getLogo(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);*/

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> listproduits = new ArrayList();
                listproduits= db.isproductofCart(id_order);
                Integer quantite = foodDomains.get(holder.getAdapterPosition()).getQantite();
                Integer idproduit = foodDomains.get(holder.getAdapterPosition()).getId();
                String stockable = foodDomains.get(holder.getAdapterPosition()).getStockable();

                if (quantite==0 && stockable.equals("true")){
                    Toast.makeText(context, R.string.OutofStock, Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!listproduits.contains(idproduit)) {
                            db.AddtoCartLine(idproduit,id_order,
                                    foodDomains.get(holder.getAdapterPosition()).getTitre(),
                                    foodDomains.get(holder.getAdapterPosition()).getPrix(), 1);
                    } else {
                        Toast.makeText(context, R.string.AlreadyExisting, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
