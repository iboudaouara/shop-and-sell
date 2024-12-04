package com.example.applicationshopandsell.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applicationshopandsell.ressources.AfficherTexteInfo;
import com.example.applicationshopandsell.R;
import com.google.android.material.navigation.NavigationView;

public class PageAideActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ScrollView scrollView;
    private TextView t;
    private NavigationView navigationView;
    private String[] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_aide);

        t = findViewById(R.id.txt_adapt);
        Button b = findViewById(R.id.btn_back);

        // Retrieve the type of text
        Intent intentRecupInfo = getIntent();
        String typeText = intentRecupInfo.getStringExtra("type_texte");
        Log.d("PageAideActivity", "Type of text: " + typeText);

        if (typeText != null) {
            switch (typeText) {
                case "aide":
                    menuItems = new String[] {"Comment créer un compte ?",
                            "Comment passer une commande ?",
                            "Quels modes de paiement acceptez-vous ?",
                            "Comment réinitialiser mon mot de passe si je l'ai oublié ?"};
                    t.setText(Html.fromHtml(AfficherTexteInfo.afficherAide(), Html.FROM_HTML_MODE_LEGACY));
                    break;
                case "a_propos":
                    menuItems = new String[] {
                            "1. Introduction",
                            "2. À propos de Shop & Sell",
                            "3. Utilisation de Shop & Sell",
                            "4. Application du règlement",
                            "5. Contenu",
                            "6. Notification d'allégations à l'égard de la violation de la " +
                                    "propriété intellectuelle et du droit d'auteur",
                            "7. Blocages et fonds restreints",
                            "8. Autorisation de vous contacter; " +
                            "enregistrement des appels; " +
                            "analyse du contenu des messages"};
                    t.setText(Html.fromHtml(AfficherTexteInfo.afficherAPropos(), Html.FROM_HTML_MODE_LEGACY));
                    break;
                case "contact":
                    menuItems = new String[] {""}; // Empty item, adjust if needed
                    t.setText(Html.fromHtml(AfficherTexteInfo.afficherContacter(), Html.FROM_HTML_MODE_LEGACY));
                    break;
                default:
                    Log.e("PageAideActivity", "Unknown type of text: " + typeText);
            }
        }

        b.setOnClickListener(v1 -> {
            Intent i = new Intent(PageAideActivity.this, ConnexionUtilisateurActivity.class);
            startActivity(i);
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        scrollView = findViewById(R.id.scroll_view);

        if (drawerLayout != null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            setupMenu(menuItems);

            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id >= 0 && id < menuItems.length) {
                    Toast.makeText(PageAideActivity.this, menuItems[id], Toast.LENGTH_SHORT).show();
                    scrollToAnchor(menuItems[id]);
                } else {
                    Log.e("PageAideActivity", "Menu item index out of bounds: " + id);
                }

                drawerLayout.closeDrawers();
                return true;
            });
        }
    }

    private void setupMenu(String[] menuItems) {
        Menu menu = navigationView.getMenu();
        menu.clear(); // Clear existing items

        for (int i = 0; i < menuItems.length; i++) {
            menu.add(Menu.NONE, i, Menu.NONE, menuItems[i]);
        }
    }
    private void scrollToAnchor(String targetText) {
        scrollView.post(() -> {
            t.post(() -> {
                // Get the rendered text from TextView
                String renderedText = t.getText().toString();
                Log.d("PageAideActivity", "Rendered TextView content: " + renderedText);

                // Find the position of the target text
                int start = renderedText.indexOf(targetText);
                Log.d("PageAideActivity", "Text position for \"" + targetText + "\": " + start);

                if (start != -1) {
                    // Calculate the line number containing the start of the target text
                    int lineNumber = t.getLayout().getLineForOffset(start);
                    int y = t.getLayout().getLineTop(lineNumber);

                    // Log scrolling position
                    Log.d("PageAideActivity", "Scrolling to line " + lineNumber + ", y = " + y);

                    // Scroll smoothly to the calculated position
                    scrollView.smoothScrollTo(0, y);
                } else {
                    Log.d("PageAideActivity", "Text not found: " + targetText);
                }
            });
        });
    }


}
