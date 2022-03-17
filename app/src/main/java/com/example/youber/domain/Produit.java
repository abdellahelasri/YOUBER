package com.example.youber.domain;

import android.database.Cursor;

import java.io.Serializable;

public class Produit  {
    private int idcat;
    private String nom;
    private String logo;
    private Double prix;
    private int id;
    private String stockable;
    private int qantite;

    private  static  final String IDP_COL = "idproduit";
    private  static  final String  IDCAT_COL = "idcat" ;
    private static  final  String  NOM_COL = "nom";
    private static  final  String  QUANTITE_COL = "quantite_stock" ;
    private static  final  String  STOCKABLE = "stockable";
    private static  final  String  PRIX_COL = "prix" ;

    public Produit (Cursor cursor)
    {
        id = cursor.getInt(cursor.getColumnIndexOrThrow(IDP_COL));
        idcat = cursor.getInt(cursor.getColumnIndexOrThrow(IDCAT_COL));
        nom = cursor.getString(cursor.getColumnIndexOrThrow(NOM_COL));
        prix = cursor.getDouble(cursor.getColumnIndexOrThrow(PRIX_COL));
        qantite = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITE_COL));
        stockable = cursor.getString(cursor.getColumnIndexOrThrow(STOCKABLE));
    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
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

    public String getStockable() { return stockable;  }

    public void setStockable(String stockable) { this.stockable = stockable; }
}
