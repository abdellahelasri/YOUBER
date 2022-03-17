package com.example.youber.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

import com.example.youber.Client.Client;
import com.example.youber.R;
import com.example.youber.domain.Categorie;
import com.example.youber.domain.Commande;
import com.example.youber.domain.LigneCommande;
import com.example.youber.domain.Produit;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "youber.sql";
    private static final int DB_VERSION = 1;

    //Tables de base de données
    public static final String TABLE_PRODUCT = "produit";
    public static final String TABLE_CATEGORY = "categorie";
    public static final String TABLE_ORDERLINE = "ligne_commande";
    public static final String TABLE_ORDER = "commande";
    public static final String TABLE_CLIENT = "client";


    public  static  final String ID_COL = "id";
    public static final String NOM_CAT = "nom";

    // colonnes pour produits
    public  static  final String IDP_COL = "idproduit";
    public  static  final String  IDCAT_COL = "idcat" ;
    public static  final  String  NOM_COL = "nom";
    public static  final  String  QUANTITE_COL = "quantite_stock" ;
    public static  final  String  STOCKABLE = "stockable" ;
    public static  final  String PRICE_COL = "prix" ;

    // colonnes pour la table client
    public  static  final String IDCLIENT = "idclient";
    public  static  final String NOM_CLIENT = "nom";
    public  static  final String PRENOM_CLIENT = "prenom";
    public  static  final String TELEPHONE_CLIENT = "telephone" ;
    public static  final  String ADRESSE_CLIENT = "adresse";
    public static  final  String VILLE = "ville";

    //colonnes pour ligne de commandes
    public  static  final String ID_ORDERLINE = "idLigne_commande";
    public  static  final String  ID_PRODUCT = "idProduit" ;
    public  static  final String NAME_PRODUCT = "nom" ;
    public  static  final String PRICE_ITEM = "prixUnitaire" ;
    public  static  final String SUBTOTAL = "SousTotal" ;
    public  static  final String ID_ORDER = "idCommande";
    public  static  final String  ORDERED_QUANTITY = "quantite_commande" ;

    //colonnes pour commandes
    public  static  final String ORDER_STATUS = "statutCommande";
    public  static  final String CASH = "espece";
    public  static  final String CB = "carteBancaire";
    public  static  final String OTHER = "autre";
    public  static  final String ORDERDATE = "dateCommande";
    public  static  final String ORDERTIME = "heureCommande";
    public  static  final String DELIVERYDATE = "dateLivraison";
    public  static  final String DELIVERYTIME = "heureLivraison";
    public  static  final String PAYMENT_STATUS = "statutPaiement";
    public  static  final String ORDER_TOTAL = "totalCommande";
    public  static  final String ORDER_TYPE = "typeCommande";


    public DatabaseHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cat = "CREATE TABLE "
                + TABLE_CATEGORY
                +"("
                +ID_COL
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NOM_CAT
                +" TEXT )";

        String product= "CREATE TABLE "
                + TABLE_PRODUCT
                +"("
                +IDP_COL
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +IDCAT_COL
                +" INTEGER,"
                + NOM_COL
                +" TEXT,"
                +QUANTITE_COL
                +" INTEGER,"
                +STOCKABLE
                +" TEXT,"
                + PRICE_COL
                +" FLOAT," +
                "FOREIGN KEY("+IDCAT_COL+")REFERENCES "+ TABLE_CATEGORY +"("+ID_COL+"))";

        String Client = "CREATE TABLE "+TABLE_CLIENT+" ("
                +IDCLIENT
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NOM_CLIENT
                + " TEXT,"
                +PRENOM_CLIENT
                + " TEXT,"
                +TELEPHONE_CLIENT
                + " TEXT,"
                +ADRESSE_CLIENT
                + " TEXT,"
                +VILLE
                + " TEXT)";

        String Commande = "CREATE TABLE "+TABLE_ORDER+"("
                +ID_ORDER+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +IDCLIENT+" INTEGER,"
                +ORDER_TYPE +" TEXT,"
                +ORDER_STATUS+" TEXT,"
                +CASH +" REAL,"
                +CB +" REAL,"
                +OTHER +" REAL,"
                +PAYMENT_STATUS +" TEXT,"
                +ORDER_TOTAL+" REAL,"
                +ORDERDATE +" TEXT,"
                +ORDERTIME+" TEXT,"
                +DELIVERYDATE+" TEXT,"
                +DELIVERYTIME+" TEXT,"
                +"FOREIGN KEY("+ IDCLIENT +") REFERENCES "+TABLE_CLIENT+"("+IDCLIENT+"))";

        String LigneCommande = "CREATE TABLE "+TABLE_ORDERLINE+"("
                +ID_ORDERLINE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ID_PRODUCT+" INTEGER,"
                +NOM_COL+" TEXT,"
                +ORDERED_QUANTITY+" INTEGER,"
                +ID_ORDER +" INTEGER,"
                + PRICE_ITEM +" REAL,"
                + SUBTOTAL + " REAL,"
                +"FOREIGN KEY("+ ID_ORDER +") REFERENCES "+TABLE_ORDER+"("+ID_ORDER+"),"
                +"FOREIGN KEY("+ID_PRODUCT+") REFERENCES "+TABLE_PRODUCT+"("+IDP_COL+"))";

        db.execSQL(cat);
        db.execSQL(Client);
        db.execSQL(product);
        db.execSQL(Commande);
        db.execSQL(LigneCommande);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddtoCartLine(int idProduit,int idOrder,String nomProduit, Double prix,int quantite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(idOrder!=0)
            values.put(ID_ORDER,idOrder);
        values.put(ID_PRODUCT,idProduit);
        values.put(NAME_PRODUCT, nomProduit);
        values.put(PRICE_ITEM, prix);
        values.put(ORDERED_QUANTITY,quantite);
        values.put(SUBTOTAL,prix);
        db.insert(TABLE_ORDERLINE,null, values);
        db.close();
    }

    public void deleteOrderLine(int idOrderLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ORDERLINE, ID_ORDERLINE+"=?", new String[]{String.valueOf(idOrderLine)});
        db.close();
    }

    public double getCartTotal()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+"SUM("+SUBTOTAL+")"+" FROM "+TABLE_ORDERLINE+" WHERE "+ID_ORDER+" IS NULL",new String[]{});

        double total=0.0;
        while (cursor.moveToNext()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("SUM("+SUBTOTAL+")"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public void updateCartQuantity(int idproduit, Boolean ajout) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (ajout){
            db.execSQL("UPDATE " + TABLE_ORDERLINE + " SET " + ORDERED_QUANTITY + "=" + ORDERED_QUANTITY + "+1"
                    + " WHERE " + ID_PRODUCT + "=?", new String[]{String.valueOf(idproduit)});
            db.execSQL("UPDATE " + TABLE_ORDERLINE + " SET " + SUBTOTAL + "=" + SUBTOTAL + "+" + PRICE_ITEM
                + " WHERE " + ID_PRODUCT + "=?", new String[]{String.valueOf(idproduit)});
         }
        else
        {
            db.execSQL("UPDATE "+TABLE_ORDERLINE+" SET "+ORDERED_QUANTITY+"="+ORDERED_QUANTITY+"-1"
                    +" WHERE "+ID_PRODUCT+"=?",new String[]{String.valueOf(idproduit)});
            db.execSQL("UPDATE "+TABLE_ORDERLINE+" SET "+SUBTOTAL+"="+SUBTOTAL+"-"+PRICE_ITEM
                    +" WHERE "+ID_PRODUCT+"=?",new String[]{String.valueOf(idproduit)});
        }
        db.close();
    }


    /*---------------------Manage categories---------------------*/

    public void addCategorie(String nom) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOM_COL, nom);

        db.insert(TABLE_CATEGORY, null, values);

        db.close();
    }

    public ArrayList<Categorie> displayCategorie() {
        ArrayList<Categorie> listCategorie = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_CATEGORY, new String[]{ID_COL,NOM_CAT}, null,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listCategorie.add(new Categorie(cursor));
        }

        cursor.close();
        db.close();

        return listCategorie;
    }

    public void deleteCategory(int idCategory) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CATEGORY, ID_COL+"=?", new String[]{String.valueOf(idCategory)});
        db.close();
    }

    public void updateCategory(int idCategory,String nom) {

        // calling a method to get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM_COL, nom);

        db.update(TABLE_CATEGORY, values, ID_COL+"=?",new String[]{String.valueOf(idCategory)});
        db.close();
    }

    public int countProducts(int idCategory){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT("+IDP_COL+") " +
                "FROM "+TABLE_CATEGORY+" JOIN "+TABLE_PRODUCT+" ON "+ ID_COL+"="+IDCAT_COL+
                " WHERE "+IDCAT_COL+" =? ", new String[]{String.valueOf(idCategory)});
        int count=0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow("COUNT("+IDP_COL+")"));
        }
        cursor.close();
        db.close();

        return count;
    }

    /*---------------------Manage clients------------------------*/
    public ArrayList<Client> displayAllClient() {
        ArrayList<Client> listClients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_CLIENT, new String[]{IDCLIENT, NOM_CLIENT, PRENOM_CLIENT, TELEPHONE_CLIENT, ADRESSE_CLIENT,VILLE}, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            listClients.add(new Client(cursor));
        }
        cursor.close();
        db.close();

        return listClients;
    }

    public void addClient(String nom, String prenom, String telephone, String adresse, String ville) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOM_COL, nom);
        values.put(PRENOM_CLIENT, prenom);
        values.put(TELEPHONE_CLIENT, telephone);
        values.put(ADRESSE_CLIENT, adresse);
        values.put(VILLE,ville);

        db.insert(TABLE_CLIENT, null, values);

        db.close();
    }

    public void updateClient(int idclient,String nom, String prenom, String telephone, String adresse_postale, String ville) {

        // calling a method to get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOM_COL, nom);
        values.put(PRENOM_CLIENT, prenom);
        values.put(TELEPHONE_CLIENT, telephone);
        values.put(ADRESSE_CLIENT, adresse_postale);
        values.put(VILLE, ville);

        db.update(TABLE_CLIENT, values, IDCLIENT+"=?",new String[]{String.valueOf(idclient)});
        db.close();
    }

    public void deleteClient(int idClient) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENT, "idclient=?", new String[]{String.valueOf(idClient)});
        db.close();
    }

    /*---------------------Manage products------------------------*/
    public void addProduit(String nom, int idcat, String quantite, String stockable, Double prix) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IDCAT_COL, idcat);
        values.put(NOM_COL, nom);
        values.put(QUANTITE_COL, quantite);
        values.put(STOCKABLE, stockable);
        values.put(PRICE_COL, prix);

        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void addProduit_(String nom, int idcat, String stockable, Double prix) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IDCAT_COL, idcat);
        values.put(NOM_COL, nom);
        values.put(STOCKABLE, stockable);
        values.put(PRICE_COL, prix);

        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void updateProduit(int idProduit, String nom, int idcat, int quantite, String stockable, Double prix) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IDCAT_COL, idcat);
        values.put(NOM_COL, nom);
        values.put(QUANTITE_COL, quantite);
        values.put(STOCKABLE, stockable);
        values.put(PRICE_COL, prix);

        db.update(TABLE_PRODUCT, values, "idproduit=?",new String[]{String.valueOf(idProduit)});
        db.close();
    }

    public int getQauntiteStock(int idProduit) {
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= db.rawQuery("Select "+QUANTITE_COL
                +" FROM "+TABLE_PRODUCT
                +" Where "+IDP_COL+"=?", new String[]{String.valueOf(idProduit)});
        int quantity=0;
        while (cursor.moveToNext()) {
            quantity=cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITE_COL));
        }
        cursor.close();
        db.close();

        return quantity;
    }

    public String getStockable(int idProduit) {
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= db.rawQuery("Select "+STOCKABLE
                +" FROM "+TABLE_PRODUCT
                +" Where "+IDP_COL+"=?", new String[]{String.valueOf(idProduit)});
        String stockable="true";
        while (cursor.moveToNext()) {
            stockable=cursor.getString(cursor.getColumnIndexOrThrow(STOCKABLE));
        }
        cursor.close();
        db.close();

        return stockable;
    }

    public void deleteProduit(int idProduit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, "idproduit=?", new String[]{String.valueOf(idProduit)});
        db.close();
    }


    /**
     * Displays the products of a category
     * @param id_cat_
     * @return listProducts:  the list of the categorie of wich id = id_cat_
     */
    public ArrayList<Produit> displayCatProducts(int id_cat_) {
        ArrayList<Produit> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_PRODUCT, new String[]{IDP_COL,NOM_COL, PRICE_COL,IDCAT_COL, QUANTITE_COL, STOCKABLE}, IDCAT_COL+"="+id_cat_,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new Produit(cursor));
        }
        cursor.close();
        db.close();

        return listProducts;
    }

    public ArrayList<Produit> displayAllProducts() {
        ArrayList<Produit> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false, TABLE_PRODUCT, new String[]{IDP_COL,NOM_COL, PRICE_COL,IDCAT_COL,STOCKABLE, QUANTITE_COL},null,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new Produit(cursor));
        }
        cursor.close();
        db.close();
        return listProducts;
    }

    public ArrayList<LigneCommande> displayCartProducts(int id_order) {
        ArrayList<LigneCommande> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (id_order==0)
         cursor = db.query(true, TABLE_ORDERLINE, new String[]{"*"}, ID_ORDER +" is NULL",
                null, null, null, null, null);
        else
            cursor = db.query(true, TABLE_ORDERLINE, new String[]{"*"}, ID_ORDER +"="+id_order,
                    null, null, null, null, null);

        while (cursor.moveToNext()) {
            listProducts.add(new LigneCommande(cursor));
        }

        cursor.close();
        db.close();

        return listProducts;
    }

    public ArrayList<Integer> isproductofCart(int id_order) {
        ArrayList<Integer> listProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (id_order == 0)
             cursor = db.query(true, TABLE_ORDERLINE, new String[]{ID_PRODUCT}, ID_ORDER +" is NULL",
                null, null, null, null, null);
        else
             cursor = db.query(true, TABLE_ORDERLINE, new String[]{ID_PRODUCT}, ID_ORDER +"="+id_order,
                    null, null, null, null, null);
        while (cursor.moveToNext()) {
            listProducts.add(cursor.getInt(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
        }
        cursor.close();
        db.close();
        return listProducts;
    }

    /*---------------------Manage orders------------------------*/

    public void cancelOrder(int id_order) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id_order == 0)
            db.delete(TABLE_ORDERLINE, ID_ORDER+" is NULL",new String[]{});
        else
        {
            db.delete(TABLE_ORDERLINE, ID_ORDER+"="+id_order,new String[]{});
            db.delete(TABLE_ORDER,ID_ORDER+"="+id_order,new String[]{});
        }
        db.close();
    }

    public void addOrder(int idclient, String typeOrder, String OrderStatus,String PayStatus,
                         String dateOrder, String timeOrder,String deliveryDate, String deliveryTime, Double Total) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IDCLIENT, idclient);
        values.put(ORDER_TYPE, typeOrder);
        values.put(ORDER_STATUS, OrderStatus);
        values.put(PAYMENT_STATUS, PayStatus);
        values.put(ORDERDATE, dateOrder);
        values.put(ORDERTIME, timeOrder);
        values.put(DELIVERYDATE, deliveryDate);
        values.put(DELIVERYTIME, deliveryTime);
        values.put(ORDER_TOTAL, Total);

        db.insert(TABLE_ORDER, null, values);

        db.close();
    }

    public void addOrderPaid(int idclient, String typeOrder, String OrderStatus,String PayStatus,
                         String dateOrder, String timeOrder,String deliveryDate, String deliveryTime,
                             Double Total, Double cash, Double cb, Double other) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IDCLIENT, idclient);
        values.put(ORDER_TYPE, typeOrder);
        values.put(ORDER_STATUS, OrderStatus);
        values.put(PAYMENT_STATUS, PayStatus);
        values.put(ORDERDATE, dateOrder);
        values.put(ORDERTIME, timeOrder);
        values.put(DELIVERYDATE, deliveryDate);
        values.put(DELIVERYTIME, deliveryTime);
        values.put(ORDER_TOTAL, Total);
        values.put(CASH, cash);
        values.put(CB, cb);
        values.put(OTHER, other);

        db.insert(TABLE_ORDER, null, values);


        db.close();
    }

    public int getIdOrder()
    {
        SQLiteDatabase db  = getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT MAX("+ID_ORDER+") FROM "+TABLE_ORDER,new String[]{});
        int id=0;
        while (cursor.moveToNext())
        {
            id=cursor.getInt(cursor.getColumnIndexOrThrow("MAX("+ID_ORDER+")"));
        }
        cursor.close();
        db.close();
        return id;
    }

    public void setIdOrder(int idcommande){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_ORDERLINE + " SET " + ID_ORDER + "=" + idcommande
                + " WHERE " + ID_ORDER + " is NULL", new String[]{});
        db.close();
    }

    public void setOrderStatus(int idcommande, String status){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ORDER + " SET " + ORDER_STATUS + " = '"+status+"'"
                + " WHERE " + ID_ORDER + "=?", new String[]{String.valueOf(idcommande)});
        db.close();
    }

    public ArrayList<Commande> displayAllOrders(String status) {
        ArrayList<Commande> commandeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(false, TABLE_ORDER,new String[]{ID_ORDER, IDCLIENT,ORDER_TYPE,
                        ORDER_STATUS,PAYMENT_STATUS,ORDER_TOTAL,ORDERDATE,ORDERTIME,DELIVERYDATE,DELIVERYTIME}
                        ,ORDER_STATUS+"='"+status+"'", null, null, null, null, null);

        while (cursor.moveToNext()) {
            commandeArrayList.add(new Commande(cursor));
        }

        cursor.close();
        db.close();

        return commandeArrayList;
    }
}




