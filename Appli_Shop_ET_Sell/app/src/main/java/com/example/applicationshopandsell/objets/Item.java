package com.example.applicationshopandsell.objets;

import android.media.Image;
import android.widget.ImageView;

public class Item {

    private double prix;
    private String url;
    private String nom;
    private String description;
    private int quantite;
    private int userID;

    public Item(String url, double prix,String description,String nom, int quantite,int userID) {
        this.url = url;
        this.prix = prix;
        this.description = description;
        this.nom = nom;
        this.quantite = quantite;
        this.userID = userID;
    }

    public String getImage(){return url;}
    public double getPrix() {
        return prix;
    }
    public String getDescription() {return description;}
    public String getNom() {return nom;}
    public int getQuantite() {return quantite;}
    public int getUserID() {return userID;}
}