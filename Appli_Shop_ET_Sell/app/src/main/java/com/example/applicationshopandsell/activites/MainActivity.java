package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.applicationshopandsell.OnItemClickListener;
import com.example.applicationshopandsell.objets.*;
import com.example.applicationshopandsell.adapteurs.*;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.ressources.API;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.*;

import okhttp3.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    
    public static final int CODE_ACTIVITE_DETAILS = 1;

    // Pour la création des boutons de catégories
    private HorizontalScrollView hsvBoutonsCategories;
    private LinearLayout llBoutonsCategories;

    // Pour l'affichage des articles dans la page principale
    private RecyclerView recyclerViewRabais, recyclerViewSuggestion, recyclerViewDecouvrir;
    private MyHorizontalAdapter adapter;
    private List<Item> itemList;
    private List<Item> sousItemList1;
    private List<Item> sousItemList2;
    private List<Item> sousItemList3;
    ArrayList<ImageView> tabImage = new ArrayList<ImageView>();


    // Pour la barre de navigation du bas
    private ImageButton btnAccueil, btnAjouter, btnUtilisateur;

    //Changer le point d'entrer pour le bon
    final String URL_POINT_ENTREE = "http://10.0.2.2";
    OkHttpClient client;
    Article[] tabArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vues
        hsvBoutonsCategories = findViewById(R.id.hsv_btn_categories);
        llBoutonsCategories = findViewById(R.id.ll_btn_categories);
        btnAccueil = findViewById(R.id.btn_accueil_main);
        btnAjouter = findViewById(R.id.btn_ajouter_main);
        btnUtilisateur = findViewById(R.id.btn_utilisateur_main);

        /* ******************* Début de la création des boutons de catégories ******************* */

        // Récupérer toutes les catégories d'article
        String[] categories = getResources().getStringArray(R.array.categories_array);

        // Boucler pour parcourir toutes les catégories et créer les boutons
        for (String category : categories) {
            Button btnCategorie = new Button(this);

            btnCategorie.setText(category); /* Afficher le nom de la catégorie */
            btnCategorie.setTypeface(null, Typeface.BOLD); /* Texte du bouton en gras */

            btnCategorie.setPadding(18,8,16,8); /* Padding du bouton */

            btnCategorie.setBackgroundResource(R.drawable.btn_categories); /* Allure du bouton */
            btnCategorie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentAfficherArticles = new Intent(MainActivity.this,
                            ArticlesActivity.class);
                    intentAfficherArticles.putExtra("categories_articles", category);
                    startActivity(intentAfficherArticles);
                }
            });

            // Définir les attributs du boutons
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, /* Largeur du bouton */
                    LinearLayout.LayoutParams.WRAP_CONTENT  /* Hauteur du bouton */
            );
            params.setMargins(8, 8, 8, 8); /* Marge des boutons */
            btnCategorie.setLayoutParams(params);

            // Add button to LinearLayout
            llBoutonsCategories.addView(btnCategorie);
        }

        /* ******************** Fin de la création des boutons de catégories ******************** */

        recyclerViewRabais = findViewById(R.id.produit_rabais);
        recyclerViewDecouvrir = findViewById(R.id.produit_decouvrir);
        recyclerViewSuggestion = findViewById(R.id.produit_suggestion);

        itemList = new ArrayList<>();
        sousItemList1 = new ArrayList<Item>();
        sousItemList2 = new ArrayList<Item>();
        sousItemList3 = new ArrayList<Item>();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRabais.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDecouvrir.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSuggestion.setLayoutManager(layoutManager3);

        chargerProduits();




        btnAccueil.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intention;

        if (v.equals(btnAccueil)) {

            Toast.makeText(this, "Vous êtes déjà sur la page d'accueil", Toast.LENGTH_SHORT).show();

        } else if (v.equals(btnAjouter)) {
            intention = new Intent(this, AjouterArticleActivity.class);
            startActivity(intention);

        } else if (v == btnUtilisateur) {

            String userID = getUserIdFromSession();

            if (userID == null) {
                Intent intentOuvrirConnexion = new Intent(this, ConnexionUtilisateurActivity.class);
                startActivity(intentOuvrirConnexion);
            } else {
                Intent intentOuvrirProfil = new Intent(this, ProfilAcheteurActivity.class);
                startActivity(intentOuvrirProfil);
            }

        } /*else {
            Article a;
            for (int i =0; i <tabImage.size();++i){
                if(v.equals(tabImage.get(i))){
                    a = tabArticles[i];
                    intention = new Intent(this, DetailsArticleActivity.class);
                    intention.putExtra("nom_article", a.getNom());
                    intention.putExtra("prix_article", a.getPrix());
                    intention.putExtra("quantite_article", a.getQuantite());
                    intention.putExtra("url_image_article", a.getUrl_image());
                    intention.putExtra("description_article",a.getDescription());
                    intention.putExtra("vendeur_article", a.getUtilisateur_id());
                    startActivity(intention);
                    break;
                }
            }

        }
        */
    }
    private void chargerProduits() {

        client = new OkHttpClient();
        Request requete = new Request.Builder()
                //changer le path pour la bonne route
                .url(API.URL_POINT_ENTREE+"/api/articles")
                .build();

        new Thread(){
            @Override
            public void run() {

                Response response = null;
                try{
                    response = client.newCall(requete).execute();

                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Article[] article = null;
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);

                            try {
                                article =  mapper.readValue(jsonData, Article[].class);

                                for (int i =0; i < article.length; ++i) {
                                    itemList.add(new Item(API.URL_POINT_ENTREE + article[i].getUrl_image(), article[i].getPrix(),article[i].getDescription(),article[i].getNom(),article[i].getQuantite(),article[i].getUtilisateur_id()));
                                }

                                Collections.shuffle(itemList);
                                System.out.println("Avant la boucle" + itemList.size());
                                for (int i = 0; i < 15 && i < itemList.size(); ++i){
                                    System.out.println(i);
                                    if(i < 5){
                                        sousItemList1.add(itemList.get(i));
                                    }
                                    else if(i < 10){
                                        sousItemList2.add(itemList.get(i));
                                    }
                                    else {
                                        sousItemList3.add(itemList.get(i));
                                    }
                                }
                                System.out.println("Après la boucle");

                                MyHorizontalAdapter adaptateur1 = new MyHorizontalAdapter(sousItemList1, new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(Item item) {
                                        System.out.println("Bonjour 1");
                                        Intent intention = new Intent(MainActivity.this, DetailsArticleActivity.class);
                                        intention.putExtra("nom_article", item.getNom());
                                        intention.putExtra("prix_article", item.getPrix());
                                        intention.putExtra("description_article", item.getDescription());
                                        intention.putExtra("url_image_article", item.getImage());
                                        intention.putExtra("quantite_article", item.getQuantite());
                                        intention.putExtra("vendeur_article", item.getUserID());
                                        startActivity(intention);
                                    }
                                });

                                MyHorizontalAdapter adaptateur2 = new MyHorizontalAdapter(sousItemList2, new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(Item item) {
                                        System.out.println("Bonjour 2");
                                        Intent intention = new Intent(MainActivity.this, DetailsArticleActivity.class);

                                        intention.putExtra("nom_article", item.getNom());
                                        intention.putExtra("prix_article", item.getPrix());
                                        intention.putExtra("description_article", item.getDescription());
                                        intention.putExtra("url_image_article", item.getImage());
                                        intention.putExtra("quantite_article", item.getQuantite());
                                        intention.putExtra("vendeur_article", item.getUserID());


                                        startActivity(intention);
                                    }
                                });

                                MyHorizontalAdapter adaptateur3 = new MyHorizontalAdapter(sousItemList3, new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(Item item) {
                                        System.out.println("Bonjour 3");
                                        Intent intention = new Intent(MainActivity.this, DetailsArticleActivity.class);
                                        intention.putExtra("nom_article", item.getNom());
                                        intention.putExtra("prix_article", item.getPrix());
                                        intention.putExtra("description_article", item.getDescription());
                                        intention.putExtra("url_image_article", item.getImage());
                                        intention.putExtra("quantite_article", item.getQuantite());
                                        intention.putExtra("vendeur_article", item.getUserID());
                                        startActivity(intention);
                                    }
                                });

                                recyclerViewRabais.setAdapter(adaptateur1);
                                recyclerViewSuggestion.setAdapter(adaptateur2);
                                recyclerViewDecouvrir.setAdapter(adaptateur3);

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    Log.e("ChargerProduits", "Network error", e);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Failed to load products. Please check your network connection.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally {
                    if (response != null) {
                        response.close(); // Always close the response to free resources
                    }
                }
            }
        }.start();
    }

    private String getUserIdFromSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return preferences.getString("id", null);
    }
}