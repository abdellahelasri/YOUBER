package com.example.youber.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.youber.SecondActivity;
import com.example.youber.domain.Categorie;
import com.example.youber.domain.Produit;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "youber.sql";
    private static int DB_VERSION = 1;

    //Tables de base de données
    private static final String TABLE_PRODOUITS = "produit";
    private static final String TABLE_CATEGORIE = "categorie";

    private static final String NOM_CAT = "nom";


    // colonnes pour produits
    private  static  final String ID_COL = "id";
    private  static  final String  IDCAT_COL = "idcat" ;
    private static  final  String  NOM_COL = "nom";
    private static  final  String  QUANTITE_COL = "quantite_stock" ;
    private static  final  String  PRIX_COL = "prix" ;

    public DatabaseHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        A saisir pour la création de tables
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Les méthodes
     */

    public void addCategorie(String nom) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOM_CAT, nom);
        values.put(NOM_COL, nom);

        db.insert(TABLE_CATEGORIE, null, values);

        db.close();
    }

    public ArrayList<Categorie> displayCategorie() {
        ArrayList<Categorie> listCategorie = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_CATEGORIE, new String[]{ID_COL,NOM_CAT}, null,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listCategorie.add(new Categorie(cursor));
        }

        cursor.close();
        db.close();

        return listCategorie;
    }


    public ArrayList<Produit> displayAllProducts() {
        ArrayList<Produit> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_PRODOUITS, new String[]{NOM_COL, PRIX_COL,IDCAT_COL, QUANTITE_COL},null,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new Produit(cursor));
        }

        cursor.close();
        db.close();

        return listProducts;
    }

    /**
     * Displays the products of a category
     * @param id_cat_
     * @return listProducts:  the list of the categorie of wich id = id_cat_
     */
    public ArrayList<Produit> displayCatProducts(int id_cat_) {
        ArrayList<Produit> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_PRODOUITS, new String[]{NOM_COL, PRIX_COL,IDCAT_COL, QUANTITE_COL}, IDCAT_COL+"="+id_cat_,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new Produit(cursor));
        }

        cursor.close();
        db.close();

        return listProducts;
    }

}

