<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Site de Vente en Ligne</title>
    <link rel="stylesheet" href="articles.css">
</head>

<body>
    <header>
        <div class="header-container">
            <a href="accueil.php" class="logo">Shop <img class="img_logo" src="Image/image_logo.png" alt="Image du logo"> Sell</a>
            <div class="search-bar">
                <input type="text" id="searchInput" placeholder="Rechercher des produits">
                <button type="button" id="searchButton">Rechercher</button>
            </div>
            <a href="login.php" id="loginLink" class="header-links">Connexion</a>
            <a href="profil.php" id="profileLink" class="header-links" style="display: none;">Profil</a>
            <a href="#" id="logoutButton" class="header-links" style="display: none;">Déconnexion</a>
            <a href="sell.php" id="sellLink" class="header-links" style="display: none;">Vendre</a>
            <a href="aide.php" class="header-links">Aide</a>
        </div>
    </header>

    <div class="content-wrapper">

        <aside class="sidebar">
            <h2>Catégorie d'article</h2>
            <ul class="types-list">
                <li><a href="articles.php">Tous les articles</a></li>
                <li><a href="articles.php?type=Vetements">Vêtements</a></li>
                <li><a href="articles.php?type=Electronique">Électronique</a></li>
                <li><a href="articles.php?type=Sport">Sport</a></li>
                <li><a href="articles.php?type=Education">Éducation</a></li>
                <li><a href="articles.php?type=Accessoires">Accessoires</a></li>
                <li><a href="articles.php?type=Decorations">Décorations</a></li>
                <li><a href="articles.php?type=Sante+et+beaute">Santé et beauté</a></li>
            </ul>

            <h2>Trier par</h2>

            <div id="sortOptions">

                <div class="sort-option">
                    <input type="checkbox" name="sort" id="nameAsc" value="nameAsc">
                    <label for="nameAsc">Nom (A-Z)</label>
                </div>

                <div class="sort-option">
                    <input type="checkbox" name="sort" id="nameDesc" value="nameDesc">
                    <label for="nameDesc">Nom (Z-A)</label>
                </div>

                <div class="sort-option">
                    <input type="checkbox" name="sort" id="priceAsc" value="priceAsc">
                    <label for="priceAsc">Prix (Croissant)</label>
                </div>

                <div class="sort-option">
                    <input type="checkbox" name="sort" id="priceDesc" value="priceDesc">
                    <label for="priceDesc">Prix (Décroissant)</label>
                </div>

                <div class="price-range">
                    <label for="minPrice">Prix minimum: <input type="number" id="minPrice" min="0"></label>
                </div>

                <div class="price-range">
                    <label for="maxPrice">Prix maximum: <input type="number" id="maxPrice" min="0"></label>
                </div>

                <div class="button-group">
                    <button id="applySort">Sauvegarder les choix</button>
                    
                    <button id="resetSort">Réinitialiser les choix</button>
                </div>

            </div>

        </aside>

        <main>
            <h1>Articles</h1>
            <ul id="articles-list" class="articles-list"></ul>
        </main>

    </div>

    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>

    <script src="articles.js"></script>
    <script src="auth.js"></script>
    <script src="search.js"></script>



</body>

</html>