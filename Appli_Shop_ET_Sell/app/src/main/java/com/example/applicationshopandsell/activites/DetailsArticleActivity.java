package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applicationshopandsell.objets.Utilisateur;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.ressources.API;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailsArticleActivity extends AppCompatActivity implements View.OnClickListener {

    OkHttpClient client;

    private ImageView ivArticle;
    private LinearLayout llDescriptionArticle;
    private TextView txtNomArticle;
    private TextView txtDescription;
    private TextView txtPrixArticle;
    private TextView txtVendeurArticle;
    private TextView txtQuantiteArticle;
    private Utilisateur utilisateur;
    private Button btnRetour, btnAcheter;
    String id_utilisateur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        Intent intention = getIntent();
        String url_image = intention.getStringExtra("url_image_article");
        String nom_article = intention.getStringExtra("nom_article");
        String description_article = intention.getStringExtra("description_article");
        String prix_article = String.valueOf(intention.getDoubleExtra("prix_article",
                0));
        String vendeur_article = String.valueOf(intention.getIntExtra("vendeur_article",
                0));
        int quantite_article = intention.getIntExtra("quantite_article", 0);

        id_utilisateur = getUserIdFromSession();


        ivArticle = findViewById(R.id.ivArticle);
        txtNomArticle = findViewById(R.id.txtNomArticle);
        txtDescription = findViewById(R.id.txtDescriptionArticle);
        txtPrixArticle = findViewById(R.id.txtPrixArticle);
        txtVendeurArticle = findViewById(R.id.txtVendeurArticle);
        txtQuantiteArticle = findViewById(R.id.txtQuantiteArticle);
        llDescriptionArticle = findViewById(R.id.llDescriptionArticle);

        btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(this);

        btnAcheter = findViewById(R.id.btnAcheter);
        btnAcheter.setOnClickListener(this);
        System.out.println(API.URL_POINT_ENTREE + url_image);
        Glide.with(this)
                .load(url_image)
                .into(ivArticle);

        txtNomArticle.setText(nom_article);
        txtDescription.setText(description_article);
        txtPrixArticle.setText(prix_article+" $");

        chargerUtilisateur(vendeur_article, utilisateur);


        if (quantite_article > 0) {
            txtQuantiteArticle.setText("En stock");
            txtQuantiteArticle.setTextColor(Color.BLACK);

            txtNomArticle.setTextColor(Color.BLACK);
            txtDescription.setTextColor(Color.BLACK);
            txtPrixArticle.setTextColor(Color.BLACK);
            txtVendeurArticle.setTextColor(Color.BLACK);
            //llDescriptionArticle.setBackgroundColor(Color.GREEN);

        } else {
            txtQuantiteArticle.setText("Plus en stock");

            txtNomArticle.setTextColor(Color.GRAY);
            txtDescription.setTextColor(Color.GRAY);
            txtPrixArticle.setTextColor(Color.GRAY);
            txtVendeurArticle.setTextColor(Color.GRAY);
            txtQuantiteArticle.setTextColor(Color.RED);
            txtDescription.setTypeface(null, Typeface.BOLD);
            ivArticle.setAlpha(125);

            //txtNomArticle.setTextColor(Color.WHITE);
            //txtPrixArticle.setTextColor(Color.WHITE);
            //txtVendeurArticle.setTextColor(Color.WHITE);
            //txtQuantiteArticle.setTextColor(Color.WHITE);
            //llDescriptionArticle.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnRetour) {
            setResult(RESULT_OK);
            finish();
        } else if (v == btnAcheter) {
            if (id_utilisateur != null) {
                Intent intention = new Intent(this, ProfilVendeurActivity.class);

                intention.putExtra("nom", utilisateur.getNom());
                intention.putExtra("prenom", utilisateur.getPrenom());
                intention.putExtra("email", utilisateur.getEmail());
                intention.putExtra("telephone", utilisateur.getTelephone());
                intention.putExtra("url_img", utilisateur.getPhoto_profil());
                intention.putExtra("nom_utilisateur", utilisateur.getNom_utilisateur());
                intention.putExtra("id_utilisateur", utilisateur.getId());

                startActivity(intention);
            } else {
                Toast.makeText(this, "Il faut être connecté pour utiliser cette fonctionnalité."
                        , Toast.LENGTH_SHORT).show();
            }

        }

    }


    private void chargerUtilisateur(String utilisateur_id, Utilisateur utilisateur) {

        client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(API.URL_POINT_ENTREE+"/api/utilisateurs/" + utilisateur_id)
                .build();

        (new Thread(){
            @Override
            public void run() {

                Response response = null;
                try {
                    response = client.newCall(requete).execute();

                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();
                    System.out.println("C'est moi le coupable: " + jsonData);

                    DetailsArticleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utilisateur utilisateur;
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false); // Ignore unknown properties

                            try {
                                // Parse the JSON data into a JsonNode
                                JsonNode rootNode = mapper.readTree(jsonData);

                                // Extract the 'vendor' part
                                JsonNode vendorNode = rootNode.path("vendorOrUser");

                                // Convert the 'vendor' part to a JSON string
                                String vendorJsonData = vendorNode.toString();

                                // Deserialize the 'vendor' part into Utilisateur class
                                utilisateur = mapper.readValue(vendorJsonData, Utilisateur.class);
                                System.out.println("test" + utilisateur.getNom());
                                DetailsArticleActivity.this.utilisateur = utilisateur;

                                txtVendeurArticle.setText(utilisateur.getNom_utilisateur());

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private String getUserIdFromSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return preferences.getString("id", null);
    }
}