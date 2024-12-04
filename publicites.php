<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Publicités - Shop & Sell</title>
    <link rel="stylesheet" href="conditions.css">
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
    <div class="sidebar">
        <ul>
            <li><a href="#introduction">Introduction</a></li>
            <li><a href="#type-de-renseignements">Types de renseignements</a></li>
            <li><a href="#collaboration">Collaboration avec des tiers</a></li>
            <li><a href="#preferences">Préférences publicitaires</a></li>
        </ul>
    </div>
    <div class="content">
        <h1>Publicités ciblées</h1>

        <h2 id="introduction">Introduction</h2>
        <p>Les publicités ciblées sont des annonces personnalisées basées sur vos intérêts et comportements en ligne. 
            Elles sont conçues pour vous montrer des produits et services qui pourraient vous plaire, 
            en s'appuyant sur vos interactions précédentes avec Shop & Sell. Nous adhérons aux normes 
            d'autorégulation de l'Alliance canadienne de la publicité numérique pour garantir une publicité éthique et transparente.</p>

        <h2 id="type-de-renseignements">Quels renseignements utilisons-nous?</h2>
        <p>Pour afficher des publicités basées sur vos intérêts, nous collectons des données anonymes à partir de vos interactions avec notre site.
             Nous n'utilisons pas de renseignements personnels identifiables tels que votre nom ou votre adresse e-mail.
              Nous utilisons des technologies comme les cookies et les pixels invisibles pour analyser l'efficacité de nos publicités 
              et vous proposer des annonces plus pertinentes.</p>
        <p>Pour en savoir plus sur les données que nous recueillons, consultez notre Déclaration de confidentialité.</p>

        <h2 id="collaboration">Collaboration avec des tiers</h2>
        <p>Nous travaillons avec des partenaires externes, y compris des annonceurs, des réseaux sociaux et des moteurs de recherche, 
            pour améliorer la pertinence de nos publicités. Nous ne partageons pas vos renseignements personnels identifiables avec ces partenaires.
             Les annonceurs tiers peuvent supposer que les utilisateurs qui cliquent sur des publicités font partie du groupe ciblé, 
             mais ils ne reçoivent pas d'informations permettant de vous identifier personnellement.</p>
        <p>Les tiers utilisent également des technologies publicitaires pour mesurer l'efficacité de leurs annonces et 
            vous proposer des publicités mieux ciblées. Pour plus d'informations sur la gestion des cookies et autres technologies,
             consultez notre Déclaration sur l'utilisation des cookies.</p>

        <h2 id="preferences">Préférences publicitaires</h2>
        <p>Shop & Sell vous offre la possibilité de gérer vos préférences en matière de publicités ciblées. 
            Vous pouvez choisir de ne pas recevoir ce type de publicités tout en continuant à voir des annonces génériques.
             Consultez notre page Préférences publicitaires pour ajuster vos paramètres.</p>
        <p>Vous pouvez également refuser les publicités personnalisées de la part de réseaux publicitaires tiers 
            en visitant les sites du Network Advertising Initiative (NAI) et de l'Alliance canadienne de la publicité numérique (ACP).</p>
        <p>Pour plus d'informations sur les pratiques publicitaires de nos partenaires, 
            veuillez consulter leurs politiques respectives en matière de publicité et de confidentialité.</p>
    </div>
    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>
    <script src="auth.js"></script>
    <script src="search.js"></script>

</body>

</html>
