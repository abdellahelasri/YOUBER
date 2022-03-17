package com.example.youber.adapter;

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

import com.example.youber.Client.Client;
import com.example.youber.Client.ManageClient;
import com.example.youber.Client.UpdateClientActivity;
import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    ArrayList<Client> clientList;
    Activity  context ;

    private DatabaseHelper dbHelper;

    public ClientAdapter(ArrayList<Client> clientList, Activity context ) {
        this.clientList = clientList;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
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
        holder.code.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getIdclient()));
        holder.nom.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getNom()));
        holder.prenom.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getPrenom()));
        holder.telephone.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getTelephone()));
        holder.addresse.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getAdresse()));
        holder.ville.setText(String.valueOf(clientList.get(holder.getAdapterPosition()).getVille()));
    }


    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nom, prenom, code, addresse, ville, telephone;
        Button update, delete;
        ImageView pic;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            code = itemView.findViewById(R.id.orderId);
            nom = itemView.findViewById(R.id.cityOrder2);
            prenom = itemView.findViewById(R.id.addressOrder2);
            telephone = itemView.findViewById(R.id.phoneOrder);
            addresse = itemView.findViewById(R.id.clientAdress);
            ville = itemView.findViewById(R.id.city);
            update = itemView.findViewById(R.id.editButton);
            delete = itemView.findViewById(R.id.deleteButton);
            //pic = itemView.findViewById(R.id.clientPic);

            update.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (v.getId() == update.getId()){
                Intent i = new Intent(context, UpdateClientActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("idclient", clientList.get(getAdapterPosition()).getIdclient());
                bundle.putString("nom", clientList.get(getAdapterPosition()).getNom());
                bundle.putString("prenom", clientList.get(getAdapterPosition()).getPrenom());
                bundle.putString("telephone", clientList.get(getAdapterPosition()).getTelephone());
                bundle.putString("adresse", clientList.get(getAdapterPosition()).getAdresse());
                bundle.putString("ville", clientList.get(getAdapterPosition()).getVille());
                i.putExtras(bundle);

                context.startActivity(i);
                context.finish();
            }
            else if(v.getId() == delete.getId()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.ClientDelete);
                alert.setMessage(R.string.ClientDelete_message);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteClient(clientList.get(getAdapterPosition()).getIdclient());
                        Intent i = new Intent(context, ManageClient.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("commande", false);
                        i.putExtras(bundle);
                        context.startActivity(i);
                        context.overridePendingTransition(0,0);
                        context.finish();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        }
    }
}
