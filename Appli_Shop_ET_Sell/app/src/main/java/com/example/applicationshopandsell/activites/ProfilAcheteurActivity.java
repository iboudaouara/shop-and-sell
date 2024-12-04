package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applicationshopandsell.ressources.API;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.objets.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class ProfilAcheteurActivity extends AppCompatActivity implements View.OnClickListener {

    private Utilisateur utilisateur;

    private TextView txtNomUtilisateur, txtInfoPerso, txtNom, txtPrenom, txtTelephone, txtEmail;
    private EditText edtNom, edtPrenom, edtTelephone, edtEmail,
            edtAdresse, edtVille, edtCodePostal, edtPays,
            edtMotDePasse, edtConfirmerMotDePasse;
    private ImageView ivPhotoProfil;
    private String nom_utilisateur, nom, prenom, telephone, email;
    private Button btnDeconnexion, btnSauvegarder;
    private ImageButton btnAccueil, btnAjouter, btnUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_acheteur);

        btnAccueil = findViewById(R.id.btn_accueil_PA);
        btnAjouter = findViewById(R.id.btn_ajouter_PA);
        btnUtilisateur = findViewById(R.id.btn_utilisateur_PA);

        txtNomUtilisateur = findViewById(R.id.txtNomUtilisateur);
        txtInfoPerso = findViewById(R.id.txtInfoPerso);
        txtNom = findViewById(R.id.txtNom);
        edtNom = findViewById(R.id.edtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        edtPrenom = findViewById(R.id.edtPrenom);
        txtTelephone = findViewById(R.id.txtTelephone);
        edtTelephone = findViewById(R.id.edtTelephone);
        txtEmail = findViewById(R.id.txtEmail);
        edtEmail = findViewById(R.id.edtEmail);
        edtAdresse = findViewById(R.id.edtAdresse);
        edtVille = findViewById(R.id.edtVille);
        edtCodePostal = findViewById(R.id.edtCodePostal);
        edtPays = findViewById(R.id.edtPays);
        edtMotDePasse = findViewById(R.id.edtMotDePasse);
        edtConfirmerMotDePasse = findViewById(R.id.edtConfirmerMotDePasse);

        ivPhotoProfil = findViewById(R.id.ivPhotoProfil);

        btnDeconnexion = findViewById(R.id.btnDeconnexionPA);
        btnSauvegarder = findViewById(R.id.btnSauvegarderPA);

        btnDeconnexion.setOnClickListener(this);
        btnSauvegarder.setOnClickListener(this);

        btnAccueil.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);

        API.trouverUnUtilisateur(getUserIdFromSession(), this, new API.Callback<Utilisateur>() {
            @Override
            public void onSuccess(Utilisateur utilisateur) {
                ProfilAcheteurActivity.this.utilisateur = utilisateur;
                updateUI();
                Toast.makeText(ProfilAcheteurActivity.this, "Utilisateur trouvé: " +
                        utilisateur.getNom(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ProfilAcheteurActivity.this,
                        "Erreur lors de la récupération de l'utilisateur: "
                                + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI() {
        nom_utilisateur = utilisateur.getNom_utilisateur() != null ?
                utilisateur.getNom_utilisateur() : "non trouvé";
        txtNomUtilisateur.setText(nom_utilisateur);

        txtInfoPerso.setText("Informations personnelles");

        nom = utilisateur.getNom() != null ?
                utilisateur.getNom() : "non trouvé";
        edtNom.setText(nom);


        prenom = utilisateur.getPrenom() != null ?
                utilisateur.getPrenom() : "non trouvé";

        edtPrenom.setText(prenom);

        telephone = utilisateur.getTelephone() != null ?
                utilisateur.getTelephone() : "non trouvé";
        txtTelephone.setText("Téléphone: ");
        edtTelephone.setText(telephone);

        email = utilisateur.getEmail() != null ? utilisateur.getEmail() : "non trouvé";
        edtEmail.setText(email);
        edtAdresse.setText(utilisateur.getAdresse());
        edtVille.setText(utilisateur.getVille());
        edtCodePostal.setText(utilisateur.getCode_postal());
        String photoProfil;
        if (utilisateur.getPhoto_profil() != null) {
             photoProfil = API.URL_POINT_ENTREE + "/Image/" + utilisateur.getPhoto_profil();
        } else {
            photoProfil = "https://static.vecteezy.com/ti/vecteur-libre/p3/12911441-icone-de-profil-d-avatar-par-defaut-dans-le-style-de-ligne-vectoriel.jpg";
        }
        System.out.println(photoProfil);

        Glide.with(ProfilAcheteurActivity.this)
                .load(photoProfil)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(ivPhotoProfil);


        txtNomUtilisateur.setVisibility(View.VISIBLE);
        txtNom.setVisibility(View.VISIBLE);
        edtNom.setVisibility(View.VISIBLE);
        txtPrenom.setVisibility(View.VISIBLE);
        edtPrenom.setVisibility(View.VISIBLE);
        edtTelephone.setVisibility(View.VISIBLE);
        txtTelephone.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDeconnexion) {
            deconnecterUtilisateur();
        } else if (v == btnSauvegarder) {

            String id = getUserIdFromSession();
            String username = txtNomUtilisateur.getText().toString();
            String email = edtEmail.getText().toString();
            String telephone = edtTelephone.getText().toString();
            String motDePasse = edtMotDePasse.getText().toString();
            String ConfirmerMotDePasse = edtConfirmerMotDePasse.getText().toString();
            String prenom = edtPrenom.getText().toString();
            String nom = edtNom.getText().toString();
            String adresse = edtAdresse.getText().toString();
            String ville = edtVille.getText().toString();
            String codePostal = edtCodePostal.getText().toString();
            String pays = edtPays.getText().toString();

            if (motDePasse.equals(ConfirmerMotDePasse)) {
                new UpdateUserTask().execute(id, username, email, telephone, motDePasse, prenom,
                        nom, adresse, ville, codePostal, pays);
            } else {
                Toast.makeText(this, "Les mots de passe doivent être identiques.",
                        Toast.LENGTH_SHORT).show();
            }


        } else if (v == btnAccueil) {
            Intent intention = new Intent(this, MainActivity.class);
            startActivity(intention);

        } else if (v == btnAjouter) {
            Intent intention = new Intent(this, AjouterArticleActivity.class);
            startActivity(intention);
        } else if (v == btnUtilisateur) {
            Toast.makeText(this, "Vous êtes déjà sur la page de profil.", Toast.LENGTH_SHORT).show();

        }
    }
    private String getUserIdFromSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        return preferences.getString("id", null);
    }
    private void deconnecterUtilisateur() {

        // Accéder aux préférences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);

        //  Récupérer l'éditeur de préférences
        SharedPreferences.Editor editor = preferences.edit();

        // Supprimer l'ID de session
        editor.clear();
        editor.apply();

        // Rediriger vers la page de connexion
        Intent loginIntent = new Intent(this, ConnexionUtilisateurActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private class UpdateUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/utilisateurs/update");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", params[0]);
                jsonInput.put("username", params[1]);
                jsonInput.put("email", params[2]);
                jsonInput.put("phone", params[3]);
                jsonInput.put("password", params[4]);
                jsonInput.put("prenom", params[5]);
                jsonInput.put("nom", params[6]);
                jsonInput.put("adresse", params[7]);
                jsonInput.put("ville", params[8]);
                jsonInput.put("code_postal", params[9]);
                jsonInput.put("pays", params[10]);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                Log.d("UpdateUserTask", "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }

                        // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        return jsonResponse.toString();
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject response = new JSONObject(result);

                    // Log the response for debugging
                    Log.d("UpdateUserTask", "Server response: " + result);

                    if (response.has("success") && response.getBoolean("success")) {
                        Toast.makeText(ProfilAcheteurActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                        //finish();
                    } else {
                        Toast.makeText(ProfilAcheteurActivity.this, "Update failed: " + response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("BugIB", e.toString());
                    Toast.makeText(ProfilAcheteurActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ProfilAcheteurActivity.this, "Update failed", Toast.LENGTH_LONG).show();
            }
        }
    }




}