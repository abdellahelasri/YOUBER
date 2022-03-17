package com.example.youber.domain;

import android.database.Cursor;

public class LigneCommande {
    int id;
    int idProduct;
    String nameProduct;
    Double priceItem;
    Double subTotal;
    int idOrder;
    int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(Double priceItem) {
        this.priceItem = priceItem;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public LigneCommande(int idProduit, String nomProduit, Double prix, int quantite)
    {
        this.idProduct =idProduit;
        this.nameProduct =nomProduit;
        this.priceItem =prix;
        this.quantity =quantite;
    }

    public LigneCommande (Cursor cursor)
    {
        id = cursor.getInt(cursor.getColumnIndexOrThrow("idLigne_commande"));
        idProduct = cursor.getInt(cursor.getColumnIndexOrThrow("idProduit"));
        quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantite_commande"));
        idOrder = cursor.getInt(cursor.getColumnIndexOrThrow("idCommande"));
        nameProduct = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        priceItem = cursor.getDouble(cursor.getColumnIndexOrThrow("prixUnitaire"));
        subTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("SousTotal"));
    }
}
