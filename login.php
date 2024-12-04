<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Se connecter - Shop&Sell</title>
    <link rel="stylesheet" href="login.css">


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

    <div class="container">
        <div class="form-container">

            <form id="loginForm">
                <h1>Se connecter</h1>

                <input type="text" id="email" name="email" placeholder="Adresse électronique" required>
                <input type="password" id="password" name="password" placeholder="Mot de passe" required>
                <div class="form-button">
                    <a href="motdepasse-oublie.php" class="LostPassword">Mot de passe oublié ?</a>
                    <button type="submit">Se connecter</button>
                    <a href="creationCompte.php" class="create">Créer un compte</a>
                </div>
                <p>En continuant, vous acceptez les <a href="conditions.php">Conditions d'utilisation</a> et la <a href="confidentialite.html">Déclaration de confidentialité</a>.</p>
            </form>
        </div>
    </div>
    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>


    <script src="login.js"></script>
    <script src="search.js"></script>

</body>

</html>