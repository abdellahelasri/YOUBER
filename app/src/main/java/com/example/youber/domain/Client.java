package com.example.youber.domain;

import android.database.Cursor;

import java.io.Serializable;

public class Client implements Serializable {
        private int idclient;
        private String nom;
        private String prenom;
        private String telephone;
        private String adresse;
        private String ville;
        private int zip;


        public Client (Cursor cursor)
        {
            nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
            telephone = cursor.getString(cursor.getColumnIndexOrThrow("telephone"));
            adresse = cursor.getString(cursor.getColumnIndexOrThrow("adresse"));
            ville = cursor.getString(cursor.getColumnIndexOrThrow("ville"));
            zip = cursor.getInt(cursor.getColumnIndexOrThrow("zip"));
        }


}


