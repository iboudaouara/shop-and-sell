package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationshopandsell.ressources.API;
import com.example.applicationshopandsell.objets.Article;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.adapteurs.SpinnerAdapter;

import java.text.Normalizer;
import java.util.*;

import okhttp3.*;

public class ArticlesActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {
    // Codes d'activités
    public static final int CODE_ACTIVITE_DETAILS = 1;

    // API - Communication réseau
    private OkHttpClient client;

    // Vues pour la liste des articles
    private Spinner spinnerTrier;
    private ListView lvArticles;

    // Vues pour la barre de navigation
    private ImageButton btnAccueil;
    private ImageButton btnAjouter;
    private ImageButton btnUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        // Récupérer les vues
        spinnerTrier = findViewById(R.id.spinnerTrier);
        lvArticles = findViewById(R.id.lvArticle);

        btnAccueil = findViewById(R.id.btn_accueil_articles);
        btnAjouter = findViewById(R.id.btn_ajouter_articles);
        btnUtilisateur = findViewById(R.id.btn_utilisateur_articles);

        // Initialiser le client OkHttp
        client = new OkHttpClient();

        // Récupérer la liste des options pour le spinner de tri
        List<String> listeOptions = Arrays.asList(getResources().
                getStringArray(R.array.sorting_options));

        // Configurer un adapteur pour le spinner
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, listeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrier.setAdapter(adapter);

        // Pré-sélectionner un filtre si un bouton de catégories a été cliqué depuis le 'main'
        Intent intent = getIntent();
        String categorieFiltre = intent.getStringExtra("categories_articles");

        if (categorieFiltre != null) {
            int position = listeOptions.indexOf(categorieFiltre);
            if (position >= 0) {
                spinnerTrier.setSelection(position);
            } else {
                //Toast.makeText(this, "Catégorie non trouvée.", Toast.LENGTH_SHORT).show();
            }
        } else {
            API.chargerListeArticles("/api/articles", this, lvArticles);
        }

        //Écouteur our le spinner de tri

        spinnerTrier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) { // Pour ignorer le titre du spinner
                    String selectedOption = parent.getItemAtPosition(position).toString();
                    appliquerOptionDeTri(selectedOption);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        // Écouteur pour la liste d'articles
        lvArticles.setOnItemClickListener(this);

        // Écouteur pour la barre de navigation

        if (btnAccueil != null) {
            btnAccueil.setOnClickListener(this);
            System.out.println("btnAccueil");
        }
        if (btnAjouter != null) {
            btnAjouter.setOnClickListener(this);
            System.out.println("btnAjouter");
        }
        if (btnUtilisateur != null) {
            btnUtilisateur.setOnClickListener(this);
            System.out.println("btnUtilisateur");
        }

        API.chargerListeArticles("/api/articles", this, lvArticles);
    }

    private void appliquerOptionDeTri(String optionSelectionnee) {
        Toast.makeText(this, "Option séléctionnée: " + optionSelectionnee,
                Toast.LENGTH_SHORT).show();

        String routeAPI = null;

        switch (optionSelectionnee) {
            case "Trier par nom":
                routeAPI = "/api/articles/trier/nom";
                break;
            case "Trier par prix (asc)":
                routeAPI = "/api/articles/trier/prix/ASC";
                break;
            case "Trier par prix (desc)":
                routeAPI = "/api/articles/trier/prix/DESC";
                break;
            case "Trier par date":
                routeAPI = "/api/articles/trier/date_ajout";
                break;
            case "Tous":
                routeAPI = "/api/articles";
                break;
            default: /* Pour les catégories */
                routeAPI = "/api/articles/categories/"
                        + removeSpecialCharacters(optionSelectionnee);
                System.out.println(routeAPI);

        }

        // Mettre à jour la liste d'articles en fonction de l'option de tri sélectionnée
        API.chargerListeArticles(routeAPI, this, lvArticles);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Article article = (Article) parent.getItemAtPosition(position);
        Intent intention = new Intent(this, DetailsArticleActivity.class);

        intention.putExtra("position", position);
        intention.putExtra("id_article", article.getId());
        intention.putExtra("nom_article", article.getNom());
        intention.putExtra("description_article", article.getDescription());
        intention.putExtra("prix_article", article.getPrix());
        intention.putExtra("quantite_article", article.getQuantite());
        intention.putExtra("vendeur_article", article.getUtilisateur_id());
        intention.putExtra("url_image_article", API.URL_POINT_ENTREE + article.getUrl_image());

        startActivityForResult(intention, CODE_ACTIVITE_DETAILS);
    }
    public static String removeSpecialCharacters(String input) {
        // Normalize the string to decompose characters into base characters and diacritics
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // Remove diacritics
        String asciiOnly = normalized.replaceAll("\\p{M}", "");

        // Optionally, replace spaces and non-ASCII characters (though this may not be
        // needed if only diacritics are involved)
        // Remove anything that's not a letter or space
        asciiOnly = asciiOnly.replaceAll("[^\\p{ASCII}]", "");

        return asciiOnly;
    }

    public static String formatSortOption(String input) {
        // Remove unwanted characters (e.g., "Trier par" and parentheses)
        String cleanedInput = input.replace("Trier par ", "").
                replace("(", "").replace(")", "").trim();

        // Split the cleaned input into parts
        String[] parts = cleanedInput.split(" ");

        // Handle cases with no order specified
        if (parts.length == 1) {
            return parts[0]; // Return the field name directly
        } else if (parts.length == 2) {
            String field = parts[0]; // e.g., "prix" or "nom"
            String order = parts[1]; // e.g., "asc"
            return field + "/" + order.toUpperCase(); // e.g., "prix/ASC"
        } else {
            // Handle unexpected input format
            throw new IllegalArgumentException("Invalid input format");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAccueil) {
            Intent intentOuvrirAccueil = new Intent(this, MainActivity.class);
            startActivity(intentOuvrirAccueil);
        }
        else if (v == btnAjouter) {
            Intent intentOuvrirAjouterArticle = new Intent(this, AjouterArticleActivity.class);
            startActivity(intentOuvrirAjouterArticle);
        }
        else if (v == btnUtilisateur) {
            String userID = getUserIdFromSession();
            if (userID == null) {
                Intent intentOuvrirConnexion = new Intent(this, ConnexionUtilisateurActivity.class);
                startActivity(intentOuvrirConnexion);
            } else {
                Intent intentOuvrirProfil = new Intent(this, ProfilAcheteurActivity.class);
                startActivity(intentOuvrirProfil);
            }
        }
    }

    private String getUserIdFromSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return preferences.getString("id", null);
    }
}