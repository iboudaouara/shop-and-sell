package com.example.applicationshopandsell.objets;

public class Article {
    private String id, nom, dateAjout;
    private int utilisateur_id;
    private double prix;

    private int quantite;
    private String url_image;
    private String description;

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public String getDateAjout(){return dateAjout;}
    public void setDateAjout(String dateAjout){this.dateAjout = dateAjout;}

    public Article() {
    }
    public Article(String id, String nom, int utilisateur_id, double prix, String url_image,String description,String dateAjout) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.nom = nom;
        this.prix = prix;
        this.url_image = url_image;
        this.description = description;
        this.dateAjout = dateAjout;
    }

    public void setDescription(String d) {description = d;}

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }
    public int getQuantite() {
        return quantite;
    }
    public String getUrl_image() {
        return url_image;
    }
    public String getDescription() {
        return description;
    }
}