package com.example.applicationshopandsell.adapteurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.objets.Article;
import com.example.applicationshopandsell.ressources.API;

public class ArticlesAdapter extends ArrayAdapter<Article> {
    private Article[] articles;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    private final String URL_POINT_ENTREE = "http://10.0.2.2:8181";

    public ArticlesAdapter(@NonNull Context context, int viewResourceId, @NonNull Article[] articles) {
        super(context, viewResourceId, articles);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return this.articles.length;
    }
    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Article article = this.articles[position];

        if (article != null) {
            final TextView txtNom = (TextView) view.findViewById(R.id.txtNom);
            final TextView txtPrix = (TextView) view.findViewById(R.id.txtPrix);
            final ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);

            txtNom.setText(article.getNom());

            String prixArticle = String.format("%.2f $", article.getPrix());
            txtPrix.setText(prixArticle);

            Glide.with(contexte)
                    .load(API.URL_POINT_ENTREE + article.getUrl_image())
                    .into(ivImage);
        }
        return view;
    }
}
