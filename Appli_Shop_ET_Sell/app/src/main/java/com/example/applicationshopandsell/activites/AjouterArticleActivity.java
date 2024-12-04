package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.RequestBuilder;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.ressources.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.*;

public class AjouterArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_IMAGE = 2;

    private ImageButton btnPrendrePhoto;
    private Uri imageUri;

    // Définir le type de média pour les requetes post JSON
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    // Champs de texte pour entrer l'information de l'article à ajouter
    private EditText edtNomArticle, edtPrixArticle, edtDescriptionArticle, edtQuantiteArticle;

    private Button btnAjouterUnArticle;


    private String nomArticle, prixArticle, quantiteArticle, descriptionArticle, id_utilisateur;

    // Bar de navigation
    ImageButton btnAccueil, btnAjouter, btnUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_article);

        btnPrendrePhoto = findViewById(R.id.btnPrendrePhoto); // Assume you have an ImageView to show the image

        // Récupérer l'id de l'utilisateur qui désire mettre en vente l'article
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        id_utilisateur = preferences.getString("id", null);

        // Récupérer les informations de l'article à vendre
        edtNomArticle = findViewById(R.id.edtNomArticleAjouter);
        edtPrixArticle = findViewById(R.id.edtPrixArticleAjouter);
        edtQuantiteArticle = findViewById(R.id.edtQuantiteArticleAjouter);
        edtDescriptionArticle = findViewById(R.id.txtareaDescriptionArticle);

        btnAjouterUnArticle = findViewById(R.id.btnAjouterUnArticle);
        btnAjouterUnArticle.setOnClickListener(this);

        // Pour la barre de navigation
        btnAccueil = findViewById(R.id.btn_accueil_ajouter);
        btnAjouter = findViewById(R.id.btn_ajouter_ajouter);
        btnUtilisateur = findViewById(R.id.btn_utilisateur_ajouter);

        btnAccueil.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intention;
        if (v == btnAccueil) {
            intention = new Intent(this, MainActivity.class);
            startActivity(intention);
        } else if (v == btnAjouter) {
            Toast.makeText(this,
                    "Vous êtes déjà sur le formulaire d'ajout.", Toast.LENGTH_SHORT).show();
        } else if (v == btnUtilisateur) {
            String userID = getUserIdFromSession();

            if (userID == null) {
                Intent intentOuvrirConnexion = new Intent(this, ConnexionUtilisateurActivity.class);
                startActivity(intentOuvrirConnexion);
            } else {
                Intent intentOuvrirProfil = new Intent(this, ProfilAcheteurActivity.class);
                startActivity(intentOuvrirProfil);
            }

        } else if (v == btnAjouterUnArticle) {
            id_utilisateur = getUserIdFromSession();
            nomArticle = edtNomArticle.getText().toString().trim();
            prixArticle = edtPrixArticle.getText().toString().trim();
            quantiteArticle = edtQuantiteArticle.getText().toString().trim();
            descriptionArticle = edtDescriptionArticle.getText().toString().trim();

            // Check for empty fields before proceeding
            if (nomArticle.isEmpty() || prixArticle.isEmpty() || quantiteArticle.isEmpty() || descriptionArticle.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            File imageFile = new File("/path/to/your/image.jpg");
            if (!imageFile.exists()) {
                Toast.makeText(this, "Image file does not exist.", Toast.LENGTH_SHORT).show();
                return;
            }

            uploadImage(imageFile, Integer.parseInt(id_utilisateur), nomArticle, prixArticle, quantiteArticle, descriptionArticle);
        }
    }
    private String getUserIdFromSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return preferences.getString("id", null);
    }
    public void uploadImage(File imageFile, int id_utilisateur, String nomArticle, String prixArticle,
                            String quantiteArticle, String descriptionArticle) {

        OkHttpClient client = new OkHttpClient();

        try {
            // Create the JSON object with the necessary fields
            JSONObject objet = new JSONObject();
            objet.put("utilisateur_id", id_utilisateur);
            objet.put("nom", nomArticle);
            objet.put("prix", Double.parseDouble(prixArticle));
            objet.put("quantite", Integer.parseInt(quantiteArticle));
            objet.put("type", "Electronique");
            objet.put("image", "tomate.png");
            objet.put("description_article", descriptionArticle);
            objet.put("nom_utilisateur", "tommi07");

            // Convert the JSONObject to a string and create a RequestBody
            RequestBody jsonBody = RequestBody.create(objet.toString(), MediaType.parse("application/json; charset=utf-8"));

            // Create a request body with the file and image media type
            RequestBody fileBody = RequestBody.create(imageFile, MediaType.parse("image/jpeg"));

            // Create MultipartBody to hold the file and the JSON data
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", imageFile.getName(), fileBody)
                    .addFormDataPart("json", null, jsonBody)
                    .build();
            System.out.println("requete");
            // Create a request to the server
            Request request = new Request.Builder()
                    .url(API.URL_POINT_ENTREE+"/api/articles")
                    .post(requestBody)
                    .build();

            // Execute the request asynchronously
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace(); // Handle the error
                    System.out.println("fail");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("success");
                    if (response.isSuccessful()) {
                        // Handle the response from the server
                        System.out.println("Image and data uploaded successfully: " + response.body().string());
                    } else {
                        System.out.println("Upload failed: " + response.message());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

