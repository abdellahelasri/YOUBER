package com.example.youber.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.CartListActivity;
import com.example.youber.R;
import com.example.youber.domain.LigneCommande;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<LigneCommande> listProducts = new ArrayList<>();
    DatabaseHelper dbHelper;
    TextView totalInAdapter;
    Activity context;

    public CartAdapter(ArrayList<LigneCommande> listProducts, Activity context)
    {
        this.listProducts = listProducts;
        this.context = context;
        totalInAdapter= context.findViewById(R.id.totalTxt);
        dbHelper = new DatabaseHelper(context);
    }

    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new CartAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.name.setText(listProducts.get(position).getNameProduct());
        holder.quantity.setText(String.valueOf(listProducts.get(position).getQuantity()));
        holder.subTotal.setText(String.valueOf(listProducts.get(position).getSubTotal()));
        setTotal();
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, quantity, subTotal;
        Button deleteItem, plus, minus;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.orderlineQuantity);
            subTotal = itemView.findViewById(R.id.subTotal);

            deleteItem = itemView.findViewById(R.id.removeItem);
            plus = itemView.findViewById(R.id.plusCardBtn);
            minus = itemView.findViewById(R.id.minusCardBtn);

            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
            deleteItem.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (v.getId()==deleteItem.getId()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.ProductDelete);
                alert.setMessage(R.string.ProductDelete_message);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteOrderLine(listProducts.get(getAdapterPosition()).getId());
                        Intent i = new Intent(context, CartListActivity.class);
                        context.startActivity(i);
                        context.overridePendingTransition(0, 0);
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
            int idProduit = listProducts.get(getAdapterPosition()).getIdProduct();
            String stockable = dbHelper.getStockable(idProduit);
            Double priceItem = listProducts.get(getAdapterPosition()).getPriceItem();
            int quantitecommande = Integer.parseInt((String) quantity.getText());
            int quantiteStock = dbHelper.getQauntiteStock(idProduit);

            if(v.getId()==plus.getId())
            {
                if(stockable.equals("false"))
                {
                    dbHelper.updateCartQuantity(idProduit,true);
                    quantity.setText(String.valueOf(quantitecommande+1));
                    subTotal.setText(String.valueOf((quantitecommande+1)*priceItem));
                    setTotal();
                }
                else if (stockable.equals("true"))
                {
                    if(quantiteStock-quantitecommande <= 0)
                    {
                        Toast.makeText(context, R.string.OutofStock, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dbHelper.updateCartQuantity(idProduit,true);
                        quantity.setText(String.valueOf(quantitecommande+1));
                        subTotal.setText(String.valueOf((quantitecommande+1)*priceItem));
                        setTotal();
                    }
                }
            }
            else if (v.getId()==minus.getId())
            {
                if (quantitecommande>1) {
                    dbHelper.updateCartQuantity(idProduit, false);
                    quantity.setText(String.valueOf(quantitecommande - 1));
                    subTotal.setText(String.valueOf((quantitecommande - 1)*priceItem));
                    setTotal();
                }
            }
        }
    }

    private void setTotal()
    {
        Double total = dbHelper.getCartTotal();
        totalInAdapter.setText(String.valueOf(total));
    }
}
