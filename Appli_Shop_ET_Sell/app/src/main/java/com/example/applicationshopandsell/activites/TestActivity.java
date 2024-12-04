package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.objets.Vendeur;
import com.example.applicationshopandsell.adapteurs.VendeurAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Changer le point d'entrer pour le bon
    final String URL_POINT_ENTREE = "http://10.0.2.2";
    OkHttpClient client;

    private ListView lsVendeur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lsVendeur = findViewById(R.id.lsVendeur);
        chargerVendeur();

        lsVendeur.setOnItemClickListener(this);
    }

    private void chargerVendeur() {

        client = new OkHttpClient();
        Request requete = new Request.Builder()
                //changer le path pour la bonne route
                .url(URL_POINT_ENTREE+"/api/user")
                .build();

        new Thread(){
            @Override
            public void run() {

                Response response = null;
                try{
                    response = client.newCall(requete).execute();

                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();

                    TestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Vendeur[] vendeurs = null;

                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);

                            try {
                                vendeurs =  mapper.readValue(jsonData, Vendeur[].class);
                                VendeurAdapter adapter = new VendeurAdapter(TestActivity.this, R.layout.vendeur_layout, vendeurs);
                                lsVendeur.setAdapter(adapter);

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vendeur v = (Vendeur) parent.getItemAtPosition(position);

        Intent intention = new Intent(this, ProfilVendeurActivity.class);
        intention.putExtra("nom", v.getNom());
        intention.putExtra("prenom", v.getPrenom());
        intention.putExtra("email", v.getEmail());
        intention.putExtra("telephone", v.getTel());
        intention.putExtra("url_img",v.getPhoto_profil());
        intention.putExtra("nom_utilisateur", v.getId());
        startActivity(intention);
    }
}