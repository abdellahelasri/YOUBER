package com.example.youber.adapter;


import static java.util.ResourceBundle.getBundle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.youber.Client.Client;
import com.example.youber.Client.Client_full_display;
import com.example.youber.R;
import com.example.youber.Client.Client;
import com.example.youber.Client.Client_full_display;
import com.example.youber.Client.UpdateClientActivity;
import com.example.youber.domain.Produit;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    ArrayList<Client> clientList;
    ArrayList<Produit> listPanier ;
   Context context ;

    private DatabaseHelper dbHelper;

    public ClientAdapter(ArrayList<Client> clientList, Context context ) {
        this.clientList = clientList;
        this.context = context;

    }


    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_client, parent, false);

        return new ClientAdapter.ViewHolder(inflate);
    }

    public void filterListClient(ArrayList<Client> listClients)
    {
        clientList = listClients;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewHolder holder, int position) {
        holder.name.setText(clientList.get(holder.getAdapterPosition()).getNom());
        holder.prename.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getPrenom()));
       // Client client = clientList.get(holder.getAdapterPosition());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier("user_icon", "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        //--------------------------------------------------------------//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, clientList.get(holder.getAdapterPosition()).getPrenom(), Toast.LENGTH_SHORT).show();

                // on below line we are calling an intent.
             Intent i = new Intent(context, Client_full_display.class);

                /* below we are passing all our values.
                i.putExtra("idclient", clientList.get(holder.getAdapterPosition()).getIdclient());
                i.putExtra("name", clientList.get(holder.getAdapterPosition()).getNom());
                i.putExtra("prename", clientList.get(holder.getAdapterPosition()).getPrenom());
                i.putExtra("telphone", clientList.get(holder.getAdapterPosition()).getTelephone());
                i.putExtra("adressePostale", clientList.get(holder.getAdapterPosition()).getAdresse());
*/
                Bundle bundle = new Bundle();

                bundle.putInt("idclient", clientList.get(holder.getAdapterPosition()).getIdclient());
                bundle.putString("name", clientList.get(holder.getAdapterPosition()).getNom());
                bundle.putString("prename", clientList.get(holder.getAdapterPosition()).getPrenom());
                bundle.putString("telephone", clientList.get(holder.getAdapterPosition()).getTelephone());
                bundle.putString("adressePostale", clientList.get(holder.getAdapterPosition()).getAdresse());

                i.putExtras(bundle);

                context.startActivity(i);


            }
        });


    }



    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, prename;
        ImageView pic;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.adapterclientName);
            prename = itemView.findViewById(R.id.adapterclientPreName);
            pic = itemView.findViewById(R.id.clientPic);

        }


    }
}
