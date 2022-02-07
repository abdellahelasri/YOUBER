package com.example.youber.domain;

import android.database.Cursor;

public class Categorie {
    private int id;
    private String titre;
    private String pic;

    public Categorie(String titre, String pic) {
        this.titre = titre;
        this.pic = pic;
    }

    public Categorie(Cursor cursor)
    {
        titre = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
