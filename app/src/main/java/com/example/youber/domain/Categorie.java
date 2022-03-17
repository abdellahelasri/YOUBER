package com.example.youber.domain;

import android.database.Cursor;

public class Categorie {
    private int id;
    private String nom;
    private String pic;

    public Categorie(String titre, String pic) {
        this.nom = titre;
        this.pic = pic;
    }

    public Categorie(Cursor cursor)
    {
        nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
    }

    public String getTitre() {
        return nom;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setTitre(String titre) {
        this.nom = titre;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
