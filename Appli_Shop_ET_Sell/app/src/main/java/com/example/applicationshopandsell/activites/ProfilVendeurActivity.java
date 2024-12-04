package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applicationshopandsell.objets.Article;
import com.example.applicationshopandsell.adapteurs.ArticlesAdapter;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.ressources.API;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProfilVendeurActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient client;

    private ImageView imgVendeur;

    private TextView nomVendeur;
    private TextView prenomVendeur;
    private TextView mailVendeur;
    private TextView telVendeur;
    private ImageButton btnRetour;

    private ListView listeArtcileVendeur;

    String nom;
    String prenom;
    String mail;
    String telephone;
    String img;
    String nom_user;
    int id_vendeur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_vendeur);

        imgVendeur = findViewById(R.id.img_vendeur);

        nomVendeur = (TextView) findViewById(R.id.txt_nom_vendeur);
        prenomVendeur = (TextView) findViewById(R.id.txt_prenom_vendeur);
        mailVendeur = (TextView) findViewById(R.id.txt_mail_vendeur);
        telVendeur = (TextView) findViewById(R.id.txt_tel_vendeur);
        telVendeur.setTextColor(Color.BLUE);
        btnRetour = (ImageButton) findViewById(R.id.btn_retour_vendeur);

        listeArtcileVendeur = (ListView) findViewById(R.id.liste_article_vendeur);

        Intent i = getIntent();

        nom = i.getStringExtra("nom");
        prenom = i.getStringExtra("prenom");
        mail = i.getStringExtra("email");
        telephone = i.getStringExtra("telephone");
        img = i.getStringExtra("url_img");
        nom_user = i.getStringExtra("nom_utilisateur");
        id_vendeur = i .getIntExtra("id_utilisateur", 0);



        nomVendeur.setText(nom);
        prenomVendeur.setText(prenom);
        mailVendeur.setText(mail);
        telVendeur.setText(telephone);

        telVendeur.setOnClickListener(this);

        Log.d("IMAGE", API.URL_POINT_ENTREE + "/Image/" + img);

        Glide.with(ProfilVendeurActivity.this)
                .load(API.URL_POINT_ENTREE + "/Image/" + img)
                .into(imgVendeur);

        chargerProduits();

        btnRetour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == telVendeur){
            Uri u = Uri.parse("smsto:"+ telVendeur.getText().toString());
            Intent intention = new Intent(Intent.ACTION_SENDTO,u);
            startActivity(intention);
        }
        else{
            setResult(RESULT_OK);
            finish();
        }


    }

    private void chargerProduits() {

        client = new OkHttpClient();
        Request requete = new Request.Builder()
                //changer le path pour la bonne route
                .url(API.URL_POINT_ENTREE + "/api/articles")
                .build();

        new Thread(){
            @Override
            public void run() {

                Response response = null;
                try{
                    response = client.newCall(requete).execute();

                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();
                    ProfilVendeurActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Article[] article = null;
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);

                            try {
                                article =  mapper.readValue(jsonData, Article[].class);

                                ArrayList<Article> articleSelected = new ArrayList<Article>();

                                for (int i = 0; i < article.length; ++i){
                                    System.out.println("le id" + article[i].getUtilisateur_id());
                                    System.out.println(id_vendeur);
                                        if (article[i].getUtilisateur_id() == id_vendeur){
                                            articleSelected.add(article[i]);
                                        }
                                }

                                Article[] articleTabSelected = new Article[articleSelected.size()];
                                for (int i = 0; i < articleTabSelected.length; ++i){
                                    articleTabSelected[i] = articleSelected.get(i);
                                }

                                ArticlesAdapter adapter = new ArticlesAdapter(ProfilVendeurActivity.this, R.layout.article, articleTabSelected);
                                listeArtcileVendeur.setAdapter(adapter);

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



}