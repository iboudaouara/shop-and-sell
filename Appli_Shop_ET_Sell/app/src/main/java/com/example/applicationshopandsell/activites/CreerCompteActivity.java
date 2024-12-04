package com.example.applicationshopandsell.activites;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationshopandsell.ressources.API;
import com.example.applicationshopandsell.R;

import org.json.JSONObject;
import java.io.*;
import java.net.*;

public class CreerCompteActivity extends AppCompatActivity implements View.OnClickListener {

    /* Pour les questions de sécurite */
    private static final int CODE_QUESTION_SECURITE = 1;

    /* Bar de navigation */
    private ImageButton btnAccueil;
    private ImageButton btnAjouter;
    private ImageButton btnUtilisateur;

    /* Formulaire d'inscription */
    private EditText edtNom;
    private EditText edtPrenom;
    private EditText edtEmail;
    private EditText edtTel;
    private EditText edtMotDePasse;
    private EditText edtConfirmerMotDePasse;
    private Button btn_sinscire;

    /* Questions de sécurité */
    private String questionSecurite1;
    private String reponseSecurite1;
    private String questionSecurite2;
    private String reponseSecurite2;

    /* Infos de l'utilisateur */
    private int id_utilisateur;
    private String nomFamilleUtilisateur;
    private String nomDUtilisateur;
    private String prenomUtilisateur;
    private String emailUtilisateur;
    private String telUtilisateur;
    private String mdpUtilisateur;
    private String mdp2Utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        /* Bar de navigation */
        btnAccueil = findViewById(R.id.btnAccueilSinscrire);
        btnAjouter = findViewById(R.id.btnAjouterSinscrire);
        btnUtilisateur = findViewById(R.id.btnUtilisateurSinscrire);

        btnAccueil.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);

        /* Formulaire d'inscription */
        edtNom = findViewById(R.id.edtNomFamilleUtilisateur);
        edtPrenom = findViewById(R.id.edtPrenomUtilisateur);
        edtEmail = findViewById(R.id.edtEmailUtilisateur);
        edtTel = findViewById(R.id.edtTelUtilisateur);
        edtMotDePasse = findViewById(R.id.edtMDP1Utilisateur);
        edtConfirmerMotDePasse = findViewById(R.id.edtMDP2Utilisateur);

        btn_sinscire = findViewById(R.id.btnSinscrire);
        btn_sinscire.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intention;

        if (v == btnAccueil) {

            intention = new Intent(this, MainActivity.class);
            startActivity(intention);

        } else if (v == btnAjouter) {

            /* Intention pour ajouter un produit */

        } else if (v == btnUtilisateur) {

            /* Affiche le profil de l'utilisateur */
            intention = new Intent(this, ConnexionUtilisateurActivity.class);
            startActivity(intention);

        } else if (v == btn_sinscire) {
            if (!champDeTexteRempli(edtNom, edtPrenom, edtEmail, edtTel,
                    edtMotDePasse, edtConfirmerMotDePasse)){

                Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();

            } else if (!motDePasseIdentique(edtMotDePasse, edtConfirmerMotDePasse)) {
                Toast.makeText(this, "Les mots de passe sont différents",
                        Toast.LENGTH_SHORT).show();

            } else {
                showUsernameDialog();
            }



        }
    }

    private boolean motDePasseIdentique(EditText mdpUtilisateur, EditText mdp2Utilisateur) {

        return mdpUtilisateur.getText().toString().equals(mdp2Utilisateur.getText().toString());
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

    private class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/register");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("nom_utilisateur", params[0]);
                jsonInput.put("email", params[1]);
                jsonInput.put("password", params[2]);
                jsonInput.put("prenom", params[3]);
                jsonInput.put("nom", params[4]);
                jsonInput.put("pays", params[5]);
                jsonInput.put("telephone", params[6]);
                jsonInput.put("question_securite1", params[7]);
                jsonInput.put("reponse_securite1", params[8]);
                jsonInput.put("question_securite2", params[9]);
                jsonInput.put("reponse_securite2", params[10]);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                Log.d("RegisterTask", "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }

                        // Parse the JSON response to get the user ID
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        String userId = jsonResponse.getString("id");
                        id_utilisateur = Integer.parseInt(userId);

                        System.out.println(id_utilisateur + "Bonjour");

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

                    // Log the response for debugging
                    Log.d("RegisterTask", "Server response: " + result);

                    if (response.has("error")) {
                        Toast.makeText(CreerCompteActivity.this, "Registration failed: " + response.getString("error"), Toast.LENGTH_LONG).show();
                    } else if (response.has("message")) {
                        Toast.makeText(CreerCompteActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreerCompteActivity.this, ConnexionUtilisateurActivity.class);
                        intent.putExtra("nom_utilsiateur", nomFamilleUtilisateur);
                        intent.putExtra("prenom_utilisateur", prenomUtilisateur);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CreerCompteActivity.this, "Unexpected response format", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("BugIB", e.toString());
                    Toast.makeText(CreerCompteActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CreerCompteActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_QUESTION_SECURITE && resultCode == RESULT_OK) {

            questionSecurite1 = data.getStringExtra("question1");
            questionSecurite2 = data.getStringExtra("question2");
            reponseSecurite1 = data.getStringExtra("answer1");
            reponseSecurite2 = data.getStringExtra("answer2");

            nomFamilleUtilisateur = edtNom.getText().toString();
            prenomUtilisateur = edtPrenom.getText().toString();
            emailUtilisateur = edtEmail.getText().toString();
            telUtilisateur = edtTel.getText().toString();
            mdpUtilisateur = edtMotDePasse.getText().toString();
            mdp2Utilisateur = edtConfirmerMotDePasse.getText().toString();

            new RegisterTask().execute(
                    nomDUtilisateur,      /* nom_utilisateur */
                    emailUtilisateur,       /* email */
                    mdpUtilisateur,         /* password */
                    prenomUtilisateur,      /* prenom */
                    nomFamilleUtilisateur,  /* nom */
                    "",                     /* pays */
                    telUtilisateur,         /* telephone */
                    questionSecurite1,      /* question_securite1 */
                    reponseSecurite1,       /* reponse_securite1 */
                    questionSecurite2,      /* question_securite2 */
                    reponseSecurite2        /* reponse_securite2 */
            );




        }
    }
    private void showUsernameDialog() {
        // Create an EditText view for user input
        final EditText input = new EditText(this);
        input.setHint("Enter username");

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Enter Username")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the username input and proceed with account creation
                        String username = input.getText().toString();
                        if (!username.isEmpty()) {
                            CreerCompteActivity.this.nomDUtilisateur = username; // Set the username
                            // Proceed to the next step
                            startQuestionSecuriteActivity();
                        } else {
                            Toast.makeText(CreerCompteActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
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