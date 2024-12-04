package com.example.applicationshopandsell.adapteurs;

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
import com.example.applicationshopandsell.objets.Vendeur;

public class VendeurAdapter extends ArrayAdapter<Vendeur> {

    private Vendeur[] vendeurs;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    private final String URL_POINT_ENTREE = "http://10.0.2.2";

    public VendeurAdapter(@NonNull Context context, int viewResourceId, @NonNull Vendeur[] vendeurs) {

        super(context, viewResourceId, vendeurs);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.vendeurs = vendeurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Vendeur vendeur = this.vendeurs[position];

        if (vendeur != null) {
            TextView txtNomPrenom = (TextView) view.findViewById(R.id.txt_test_v);
            ImageView ivImage = (ImageView) view.findViewById(R.id.img_test_v);

            txtNomPrenom.setText(vendeur.getNom()+" "+vendeur.getPrenom());

            Glide.with(contexte)
                    .load(vendeur.getPhoto_profil())
                    .into(ivImage);
        }
        return view;
    }
}
