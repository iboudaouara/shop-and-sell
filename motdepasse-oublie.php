<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mot de Passe Oublié</title>
    <link rel="stylesheet" href="motdepasse-oublie.css">

</head>

<body class="motdepasse-oublie">
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
        <h2>Mot de Passe Oublié</h2>
        <div id="step1">
            <form id="emailForm">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                <button type="submit">Vérifier Email</button>
            </form>
        </div>
        <div id="step2" style="display:none;">
            <form id="securityQuestionsForm">
                <div id="questions"></div>
                <button type="submit">Vérifier Réponses</button>
            </form>
        </div>
        <div id="step3" style="display:none;">
            <form id="newPasswordForm">
                <label for="newPassword">Nouveau Mot de Passe:</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <label for="confirmPassword">Confirmer Mot de Passe:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <button type="submit">Changer Mot de Passe</button>
            </form>
        </div>
        <div id="message"></div>
    </div>
    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>

    <script src="motdepasse-oublie.js"></script>
    <script src="search.js"></script>
    <script src="auth.js"></script>


</body>

</html>