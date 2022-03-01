package com.example.youber.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import com.example.youber.Client.Client;
import com.example.youber.domain.Categorie;
import com.example.youber.domain.LigneCommande;
import com.example.youber.domain.Produit;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "youber.sql";
    private static int DB_VERSION = 1;

    //Tables de base de données
    private static final String TABLE_PRODOUITS = "produit";
    private static final String TABLE_CATEGORIE = "categorie";
    private static final String TABLE_ORDERLINE = "ligne_commande";
    private static final String TABLE_CLIENT = "client";


    private  static  final String ID_COL = "id";
    private static final String NOM_CAT = "nom";

    // colonnes pour produits
    private  static  final String IDP_COL = "idproduit";
    private  static  final String  IDCAT_COL = "idcat" ;
    private static  final  String  NOM_COL = "nom";
    private static  final  String  QUANTITE_COL = "quantite_stock" ;
    private static  final  String  PRIX_COL = "prix" ;

    //colonnes pour ligne de commandes
    private  static  final String ID_ORDERLINE = "idLigne_commande";
    private  static  final String  ID_PRODUCT = "idProduit" ;
    private  static  final String  NOM_PRODUIT = "nom" ;
    private  static  final String  PRIX = "prix" ;
    private  static  final String  ID_COMMANDE = "idCommande";
    private  static  final String  ORDERED_QUANTITY = "quantite_commande" ;

    // colonnes pour la table client
    private  static  final String IDCLIENT_COL = "idclient";
    private  static  final String PRENOM_COL = "prenom";
    private  static  final String  TELEPHONE_COL = "telephone" ;
    private static  final  String  ADRESSE_COL = "adresse";


    public DatabaseHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String cat = "CREATE TABLE "
                +TABLE_CATEGORIE
                +"("
                +ID_COL
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NOM_CAT
                +" TEXT )";

        String product= "CREATE TABLE "
                +TABLE_PRODOUITS
                +"("
                +IDP_COL
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +IDCAT_COL
                +" INTEGER,"
                + NOM_COL
                +" TEXT,"
                +QUANTITE_COL
                +" INTEGER,"
                +PRIX_COL
                +" FLOAT," +
                "FOREIGN KEY("+IDCAT_COL+")REFERENCES "+TABLE_CATEGORIE+"("+ID_COL+"))";

        String Client = "CREATE TABLE client ("
                +"idclient INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"nom	TEXT,"
                +"prenom	TEXT,"
                +"telephone	TEXT,"
                +"adresse	text)";

        db.execSQL(cat);
        db.execSQL(Client);
        db.execSQL(product);
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

    public void AddtoCartLine(int idProduit,String nomProduit, Double prix,int quantite)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_PRODUCT,idProduit);
        values.put(NOM_PRODUIT, nomProduit);
        values.put(PRIX, prix);
        values.put(ORDERED_QUANTITY,quantite);
        System.out.println("Abdellah a inseré quelque chose");
        db.insert(TABLE_ORDERLINE,null, values);
    }


    public ArrayList<Produit> displayAllProducts() {
        ArrayList<Produit> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false, TABLE_PRODOUITS, new String[]{IDP_COL,NOM_COL, PRIX_COL,IDCAT_COL, QUANTITE_COL},null,
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
        Cursor cursor = db.query(true, TABLE_PRODOUITS, new String[]{IDP_COL,NOM_COL, PRIX_COL,IDCAT_COL, QUANTITE_COL}, IDCAT_COL+"="+id_cat_,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new Produit(cursor));
        }

        cursor.close();
        db.close();

        return listProducts;
    }

    public ArrayList<LigneCommande> displayCartProducts() {
        ArrayList<LigneCommande> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_ORDERLINE, new String[]{ID_ORDERLINE,NOM_PRODUIT, PRIX,ORDERED_QUANTITY,ID_COMMANDE},ID_COMMANDE+" is NULL",
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new LigneCommande(cursor));
        }

        cursor.close();
        db.close();

        return listProducts;
    }

    public ArrayList<Integer> productofCart() {
        ArrayList<Integer> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_ORDERLINE, new String[]{"idProduit"},ID_COMMANDE+" is NULL",
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            listProducts.add(cursor.getInt(cursor.getColumnIndexOrThrow("idProduit")));
        }
        cursor.close();
        db.close();
        return listProducts;
    }

    public void updateCartQuantity(int idproduit, int quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDERED_QUANTITY,quantity);
    }

    /* Les clients */
    public ArrayList<Client> displayAllClient() {
        ArrayList<Client> listClients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_CLIENT, new String[]{IDCLIENT_COL, NOM_COL,PRENOM_COL, TELEPHONE_COL, ADRESSE_COL}, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            listClients.add(new Client(cursor));
        }
        cursor.close();
        db.close();

        return listClients;
    }
    public void addClient(String nom, String prenom, String telephone, String adresse) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOM_COL, nom);
        values.put(PRENOM_COL, prenom);
        values.put(TELEPHONE_COL, telephone);
        values.put(ADRESSE_COL, adresse);

        db.insert(TABLE_CLIENT, null, values);

        db.close();
    }

    public void updateClient(String nomOriginal, String nom, String prenom,
                             String telephone, String adresse_postale) {

        // calling a method to get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM_COL, nom);
        values.put(PRENOM_COL, prenom);
        values.put(TELEPHONE_COL, telephone);
        values.put(ADRESSE_COL, adresse_postale);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_CLIENT, values, "nom=?",new String[]{nomOriginal});
        db.close();
    }
    public void deleteClient(int idClient) {
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_CLIENT, "idclient=?", new String[]{String.valueOf(idClient)});
        db.close();
    }


    // Manage produit
    public void addProduit(String nom, int idcat, int quantite, Double prix) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IDCAT_COL, idcat);
        values.put(NOM_COL, nom);
        values.put(QUANTITE_COL, quantite);
        values.put(PRIX_COL, prix);

        db.insert(TABLE_PRODOUITS, null, values);

        db.close();
    }
    public void updateProduit(int idProduit, String nom, int idcat, int quantite, Double prix) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IDCAT_COL, idcat);
        values.put(NOM_COL, nom);
        values.put(QUANTITE_COL, quantite);
        values.put(PRIX_COL, prix);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_PRODOUITS, values, "idproduit=?",new String[]{String.valueOf(idProduit)});
        db.close();
    }

    public void deleteProduit(int idProduit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODOUITS, "idproduit=?", new String[]{String.valueOf(idProduit)});
        db.close();
    }


}

