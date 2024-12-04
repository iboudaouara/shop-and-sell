<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Site de Vente en Ligne</title>
    <link rel="stylesheet" href="accueil.css">
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

    <main>
        <div class="hero">
            <h2 id="welcomeMessage">Bienvenue!</h2>
            <img src="Image/img_accueil.png" alt="Image d'accueil du site">
        </div>
        <div class="products-container">

            <div class="product">
                <img src="Image/produits.png" alt="Différents produits">
                <h3>Tous vos articles préférés au meilleurs prix </h3>
                <a href="articles.php">Voir tout </a>
            </div>

            <div class="product">
                <img src="https://th.bing.com/th/id/R.5ef90275562ce9f2d75426e8cae998bc?rik=ddESt%2f1qIFrJRw&pid=ImgRaw&r=0" alt="Alexa">
                <h3>Découvrez ce que vous pouvez faire avec Alexa</h3>
                <a href="articles.php?type=Electronique">Voir plus dans les Électroniques</a>
            </div>

            <div class="product">
                <img src="Image/sports.png" alt="Différents sports">
                <h3>Tous vos sports préférés </h3>
                <a href="articles.php?type=Sport">Voir plus dans Sports</a>
            </div>

            <div class="product">
                <img src="https://assets.afcdn.com/story/20190905/2021984_w3850h2887c1cx1925cy2740cxt0cyt0cxb3850cyb5479.jpg" alt="Produits de soins">
                <h3>Prenez soins de vous pour peu </h3>
                <a href="articles.php?type=Sante_beaute">Voir plus dans Santé et Beauté</a>
            </div>

            <div class="product">
                <img src="https://i.pinimg.com/736x/62/47/cb/6247cb119a2c1fed2e5f49044884f7b7.jpg" alt="Vêtements">
                <h3>Les nouvelles tendences à porté de mains </h3>
                <a href="articles.php?type=Vetements">Voir plus dans Vêtements</a>
            </div>

            <div class="product">
                <img src="https://resize-elle.ladmedia.fr/rcrop/638,,forcex/img/var/plain_site/storage/images/loisirs/livres/dossiers/livre-fait-divers/74793089-1-fre-FR/Faits-divers-les-livres-qui-vont-vous-happer.jpg" alt="Livres">
                <h3>Apprenez en plus tous les jours </h3>
                <a href="articles.php?type=Educatif">Voir plus dans Éducation</a>
            </div>

            <div class="product">
                <img src="https://bob-crew.com/cdn/shop/articles/Accessoires-de-mode.png?v=1685206097&width=2048" alt="Accessoires de mode">
                <h3>La touche de plus </h3>
                <a href="articles.php?type=Accessoires">Voir plus dans Accessoires</a>
            </div>

            <div class="product">
                <img src="https://soumissionsquebec.ca/wp-content/uploads/2021/11/services-designer-interieur-prix-scaled.jpg" alt="Design d'intérieur">
                <h3>Votre maison à Votre goût </h3>
                <a href="articles.php?type=Decorations">Voir plus dans Décoration</a>
            </div>

        </div>
    </main>

    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>
    <script src="accueil.js"></script>
    <script src="search.js"></script>


</body>

</html>