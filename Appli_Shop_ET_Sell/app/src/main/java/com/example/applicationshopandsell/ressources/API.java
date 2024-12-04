package com.example.applicationshopandsell.ressources;

import android.app.Activity;
import android.widget.ListView;

import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.adapteurs.ArticlesAdapter;
import com.example.applicationshopandsell.objets.Article;
import com.example.applicationshopandsell.objets.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class API {

    // Prévient l'instanciation
    private API() {}

    public static final String URL_POINT_ENTREE = "http://10.0.2.2:8888";
    public static final String ROUTE_API_PAR_DEFAUT_ARTICLES = "/api/articles";
    public static final String ROUTE_API_PAR_DEFAUT_UTILISATEURS = "/api/utilisateurs";
    public static final String ID_PAR_DEFAUT = "0";

    public static void chargerListeArticles(String routeAPI, Activity Activity,
                                            ListView lvArticles) {

        routeAPI = routeAPI == null ? ROUTE_API_PAR_DEFAUT_ARTICLES : routeAPI;

        OkHttpClient client = new OkHttpClient();

        Request requete = new Request.Builder()
                .url(API.URL_POINT_ENTREE + routeAPI)
                .build();

        (new Thread(){
            @Override
            public void run() {

                Response response = null;
                try {
                    response = client.newCall(requete).execute();

                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();

                    Activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Article[] tabArticles;
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.
                                            FAIL_ON_UNKNOWN_PROPERTIES,
                                    false); /* Pour ignorer les
                                                                        propriétés inconnues */

                            try {
                                tabArticles =  mapper.readValue(
                                        jsonData, Article[].class);
                                ArticlesAdapter adaptateur = new
                                        ArticlesAdapter(Activity,
                                        R.layout.article,
                                        tabArticles);
                                lvArticles.setAdapter(adaptateur);

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

    public static void trouverUnUtilisateur(String userID, Activity Activity,
                                            Callback<Utilisateur> callback) {
        userID = userID == null ? ID_PAR_DEFAUT : userID;
        String routeAPI = ROUTE_API_PAR_DEFAUT_UTILISATEURS + "/" + userID;
        Utilisateur utilisateur;
        OkHttpClient client = new OkHttpClient();

        Request requete = new Request.Builder()
                .url(API.URL_POINT_ENTREE + routeAPI)
                .build();

        (new Thread(){
            @Override
            public void run() {
                final Response[] response = new Response[1];
                try {
                    response[0] = client.newCall(requete).execute();

                    ResponseBody responseBody = response[0].body();
                    String jsonData = responseBody.string();

                    Activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.
                                            FAIL_ON_UNKNOWN_PROPERTIES,
                                    false); /* Pour ignorer les
                                                                        propriétés inconnues */

                            try {
                                JsonNode rootNode = mapper.readTree(jsonData);
                                JsonNode vendorNode = rootNode.path("vendorOrUser"); // Access the 'vendor' node
                                Utilisateur utilisateur = mapper.treeToValue(vendorNode, Utilisateur.class);
                                callback.onSuccess(utilisateur);
                            } catch (JsonProcessingException e) {
                                callback.onError(e); // Pass error to callback
                            }
                        }
                    });
                } catch (IOException e) {
                    callback.onError(e); // Pass error to callback
                }
            }
        }).start();
    }

    // Callback interface for async operations
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
}
