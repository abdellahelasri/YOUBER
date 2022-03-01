package com.example.youber.domain;

import android.database.Cursor;

public class LigneCommande {
    int id;
    int idProduit;
    String nomProduit;
    Double prix;
    int idCommande;
    int quantite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LigneCommande(int idProduit, String nomProduit, Double prix, int quantite)
    {
        this.idProduit=idProduit;
        this.nomProduit=nomProduit;
        this.prix=prix;
        this.quantite=quantite;
    }

    public LigneCommande (Cursor cursor)
    {
        id = cursor.getInt(cursor.getColumnIndexOrThrow("idLigne_commande"));
        //idProduit = cursor.getInt(cursor.getColumnIndexOrThrow("idProduit"));
        quantite = cursor.getInt(cursor.getColumnIndexOrThrow("quantite_commande"));
        idCommande = cursor.getInt(cursor.getColumnIndexOrThrow("idCommande"));
        nomProduit = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        prix = cursor.getDouble(cursor.getColumnIndexOrThrow("prix"));
    }
}
