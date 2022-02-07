package com.example.youber.domain;

import android.database.Cursor;

import java.io.Serializable;

public class Produit implements Serializable {
    private int idcat;
    private String nom;
    private String logo;
    private Double prix;
    private int id;
    private int qantite;

    public Produit (Cursor cursor)
    {
        idcat = cursor.getInt(cursor.getColumnIndexOrThrow("idcat"));
        nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        prix = cursor.getDouble(cursor.getColumnIndexOrThrow("prix"));
        qantite = cursor.getInt(cursor.getColumnIndexOrThrow("quantite_stock"));
    }

    public String getTitre() {
        return nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        logo = logo;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQantite() {
        return qantite;
    }

    public void setQantite(int qantite) {
        this.qantite = qantite;
    }
}
