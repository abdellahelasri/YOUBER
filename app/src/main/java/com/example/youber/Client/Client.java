package com.example.youber.Client;

import android.database.Cursor;

import java.io.Serializable;

public class Client implements Serializable {
    private int idclient;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String ville;

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() { return adresse; }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Client (Cursor cursor)
    {
        idclient = cursor.getInt(cursor.getColumnIndexOrThrow("idclient"));
        nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
        telephone = cursor.getString(cursor.getColumnIndexOrThrow("telephone"));
        adresse = cursor.getString(cursor.getColumnIndexOrThrow("adresse"));
        ville = cursor.getString(cursor.getColumnIndexOrThrow("ville"));
    }
}


