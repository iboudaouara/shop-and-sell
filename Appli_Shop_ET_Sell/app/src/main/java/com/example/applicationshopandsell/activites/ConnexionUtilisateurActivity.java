package com.example.applicationshopandsell.activites;

import android.app.AlertDialog;
import android.content.*;
import android.graphics.Color;
import android.os.*;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.ressources.API;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.*;
import java.nio.charset.StandardCharsets;

import okhttp3.*;

public class ConnexionUtilisateurActivity extends AppCompatActivity implements View.OnClickListener{

    /* Pour les questions de sécurite */
    private static final int CODE_QUESTION_SECURITE = 1;

    private String email;

    /* Documents d'informations */
    private TextView txt_aide;
    private TextView txt_a_propos;
    private TextView txt_contacter;

    /* Bar de navigation */
    private ImageButton btnAccueil;
    private ImageButton btnAjouter;
    private ImageButton btnUtilisateur;

    /* Formulaire de connexion */
    private EditText edtEmail;
    private EditText edtPwd;

    private Button btnSeConnecter;
    private Button btnCreerUnCompte;
    private TextView motDePasseOublie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_utilisateur);

        /* Bar de navigation */

        // Vues
        btnAccueil = findViewById(R.id.btn_accueil_connexion);
        btnAjouter = findViewById(R.id.btn_ajouter_connexion);
        btnUtilisateur = findViewById(R.id.btn_utilisateur_connexion);

        // Écouteurs
        btnAccueil.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);

        /* Formulaire de connexion */

        // Vues
        edtEmail = findViewById(R.id.edtEmailConnexion);
        edtPwd = findViewById(R.id.edtMotDePasseConnexion);
        btnSeConnecter = findViewById(R.id.btnConnexion);
        btnCreerUnCompte = findViewById(R.id.btnCreerCompte);
        motDePasseOublie = findViewById(R.id.mdp_oublie);

        // Écouteurs
        btnSeConnecter.setOnClickListener(this);
        btnCreerUnCompte.setOnClickListener(this);
        motDePasseOublie.setOnClickListener(this);

        /* Documents d'informations */

        // Vues
        txt_aide = findViewById(R.id.aide);
        txt_a_propos = findViewById(R.id.info);
        txt_contacter = findViewById(R.id.contacter_nous);
        // Politique de confidentialité manquante


        // Écouteurs
        txt_aide.setOnClickListener(this);
        txt_a_propos.setOnClickListener(this);
        txt_contacter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        /* L'intention change en fonction du bouton cliqué */
        Intent intention;

        // Formulaire de connexion
        if (v == btnSeConnecter) {

            if (!champDeTexteRempli(edtEmail, edtPwd)) {

                Toast.makeText(this, "Veuillez remplir tous les champs.",
                        Toast.LENGTH_SHORT).show();

            } else {

                String email = edtEmail.getText().toString();
                String password = edtPwd.getText().toString();
                new LoginTask().execute(email, password);}
        }

        else if (v == btnCreerUnCompte) {

            intention = new Intent(this, CreerCompteActivity.class);
            startActivity(intention);
        }

        else if (v == motDePasseOublie) {

            showEmailDialog();
        }

        // Documents d'information
        else if(v == txt_aide){
            intention = new Intent(this, PageAideActivity.class);
            intention.putExtra("type_texte", "aide");
            startActivity(intention);
        }

        else if(v == txt_a_propos){
            intention = new Intent(this, PageAideActivity.class);
            intention.putExtra("type_texte", "a_propos");
            startActivity(intention);
        }

        else if(v == txt_contacter){
            intention = new Intent(this, PageAideActivity.class);
            intention.putExtra("type_texte", "contact");
            startActivity(intention);
        }

        // Bar de navigation
        else if (v == btnAccueil) {

            intention = new Intent(this, MainActivity.class);
            startActivity(intention);

        }

        else if (v == btnAjouter) {

            /* Intention pour ajouter un produit */
            intention = new Intent(this, AjouterArticleActivity.class);
            startActivity(intention);

        }

        else if (v == btnUtilisateur) {

            // Afficher le profil de l'utilisateur
            Toast.makeText(this, "Connectez-vous pour afficher le profil.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/login");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("email", email);
                jsonInput.put("password", password);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(),
                                    StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
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
                    if (response.has("error")) {
                        Toast.makeText(ConnexionUtilisateurActivity.this, "Login failed: "
                                + response.getString("error"), Toast.LENGTH_LONG).show();
                    } else {
                        String username = response.getJSONObject("user").getString("username");
                        String id = response.getJSONObject("user").getString("id");
                        saveUserSession(username, id);
                        Toast.makeText(ConnexionUtilisateurActivity.this, "Vous êtes connecté!",
                                Toast.LENGTH_LONG).show();
                        updateUI();
                        Intent profilIntent = new Intent(ConnexionUtilisateurActivity.this,
                                ProfilAcheteurActivity.class);
                        startActivity(profilIntent);
                        finish();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ConnexionUtilisateurActivity.this, "Error parsing response",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ConnexionUtilisateurActivity.this, "Erreur lors de la connexion",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /* Mémoriser l'utilisateur connecté */
    private void saveUserSession(String username, String id) {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("id", id);
        editor.apply();
    }

    /* Pour afficher message de bienvenue à l'utilisateur */
    private void updateUI() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        String username = preferences.getString("username", "");
        String id = preferences.getString("id", "");

        if (isLoggedIn) {
            Toast.makeText(this, "Bienvenue, " + username + " (" + id + ")!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean champDeTexteRempli(EditText... edts) {

        boolean tousRemplis = true;

        for (EditText edt : edts) {
            if (edt.getText().toString().isEmpty())  {
                edt.setHintTextColor(Color.parseColor("#F44336"));
                tousRemplis = false;
            } else {
                edt.setHintTextColor(Color.GRAY);
            }
        }

        return tousRemplis;

    }

    private class CheckEmailTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/check_email");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("email", email);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                Log.d("CheckEmailTask", "URL: " + url.toString());
                Log.d("CheckEmailTask", "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(),
                                    StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
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
                    if (response.has("error")) {
                        Toast.makeText(ConnexionUtilisateurActivity.this, "Error: "
                                + response.getString("error"), Toast.LENGTH_LONG).show();
                    } else {
                        JSONArray questions = response.getJSONArray("questions");
                        // Handle the security questions here
                        String question1 = questions.getString(0);
                        String question2 = questions.getString(1);

                        Toast.makeText(ConnexionUtilisateurActivity.this, "Security questions retrieved",
                                Toast.LENGTH_LONG).show();
                        // You can update UI or save the questions as needed
                        Intent intentQuestionsSecurite = new Intent(
                                ConnexionUtilisateurActivity.this,
                                QuestionSecuriteActivity.class);
                        intentQuestionsSecurite.putExtra("email", email);
                        intentQuestionsSecurite.putExtra("question1", question1);
                        intentQuestionsSecurite.putExtra("question2", question2);
                        startActivityForResult(intentQuestionsSecurite, CODE_QUESTION_SECURITE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ConnexionUtilisateurActivity.this, "Error parsing response",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ConnexionUtilisateurActivity.this, "Connection error",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private void showEmailDialog() {
        // Create an EditText view for user input
        final EditText input = new EditText(this);
        input.setHint("Enter email");

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Enter email")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the username input and proceed with account creation
                        String email = input.getText().toString();
                        ConnexionUtilisateurActivity.this.email = email;

                        if (!email.isEmpty()) {
                            new CheckEmailTask().execute(email);

                        } else {
                            Toast.makeText(ConnexionUtilisateurActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void startQuestionSecuriteActivity() {
        Intent intention = new Intent(this, QuestionSecuriteActivity.class);
        startActivityForResult(intention, CODE_QUESTION_SECURITE);
    }
}