package com.example.applicationshopandsell.objets;

public class Utilisateur {

    private int id;
    private String nom_utilisateur;




    private String photo_profil;
    private String nom;
    private String email;
    private String mot_de_passe;
    private String prenom;
    private String telephone;
    private String adresse;
    private String ville;
    private String code_postal;

    public Utilisateur(int id, String nom_utilisateur, String photo_profil, String nom, String email,
                       String mot_de_passe, String prenom, String telephone, String adresse, String ville,
                       String code_postal) {
        this.id = id;
        this.nom_utilisateur = nom_utilisateur;
        this.photo_profil = photo_profil;
        this.nom = nom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Utilisateur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public String getPhoto_profil() {
        return photo_profil;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public void setPhoto_profil(String photo_profil) {
        this.photo_profil = photo_profil;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", photo_profil='" + photo_profil + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
