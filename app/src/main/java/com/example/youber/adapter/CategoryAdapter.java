package com.example.youber.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.fragments.DetailsFragment;
import com.example.youber.R;
import com.example.youber.domain.Categorie;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<Categorie> categoriesMenu;
    int id_order;

    public CategoryAdapter(ArrayList<Categorie> categoriesMenu,int id_order) {
        this.categoriesMenu = categoriesMenu;
        this.id_order = id_order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorie, parent,false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoriesMenu.get(position).getTitre());
        String picUrl = "";
        switch (position) {
            case 0: {
                picUrl = "ic_pizza";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 1: {
                picUrl = "ic_burgers";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 2: {
                picUrl = "ic_sandwichs";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 3: {
                picUrl = "ic_pasta";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 4: {
                picUrl = "ic_salads";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 5: {
                picUrl = "ic_cans";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 6: {
                picUrl = "ic_bottles";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
            case 7: {
                picUrl = "ic_wines";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background));
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);

        /*
         Gestion du click sur le button detailsButton
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager;
            @Override
            public void onClick(View v) {
                DetailsFragment fragment = new DetailsFragment();
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
                int id_cat =  categoriesMenu.get(holder.getAdapterPosition()).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id_categorie", id_cat);
                bundle.putInt("id_order", id_order);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.detailsFragment,fragment).addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoriesMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(View itemView)
         {
         super(itemView);
         categoryName = itemView.findViewById(R.id.categoryName);
         categoryPic = itemView.findViewById(R.id.categoryPic);
         mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }
}
