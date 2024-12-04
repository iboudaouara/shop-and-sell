package com.example.applicationshopandsell.objets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vendeur {

    private String id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String photo_profil;

    public Vendeur(@JsonProperty("nom_utilisateur") String id,
                   @JsonProperty("nom") String nom,
                   @JsonProperty("prenom") String prenom,
                   @JsonProperty("email") String email,
                   @JsonProperty("telephone") String telephone,
                   @JsonProperty("photo_profil") String photo_profil){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.photo_profil = photo_profil;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return telephone;
    }

    public String getPhoto_profil() {
        return photo_profil;
    }
}
